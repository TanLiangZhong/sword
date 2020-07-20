package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.RoleLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色关联表
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface RoleLinkRepository extends BaseRepository<RoleLink, Long> {

    /**
     * 根据角色Id查询
     *
     * @param roleIds 角色Ids
     * @return RoleLink
     */
    List<RoleLink> findAllByRoleIdIn(Set<Long> roleIds);

    /**
     * 根据角色Id查询
     *
     * @param roleIds 角色Ids
     * @param type    类型
     * @return RoleLink
     */
    List<RoleLink> findAllByRoleIdInAndType(Set<Long> roleIds, Integer type);

    /**
     * 根据 角色Id,类型 删除
     *
     * @param roleId 角色Id
     * @param type   类型
     */
    @Modifying
    @Query("delete from RoleLink  where roleId = ?1 and type = ?2")
    void deleteByRoleIdAndType(Long roleId, Integer type);

    /**
     * 根据 角色Id 删除
     *
     * @param roleId 角色Id
     */
    @Modifying
    @Query("delete from RoleLink  where roleId = ?1")
    void deleteByRoleId(Long roleId);

    /**
     * 按角色ID和类型及元素ID删除
     *
     * @param roleId 角色Id
     * @param type 类型( 0:菜单 1:页面元素 )
     * @param elementId  元素Id
     */
    @Modifying
    @Query("delete from RoleLink  where roleId = ?1 and type = ?2 and elementId in ?3")
    void deleteByRoleIdAndTypeAndElementIdIn(Long roleId, Integer type, List<Long> elementId);
}
