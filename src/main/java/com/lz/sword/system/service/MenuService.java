package com.lz.sword.system.service;

import com.lz.sword.common.constant.BaseEnum;
import com.lz.sword.common.constant.SystemEnum;
import com.lz.sword.common.domain.system.vo.MenuVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.system.entity.Menu;
import com.lz.sword.system.entity.RoleLink;
import com.lz.sword.system.entity.UserRole;
import com.lz.sword.system.repository.MenuRepository;
import com.lz.sword.system.repository.RoleLinkRepository;
import com.lz.sword.system.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 11:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final UserRoleRepository userRoleRepository;
    private final RoleLinkRepository roleLinkRepository;
    private final MenuRepository menuRepository;
    private final RoleService roleService;

    /**
     * 获得用户所有菜单权限
     *
     * @param userId 用户Id
     * @return 用户可用菜单权限
     */
    public List<String> getUserAuthority(Long userId) {
        return this.findListByUserId(userId).stream().map(Menu::getAuthority).collect(Collectors.toList());
    }

    /**
     * 获得用户可用菜单列表
     *
     * @param userId 用户Id
     * @return 菜单集合
     */
    public List<Menu> findListByUserId(Long userId) {
        Set<Long> roleIds = userRoleRepository.findAllByUserId(userId).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<Long> menuIds = roleLinkRepository.findAllByRoleIdInAndType(roleService.findEnableRole(roleIds), SystemEnum.RoleLinkType.MENU.getCode()).stream().map(RoleLink::getMenuId).collect(Collectors.toSet());
        return menuRepository.findAllByMenuIdInAndDelFlagOrderBySort(menuIds, BaseEnum.DelFlagEnum.NORMAL.getCode());
    }

    public List<MenuVo> findList() {
        List<Menu> menus = menuRepository.findAllByDelFlagOrderBySort(BaseEnum.DelFlagEnum.NORMAL.getCode());
        return menus.stream().map(it -> {
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(it, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 菜单树
     *
     * @return 菜单树
     */
    public List<MenuVo> getTree() {
        List<MenuVo> menus = findList();
        TreeMap<Long, List<MenuVo>> treeMap = new TreeMap<>();
        menus.forEach(it -> {
            Long menuId = it.getMenuId();
            Long parentId = Optional.ofNullable(it.getParentId()).orElse(0L);
            if (!treeMap.containsKey(menuId)) {
                treeMap.put(menuId, new ArrayList<>());
            }
            it.setChildren(treeMap.get(menuId));
            if (!treeMap.containsKey(parentId)) {
                treeMap.put(parentId, new ArrayList<>());
            }
            treeMap.get(parentId).add(it);
        });
        return Optional.ofNullable(treeMap.get(0L)).orElse(Collections.emptyList());
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean save(Menu bo) {
        Menu entity = new Menu();
        Menu old = menuRepository.findTopByAuthorityAndDelFlag(bo.getAuthority(), BaseEnum.DelFlagEnum.NORMAL.getCode());
        if (old != null && !old.getMenuId().equals(bo.getMenuId())) {
            throw BusinessException.of("权限标识已存在");
        }
        if (bo.getMenuId() != null) {
            entity = menuRepository.findById(bo.getMenuId()).orElse(entity);
        } else {
            bo.setDelFlag(BaseEnum.DelFlagEnum.NORMAL.getCode());
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        menuRepository.saveAndFlush(entity);
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
        menuRepository.deleteById(BaseEnum.DelFlagEnum.DELETE.getCode(), id);
        return true;
    }
}
