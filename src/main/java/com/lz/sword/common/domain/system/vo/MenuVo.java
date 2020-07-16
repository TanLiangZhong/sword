package com.lz.sword.common.domain.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVo implements Serializable {

    @ApiModelProperty("菜单Id")
    private Long menuId;

    @ApiModelProperty("父级主键(顶级为0）")
    private Long parentId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("链接")
    private String href;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("是否显示(1-显示,0-不显示)")
    private Integer isShow;

    @ApiModelProperty("权限标识")
    private String authority;

    @ApiModelProperty("排序")
    private Long sort;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private String gmtCreatedStr;

    @ApiModelProperty("创建人")
    private String creatorName;

    @ApiModelProperty("更新时间")
    private String gmtModifiedStr;

    @ApiModelProperty("更新人")
    private String updaterName;

    @ApiModelProperty("下级菜单")
    private List<MenuVo> children;

}