package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Role;
import com.lz.sword.system.entity.User;
import com.lz.sword.system.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联
 *
 * @author 谭良忠
 * @version 1.0 2019/7/23 11:05
 */
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, Long> {

    /**
     * 根据用户Id查询
     *
     * @param userId 用户Id
     * @return UserRole
     */
    List<UserRole> findAllByUserId(Long userId);

    /**
     * 按角色ID选择用户
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    @Query("SELECT u FROM User u,UserRole ur where u.userId = ur.userId and u.delFlag = 1 and ur.roleId = ?1")
    List<User> selectUserByRoleId(Long roleId);

    /**
     * 按用户ID选择角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r, UserRole ur where r.roleId = ur.roleId and r.delFlag = 1 and ur.userId in ?1")
    List<Role> selectRoleByUserIds(Long userId);

    /**
     * 按角色ID和用户ID删除
     *
     * @param roleId  角色ID
     * @param userIds 用户ID
     */
    void deleteByRoleIdAndUserIdIn(Long roleId, List<Long> userIds);
}
