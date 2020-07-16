package com.lz.sword.system.controller;

import com.lz.sword.common.annotations.Idempotent;
import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.vo.MenuVo;
import com.lz.sword.basic.BaseController;
import com.lz.sword.system.entity.Menu;
import com.lz.sword.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 10:23
 */
@RestController
@Api(tags = {"菜单"})
@RequestMapping("system/menu")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;

    @GetMapping("findListByUserId")
    @ApiOperation("获得用户可用菜单列表")
    public RestVo<List<Menu>> findListByUserId() {
        return sendResult(menuService.findListByUserId(getUserId()));
    }

    @GetMapping("tree")
    @ApiOperation("菜单树")
    public RestVo<List<MenuVo>> getTree() {
        return sendResult(menuService.getTree());
    }

    @Permission
    @Idempotent
    @PostMapping("save")
    @ApiOperation("保存")
    public RestVo<Boolean> save(@RequestBody Menu bo) {
        if (bo.getMenuId() == null) {
            bo.setCreator(getUserId());
        }
        bo.setUpdater(getUserId());
        return sendResult(menuService.save(bo));
    }

    @Permission
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public RestVo<Boolean> deleteById(@PathVariable Long id) {
        return sendResult(menuService.deleteById(id));
    }

}
