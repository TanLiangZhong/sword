package com.lz.sword.system.service;

import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.vo.DictVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.JpaUtils;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.basic.impl.BaseServiceImpl;
import com.lz.sword.system.entity.Dict;
import com.lz.sword.system.repository.DictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 数据字典
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/17 19:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictService extends BaseServiceImpl<DictRepository, Dict, Long> {

    private final DictRepository dictRepository;

    /**
     * 分页
     *
     * @param pageBo 分页对象
     * @return {@link PageVo < DictVo >}
     */
    public PageVo<DictVo> findPage(PageBo<KeywordSearchBo> pageBo) {
        Page<Dict> page = findPage(pageBo, (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("parentId"), 0L));
            KeywordSearchBo searchBo = pageBo.getParams();
            if (!StringUtils.isEmpty(searchBo.getKeyword())) {
                String keyword = JpaUtils.addWildcard(searchBo.getKeyword());
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("tag"), keyword),
                        criteriaBuilder.like(root.get("name"), keyword)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<DictVo> vos = new ArrayList<>();
        Map<Long, List<Dict>> dictMap = dictRepository.findByParentIdIn(page.getContent().stream().map(Dict::getDictId).collect(Collectors.toSet()))
                .stream().collect(Collectors.groupingBy(Dict::getParentId));
        page.getContent().forEach(it -> {
            DictVo vo = new DictVo();
            BeanUtils.copyProperties(it, vo);
            vo.setChildren(dictMap.getOrDefault(it.getDictId(), new ArrayList<>()).stream().map(children -> {
                DictVo childrenVo = new DictVo();
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
    public Boolean save(Dict bo) {
        Dict entity = new Dict();
        Dict old = dictRepository.findTopByTag(bo.getTag());
        if (old != null && !old.getDictId().equals(bo.getDictId())) {
            throw BusinessException.of("标签已存在");
        }
        if (bo.getDictId() != null) {
            entity = dictRepository.findById(bo.getDictId()).orElse(entity);
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        dictRepository.saveAndFlush(entity);
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
        dictRepository.deleteById(id);
        return true;
    }

}
