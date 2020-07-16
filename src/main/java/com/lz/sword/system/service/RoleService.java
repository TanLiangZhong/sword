package com.lz.sword.system.service;

import com.lz.sword.common.constant.BaseEnum;
import com.lz.sword.common.constant.ResultMsg;
import com.lz.sword.common.constant.SystemEnum;
import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.bo.RoleRelationBo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.JpaUtils;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.system.entity.Role;
import com.lz.sword.system.entity.RoleLink;
import com.lz.sword.system.entity.User;
import com.lz.sword.system.entity.UserRole;
import com.lz.sword.system.repository.RoleLinkRepository;
import com.lz.sword.system.repository.RoleRepository;
import com.lz.sword.system.repository.UserRoleRepository;
import com.lz.sword.basic.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/15 12:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService extends BaseServiceImpl<RoleRepository, Role, Long> {

    private final RoleRepository roleRepository;
    private final RoleLinkRepository roleLinkRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * 分页
     *
     * @param pageBo 分页对象
     * @return {@link PageVo <Role>}
     */
    public PageVo<Role> findPage(PageBo<KeywordSearchBo> pageBo) {
        Page<Role> page = findPage(pageBo, (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("delFlag"), BaseEnum.DelFlagEnum.NORMAL.getCode()));
            KeywordSearchBo searchBo = pageBo.getParams();
            if (!StringUtils.isEmpty(searchBo.getKeyword())) {
                String keyword = JpaUtils.addWildcard(searchBo.getKeyword());
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), keyword),
                        criteriaBuilder.like(root.get("code"), keyword)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return new PageVo<>(pageBo.getCurrentPage(), pageBo.getPageSize(), page.getTotalElements(), page.getTotalPages(), page.getContent());
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean save(Role bo) {
        Role entity = new Role();
        Role old = roleRepository.findTopByCodeAndDelFlag(bo.getCode(), BaseEnum.DelFlagEnum.NORMAL.getCode());
        if (old != null && !old.getRoleId().equals(bo.getRoleId())) {
            throw BusinessException.of("唯一编号已存在");
        }
        if (bo.getRoleId() != null) {
            entity = roleRepository.findById(bo.getRoleId()).orElse(entity);
        } else {
            bo.setDelFlag(BaseEnum.DelFlagEnum.NORMAL.getCode());
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        roleRepository.saveAndFlush(entity);
        return true;
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteById(Long id) {
        roleRepository.deleteById(BaseEnum.DelFlagEnum.DELETE.getCode(), id);
        roleLinkRepository.deleteByRoleId(id);
        return true;
    }

    /**
     * 批量添加 角色关联
     *
     * @param bo RoleRelationBo
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean batchAddRoleLink(RoleRelationBo bo) {
        List<RoleLink> roleLinks = new ArrayList<>();
        Integer type = bo.getType();
        Long roleId = bo.getRoleId();
        List<Long> linkIds = bo.getLinkIds();
        if (SystemEnum.RoleLinkType.MENU.getCode().equals(type)) {
            roleLinkRepository.deleteByRoleIdAndType(roleId, type);
        } else {
            roleLinkRepository.deleteByRoleIdAndTypeAndElementIdIn(roleId, type, bo.getCancelIds());
        }
        linkIds.forEach(it -> {
            if (SystemEnum.RoleLinkType.MENU.getCode().equals(type)) {
                roleLinks.add(new RoleLink(roleId, it, null, type));
            } else {
                roleLinks.add(new RoleLink(roleId, null, it, type));
            }
        });
        if (!CollectionUtils.isEmpty(roleLinks)) {
            roleLinkRepository.saveAll(roleLinks);
        }
        return true;
    }

    /**
     * 查询角色关联的菜单或元素Id集合
     *
     * @param roleId 角色Id
     * @param type   关联类型
     * @return 0-菜单Id集合 , 1-元素Id集合
     */
    public List<Long> findLinkIds(Long roleId, Integer type) {
        List<RoleLink> roleLinks = roleLinkRepository.findAllByRoleIdInAndType(Collections.singleton(roleId), type);
        if (SystemEnum.RoleLinkType.MENU.getCode().equals(type)) {
            return roleLinks.stream().map(RoleLink::getMenuId).collect(Collectors.toList());
        } else {
            return roleLinks.stream().map(RoleLink::getElementId).collect(Collectors.toList());
        }
    }

    /**
     * 找到启用的角色Id
     *
     * @return 角色Id
     */
    public Set<Long> findEnableRole(Set<Long> roleIds) {
        return roleRepository.findAll().stream().filter(it -> SystemEnum.Status.ENABLE.getCode().equals(it.getStatus()) && roleIds.contains(it.getRoleId())).map(Role::getRoleId).collect(Collectors.toSet());
    }

    /**
     * 根据角色Id查询用户
     *
     * @param roleId 角色Id
     * @return User
     */
    public List<UserVo> selectUserByRoleId(Long roleId) {
        List<User> users = userRoleRepository.selectUserByRoleId(roleId);
        return users.stream().map(it -> {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(it, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 关联用户
     *
     * @param bo
     * @return
     */
    public Boolean relatedUser(RoleRelationBo bo) {
        if (CollectionUtils.isEmpty(bo.getLinkIds())) {
            throw BusinessException.of(ResultMsg.PARAM_IS_NULL);
        }
        List<UserRole> userRoles = new ArrayList<>();
        bo.getLinkIds().forEach(it -> userRoles.add(new UserRole(it, bo.getRoleId())));
        userRoleRepository.saveAll(userRoles);
        return true;
    }

    /**
     * 取消关联用户
     *
     * @param bo
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean cancelRelatedUser(RoleRelationBo bo) {
        userRoleRepository.deleteByRoleIdAndUserIdIn(bo.getRoleId(), bo.getLinkIds());
        return true;
    }

}
