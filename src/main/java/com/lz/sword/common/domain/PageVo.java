package com.lz.sword.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应实体
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/1 16:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页响应")
public class PageVo<T> implements Serializable {

    @ApiModelProperty("当前页")
    private int currentPage;

    @ApiModelProperty("页码大小")
    private int pageSize;

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("总页数")
    private int totalPages;

    @ApiModelProperty("结果集合")
    private List<T> list;
}
