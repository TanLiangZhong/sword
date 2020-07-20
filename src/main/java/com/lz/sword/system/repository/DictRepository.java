package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 数据字典
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface DictRepository extends BaseRepository<Dict, Long> {

    /**
     * 根据 tag 查找 top 1
     *
     * @param tag 标签
     * @return 字典
     */
    Dict findTopByTag(String tag);

    /**
     * 根据 父级Id 查找
     *
     * @param parentIds 父级Id
     * @return 字典
     */
    List<Dict> findByParentIdIn(Set<Long> parentIds);
}
