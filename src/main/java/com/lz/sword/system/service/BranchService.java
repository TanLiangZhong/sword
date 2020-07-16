package com.lz.sword.system.service;

import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.vo.BranchVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.JpaUtils;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.system.entity.Branch;
import com.lz.sword.system.repository.BranchRepository;
import com.lz.sword.basic.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组织机构
 *
 * @author Tan
 * @version 1.0, 2020/5/24 14:58
 */
@Service
@RequiredArgsConstructor
public class BranchService extends BaseServiceImpl<BranchRepository, Branch, Long> {

    private final BranchRepository branchRepository;

    /**
     * 分页
     *
     * @param pageBo 分页对象
     * @return {@link PageVo < BranchVo >}
     */
    public PageVo<BranchVo> findPage(PageBo<KeywordSearchBo> pageBo) {
        Page<Branch> page = findPage(pageBo, (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("parentId"), 0L));
            KeywordSearchBo searchBo = pageBo.getParams();
            if (!StringUtils.isEmpty(searchBo.getKeyword())) {
                String keyword = JpaUtils.addWildcard(searchBo.getKeyword());
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), keyword),
                        criteriaBuilder.like(root.get("name"), keyword)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<BranchVo> vos = new ArrayList<>();
        Map<Long, List<Branch>> branchMap = branchRepository.findByParentIdIn(page.getContent().stream().map(Branch::getBranchId).collect(Collectors.toSet()))
                .stream().collect(Collectors.groupingBy(Branch::getParentId));
        page.getContent().forEach(it -> {
            BranchVo vo = new BranchVo();
            BeanUtils.copyProperties(it, vo);
            vo.setChildren(branchMap.getOrDefault(it.getBranchId(), new ArrayList<>()).stream().map(children -> {
                BranchVo childrenVo = new BranchVo();
                BeanUtils.copyProperties(children, childrenVo);
                return childrenVo;
            }).collect(Collectors.toList()));
            vos.add(vo);
        });
        return new PageVo<>(pageBo.getCurrentPage(), pageBo.getPageSize(), page.getTotalElements(), page.getTotalPages(), vos);
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean save(Branch bo) {
        Branch entity = new Branch();
        Branch old = branchRepository.findTopByCode(bo.getCode());
        if (old != null && !old.getBranchId().equals(bo.getBranchId())) {
            throw BusinessException.of("Code已存在");
        }
        if (bo.getBranchId() != null) {
            entity = branchRepository.findById(bo.getBranchId()).orElse(entity);
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        branchRepository.saveAndFlush(entity);
        return true;
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteById(Long id) {
        branchRepository.deleteById(id);
        return true;
    }

}
