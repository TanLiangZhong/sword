package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 角色 - Repository
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {


    /**
     * 按Id删除
     *
     * @param delFlag 删除标记
     * @param id      主键
     */
    @Modifying
    @Query("update Role set delFlag = ?1 where roleId = ?2")
    void deleteById(Integer delFlag, Long id);

    Role findTopByCodeAndDelFlag(String code, Integer delFlag);

}
