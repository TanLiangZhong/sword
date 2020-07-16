package com.lz.sword.common.domain.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色关联菜单或元素
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/16 16:37
 */
@Data
@ApiModel("角色关联菜单元素-Bo")
public class RoleRelationBo {

    @ApiModelProperty("角色Id")
    private Long roleId;

    @ApiModelProperty("关系菜单或元素Id")
    private List<Long> linkIds;

    @ApiModelProperty("类型( 0:菜单 1:页面元素 )")
    private Integer type;

    @ApiModelProperty("取消关联Ids")
    private List<Long> cancelIds;
}
