package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Branch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 数据字典
 *
 * @author Tan
 * @version 1.0, 2020/5/24 14:58
 */
@Repository
public interface BranchRepository extends BaseRepository<Branch, Long> {

    List<Branch> findByParentIdIn(Set<Long> parentIds);

    Branch findTopByCode(String tag);
}
