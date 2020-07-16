package com.lz.sword.system.controller;

import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.basic.BaseController;
import com.lz.sword.system.entity.Log;
import com.lz.sword.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/17 19:03
 */
@RestController
@Api(tags = {"系统日志"})
@RequestMapping("system/log")
@RequiredArgsConstructor
public class LogController extends BaseController {

    private final LogService logService;

    @PostMapping("findPage")
    @ApiOperation("分页")
    public RestVo<PageVo<Log>> findPage(@RequestBody PageBo<KeywordSearchBo> pageBo) {
        return sendResult(logService.findPage(pageBo));
    }

}
