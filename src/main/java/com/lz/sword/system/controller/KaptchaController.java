package com.lz.sword.system.controller;

import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.vo.AuthVo;
import com.lz.sword.basic.BaseController;
import com.lz.sword.system.service.KaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图形验证码
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 10:23
 */
@Api(tags = "图形验证码")
@RequestMapping("system/kaptcha")
@RestController
@RequiredArgsConstructor
public class KaptchaController extends BaseController {

    private final KaptchaService kaptchaService;

    @GetMapping("captcha")
    @ApiOperation("获取验证码")
    public RestVo<AuthVo> getCaptcha() {
        return sendResult(kaptchaService.getCaptcha());
    }
}
