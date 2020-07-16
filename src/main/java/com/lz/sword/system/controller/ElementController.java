package com.lz.sword.system.controller;

import com.lz.sword.common.annotations.Idempotent;
import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.system.entity.Element;
import com.lz.sword.system.service.ElementService;
import com.lz.sword.basic.BaseController;
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
@RequestMapping("system/element")
@RequiredArgsConstructor
public class ElementController extends BaseController {

    private final ElementService elementService;

    @GetMapping("findByMenuId/{menuId}")
    @ApiOperation("按菜单Id查询")
    public RestVo<List<Element>> findByMenuId(@PathVariable Long menuId) {
        return sendResult(elementService.findByMenuId(menuId));
    }

    @Permission
    @Idempotent
    @PostMapping("save")
    @ApiOperation("保存")
    public RestVo<Boolean> save(@RequestBody Element bo) {
        if (bo.getElementId() == null) {
            bo.setCreator(getUserId());
        }
        bo.setUpdater(getUserId());
        return sendResult(elementService.save(bo));
    }

    @Permission
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public RestVo<Boolean> deleteById(@PathVariable Long id) {
        return sendResult(elementService.deleteById(id));
    }

}
