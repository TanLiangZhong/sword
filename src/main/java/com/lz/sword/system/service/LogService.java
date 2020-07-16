package com.lz.sword.system.service;

import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.utils.JpaUtils;
import com.lz.sword.basic.impl.BaseServiceImpl;
import com.lz.sword.system.entity.Log;
import com.lz.sword.system.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 系统日志
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/17 19:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogService extends BaseServiceImpl<LogRepository, Log, Long> {

    private final LogRepository logRepository;

    /**
     * 分页
     *
     * @param pageBo 分页对象
     * @return {@link PageVo <Log>}
     */
    public PageVo<Log> findPage(PageBo<KeywordSearchBo> pageBo) {
        Page<Log> page = findPage(pageBo, (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            KeywordSearchBo searchBo = pageBo.getParams();
            if (!StringUtils.isEmpty(searchBo.getKeyword())) {
                String keyword = JpaUtils.addWildcard(searchBo.getKeyword());
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("username"), keyword),
                        criteriaBuilder.like(root.get("operation"), keyword),
                        criteriaBuilder.like(root.get("method"), keyword)
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return new PageVo<>(pageBo.getCurrentPage(), pageBo.getPageSize(), page.getTotalElements(), page.getTotalPages(), page.getContent());
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Async
    public CompletableFuture<Boolean> save(Log bo) {
        logRepository.saveAndFlush(bo);
        return CompletableFuture.completedFuture(true);
    }


}
