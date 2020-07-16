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

    List<Element> findAllByElementIdIn(Set<Long> elementIds);

    List<Element> findByParentId(Long parentId);

    Element findTopByAuthority(String authority);
}
