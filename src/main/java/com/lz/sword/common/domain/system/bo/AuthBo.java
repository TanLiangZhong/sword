package com.lz.sword.common.domain.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 认证
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-13 19:00:48
 */
@Data
@ApiModel("认证-Bo")
public class AuthBo {

    @ApiModelProperty("令牌")
    private String token;

    @NotNull(message = "账号为空")
    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("登录密码")
    @NotNull(message = "登录密码为空")
    private String password;

    @ApiModelProperty("图形验证码")
    private String captcha;

    @ApiModelProperty(value = "令牌", hidden = true)
    private Long userId;
}
