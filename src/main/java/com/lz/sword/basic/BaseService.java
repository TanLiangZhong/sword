package com.lz.sword.basic;

import com.lz.sword.common.domain.PageBo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service 基类
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/15 12:09
 */
public interface BaseService<T, ID> {

    /**
     * 分页查询
     *
     * @param specification 查询条件
     * @param pageBo        分页对象
     * @return 分页对象
     */
    <B> Page<T> findPage(PageBo<B> pageBo, Specification<T> specification);

}
