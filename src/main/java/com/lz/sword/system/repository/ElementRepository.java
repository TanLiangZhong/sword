package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Element;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 页面元素
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface ElementRepository extends BaseRepository<Element, Long> {

    /**
     * 根据  主键 查找
     *
     * @param elementIds 主键集合
     * @return 元素
     */
    List<Element> findAllByElementIdIn(Set<Long> elementIds);

    /**
     * 根据  父级Id 查找
     *
     * @param parentId 父级Id
     * @return 元素
     */
    List<Element> findByParentId(Long parentId);

    /**
     * 根据  权限标识 查找
     *
     * @param authority 权限标识
     * @return 元素
     */
    Element findTopByAuthority(String authority);
}
