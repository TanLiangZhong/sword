package com.lz.sword.system.controller;

import com.lz.sword.common.annotations.Idempotent;
import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.vo.DictVo;
import com.lz.sword.basic.BaseController;
import com.lz.sword.system.entity.Dict;
import com.lz.sword.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 数据字典
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/17 19:03
 */
@RestController
@Api(tags = {"数据字典"})
@RequestMapping("system/dict")
@RequiredArgsConstructor
public class DictController extends BaseController {

    private final DictService dictService;

    @CrossOrigin
    @PostMapping("findPage")
    @ApiOperation("分页")
    public RestVo<PageVo<DictVo>> findPage(@RequestBody PageBo<KeywordSearchBo> pageBo) {
        return sendResult(dictService.findPage(pageBo));
    }

    @Permission
    @Idempotent
    @PostMapping("save")
    @ApiOperation("保存")
    public RestVo<Boolean> save(@RequestBody Dict bo) {
        if (bo.getDictId() == null) {
            bo.setCreator(getUserId());
        }
        bo.setUpdater(getUserId());
        return sendResult(dictService.save(bo));
    }

    @Permission
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public RestVo<Boolean> deleteById(@PathVariable Long id) {
        return sendResult(dictService.deleteById(id));
    }

}
