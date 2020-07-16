package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 菜单
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, Long> {

    /**
     * 根据主键批量查询
     *
     * @param menuIds 菜单Id
     * @param delFlag 删除标记
     * @return 菜单
     */
    List<Menu> findAllByMenuIdInAndDelFlagOrderBySort(Set<Long> menuIds, Integer delFlag);


    /**
     * 查询菜单列表
     *
     * @param delFlag 删除标记
     * @return 菜单
     */
    List<Menu> findAllByDelFlagOrderBySort(Integer delFlag);


    /**
     * 按Id删除
     *
     * @param delFlag 删除标记
     * @param id      主键
     */
    @Modifying
    @Query("update Menu set delFlag = ?1 where menuId = ?2")
    void deleteById(Integer delFlag, Long id);

    Menu findTopByAuthorityAndDelFlag(String authority, Integer delFlag);
}
