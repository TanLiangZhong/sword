package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 用户信息
 *
 * @author 谭良忠
 * @version 1.0 2019/6/19 17:19
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @param dFlag    删除标记
     * @return {@link User}
     */
    User findTopByUsernameAndDelFlag(String username, Integer dFlag);

    /**
     * 按Id删除
     *
     * @param delFlag 删除标记
     * @param id      主键
     */
    @Modifying
    @Query("update User set delFlag = ?1 where userId = ?2")
    void deleteById(Integer delFlag, Long id);
}
