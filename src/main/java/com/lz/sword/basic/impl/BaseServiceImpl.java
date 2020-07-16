package com.lz.sword.basic.impl;

import com.lz.sword.common.domain.PageBo;
import com.lz.sword.basic.BaseRepository;
import com.lz.sword.basic.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

/**
 * Service 基类
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/15 12:09
 */
public abstract class BaseServiceImpl<R extends BaseRepository<T, ID>, T, ID> implements BaseService<T, ID> {

    @Autowired(required = false)
    private R r;


    @Override
    public <B> Page<T> findPage(PageBo<B> pageBo, Specification<T> specification) {
        List<Sort.Order> sorts = new ArrayList<>();
        pageBo.getSorter().forEach((k, v) -> {
            if (Sort.Direction.DESC.name().equalsIgnoreCase(v)){
                sorts.add(Sort.Order.desc(k));
            }
            else {
                sorts.add(Sort.Order.asc(k));
            }
        });
        return r.findAll(specification,
                PageRequest.of(pageBo.getCurrentPage() - 1, pageBo.getPageSize(), Sort.by(sorts)));
    }


}
