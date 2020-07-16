package com.lz.sword.common.domain.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织机构
 *
 * @author Tan
 * @version 1.1, 2020/5/25 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("组织机构-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchVo {

    @ApiModelProperty("机构Id")
    private Long branchId;

    @ApiModelProperty("父级Id(最上级为0)")
    private Long parentId;

    @ApiModelProperty("唯一编号")
    private String code;

    @ApiModelProperty("机构名称")
    private String name;

    @ApiModelProperty("状态 (1-启用 0-禁用)")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreated;

    @ApiModelProperty("创建人")
    private Long creator;

    @ApiModelProperty("更新时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("更新人")
    private Long updater;

    @ApiModelProperty("下级")
    private List<BranchVo> children;

}
