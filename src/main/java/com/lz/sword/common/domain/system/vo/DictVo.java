package com.lz.sword.common.domain.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liangzhong.tan
 * @version 1.0 2020/4/18 14:07
 */
@Data
@ApiModel("数据字典-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictVo {

    @ApiModelProperty("字典Id")
    private Long dictId;

    @ApiModelProperty("父级Id(顶级为0)")
    private Long parentId;

    @ApiModelProperty("字典key值")
    private String tag;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreated;

    @ApiModelProperty("创建人")
    private Long creator;

    @ApiModelProperty("更新时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("更新人")
    private Long updater;

    @ApiModelProperty("下级")
    private List<DictVo> children;
}
