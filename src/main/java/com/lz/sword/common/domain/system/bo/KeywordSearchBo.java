package com.lz.sword.common.domain.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 关键字搜索
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-13 19:00:48
 */
@Data
@ApiModel("关键字搜索-Bo")
public class KeywordSearchBo {

    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("开始日期")
    private LocalDateTime beginDate;
    @ApiModelProperty("结束日期")
    private LocalDateTime endDate;
    @ApiModelProperty("不等于id")
    private List<Long> notInId;
}
