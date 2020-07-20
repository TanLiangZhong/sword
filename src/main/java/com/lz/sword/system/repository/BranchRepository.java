package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Branch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 机构
 *
 * @author Tan
 * @version 1.0, 2020/5/24 14:58
 */
@Repository
public interface BranchRepository extends BaseRepository<Branch, Long> {

    /**
     * 根据 父级Id 查找
     *
     * @param parentIds 父级Id
     * @return 机构
     */
    List<Branch> findByParentIdIn(Set<Long> parentIds);

    /**
     * 根据 tag 查找 top 1
     *
     * @param tag 标签
     * @return 机构
     */
    Branch findTopByCode(String tag);
}
