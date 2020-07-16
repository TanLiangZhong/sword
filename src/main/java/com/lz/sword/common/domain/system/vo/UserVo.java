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
 * 用户-Vo
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-13 19:00:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo implements Serializable {

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("机构Id")
    private Long branchId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("介绍")
    private String introduction;

    @ApiModelProperty("状态值(1-正常, 0-禁用)")
    private Integer status;

    @ApiModelProperty("创建时间")
    private String gmtCreatedStr;

    @ApiModelProperty("创建人")
    private String creatorName;

    @ApiModelProperty("更新时间")
    private String gmtModifiedStr;

    @ApiModelProperty("更新人")
    private String updaterName;

    @ApiModelProperty("菜单权限")
    private List<String> menuAuthority;

    @ApiModelProperty("页面元素权限")
    private List<String> elementAuthority;

    @ApiModelProperty("用户角色")
    private String roleNames;
}