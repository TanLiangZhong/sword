package com.lz.sword.system.service;

import com.lz.sword.common.constant.SystemEnum;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.system.entity.Element;
import com.lz.sword.system.entity.RoleLink;
import com.lz.sword.system.entity.UserRole;
import com.lz.sword.system.repository.ElementRepository;
import com.lz.sword.system.repository.RoleLinkRepository;
import com.lz.sword.system.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ElementService {

    private final ElementRepository elementRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleLinkRepository roleLinkRepository;
    private final RoleService roleService;

    /**
     * 按菜单Id查询
     *
     * @param menuId 菜单Id
     * @return 菜单下的页面元素
     */
    public List<Element> findByMenuId(Long menuId) {
        return elementRepository.findByParentId(menuId);
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean save(Element bo) {
        Element entity = new Element();
        Element old = elementRepository.findTopByAuthority(bo.getAuthority());
        if (old != null && !old.getElementId().equals(bo.getElementId())) {
            throw BusinessException.of("权限标识已存在");
        }
        if (bo.getElementId() != null) {
            entity = elementRepository.findById(bo.getElementId()).orElse(entity);
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        elementRepository.saveAndFlush(entity);
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
        elementRepository.deleteById(id);
        return true;
    }

    /**
     * 获得用户所有页面元素权限
     *
     * @param userId 用户Id
     * @return 用户可用页面元素权限
     */
    public List<String> getUserAuthority(Long userId) {
        Set<Long> roleIds = userRoleRepository.findAllByUserId(userId).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<Long> elementIds = roleLinkRepository.findAllByRoleIdInAndType(roleService.findEnableRole(roleIds), SystemEnum.RoleLinkType.ELEMENT.getCode()).stream().map(RoleLink::getElementId).collect(Collectors.toSet());
        return elementRepository.findAllByElementIdIn(elementIds).stream().filter(it -> SystemEnum.Status.ENABLE.getCode().equals(it.getStatus())).map(Element::getAuthority).collect(Collectors.toList());
    }

}
