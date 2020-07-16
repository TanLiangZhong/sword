package com.lz.sword.system.controller;

import com.lz.sword.common.annotations.Idempotent;
import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.system.entity.User;
import com.lz.sword.system.service.UserService;
import com.lz.sword.basic.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 10:23
 */
@RestController
@Api(tags = {"用户"})
@RequestMapping("system/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("findPage")
    @ApiOperation("分页")
    public RestVo<PageVo<UserVo>> findPage(@RequestBody PageBo<KeywordSearchBo> pageBo) {
        return sendResult(userService.findPage(pageBo));
    }

    @Permission
    @Idempotent
    @PostMapping("save")
    @ApiOperation("保存")
    public RestVo<Boolean> save(@RequestBody User bo) {
        if (bo.getUserId() == null) {
            bo.setCreator(getUserId());
        }
        bo.setUpdater(getUserId());
        return sendResult(userService.save(bo));
    }

    @Permission
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public RestVo<Boolean> deleteById(@PathVariable Long id) {
        return sendResult(userService.deleteById(id));
    }

}
