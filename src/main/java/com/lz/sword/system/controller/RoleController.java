package com.lz.sword.system.controller;

import com.lz.sword.common.annotations.Idempotent;
import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.bo.RoleRelationBo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.system.service.RoleService;
import com.lz.sword.basic.BaseController;
import com.lz.sword.system.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/15 00:08
 */
@RestController
@Api(tags = {"角色"})
@RequestMapping("system/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService roleService;

    @PostMapping("findPage")
    @ApiOperation("分页")
    public RestVo<PageVo<Role>> findPage(@RequestBody PageBo<KeywordSearchBo> pageBo) {
        return sendResult(roleService.findPage(pageBo));
    }

    @Permission
    @Idempotent
    @PostMapping("save")
    @ApiOperation("保存")
    public RestVo<Boolean> save(@RequestBody Role bo) {
        if (bo.getRoleId() == null) {
            bo.setCreator(getUserId());
        }
        bo.setUpdater(getUserId());
        return sendResult(roleService.save(bo));
    }

    @Permission
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public RestVo<Boolean> deleteById(@PathVariable Long id) {
        return sendResult(roleService.deleteById(id));
    }

    @Permission
    @Idempotent
    @PostMapping("add/link")
    @ApiOperation("权限关联菜单,元素")
    public RestVo<Boolean> batchAddRoleLink(@RequestBody RoleRelationBo bo) {
        return sendResult(roleService.batchAddRoleLink(bo));
    }

    @GetMapping("find/linkIds")
    @ApiOperation("查询权限关联表ID")
    public RestVo<List<Long>> findLinkIds(@RequestParam Long roleId, @RequestParam Integer type) {
        return sendResult(roleService.findLinkIds(roleId, type));
    }

    @GetMapping("selectUserByRoleId")
    @ApiOperation("根据角色Id查询用户")
    public RestVo<List<UserVo>> selectUserByRoleId(@RequestParam Long roleId) {
        return sendResult(roleService.selectUserByRoleId(roleId));
    }

    @Permission
    @Idempotent
    @PostMapping("relatedUser")
    @ApiOperation("关联用户")
    public RestVo<Boolean> relatedUser(@RequestBody RoleRelationBo bo) {
        return sendResult(roleService.relatedUser(bo));
    }

    @Permission
    @DeleteMapping("cancelRelatedUser")
    @ApiOperation("取消关联用户")
    public RestVo<Boolean> cancelRelatedUser(@RequestBody RoleRelationBo bo) {
        return sendResult(roleService.cancelRelatedUser(bo));
    }


}
