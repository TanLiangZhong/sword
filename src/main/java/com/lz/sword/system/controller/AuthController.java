package com.lz.sword.system.controller;

import com.lz.sword.basic.BaseController;
import com.lz.sword.common.constant.Constant;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.bo.AuthBo;
import com.lz.sword.common.domain.system.vo.AuthVo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.system.service.AuthService;
import com.lz.sword.system.service.ElementService;
import com.lz.sword.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 身份认证
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 10:23
 */
@RestController
@Api(tags = {"认证控制器"})
@RequestMapping("system/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;
    private final MenuService menuService;
    private final ElementService elementService;

    @PostMapping("login")
    @ApiOperation(Constant.USER_LOGIN_OPERATION)
    public RestVo<AuthVo> login(@Validated @RequestBody AuthBo bo) {
        return sendResult(authService.login(bo));
    }

    @GetMapping("currentUser")
    @ApiOperation("获取当前用户")
    public RestVo<UserVo> currentUser() {
        UserVo user = getUser();
        user.setMenuAuthority(menuService.getUserAuthority(user.getUserId()));
        user.setElementAuthority(elementService.getUserAuthority(user.getUserId()));
        return sendResult(user);
    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public RestVo<Boolean> logout() {
        return sendResult(authService.logout(getToken()));
    }

}
