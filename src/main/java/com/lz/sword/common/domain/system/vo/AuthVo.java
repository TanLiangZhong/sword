package com.lz.sword.common.domain.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 认证
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-13 19:00:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("认证-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthVo implements Serializable {

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("验证码")
    private String captcha;
}
