package com.lz.sword.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 分页入参
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/1 16:49
 */
@Data
@ApiModel("分页入参")
public class PageBo<T> implements Serializable {

    @ApiModelProperty(value = "当前页", required = true, example = "1")
    private int currentPage = 1;

    @ApiModelProperty(value = "页码大小", required = true, example = "10")
    private int pageSize = 10;

    @ApiModelProperty("排序")
    private HashMap<String, String> sorter;

    @ApiModelProperty("查询条件")
    private T params;
}