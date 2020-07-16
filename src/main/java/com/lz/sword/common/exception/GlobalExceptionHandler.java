package com.lz.sword.common.exception;

import com.lz.sword.common.domain.RestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * 全局异常拦截
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/4 12:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param ex {@link BusinessException}
     * @return RestVo
     */
    @ExceptionHandler(BusinessException.class)
    public RestVo<String> handleException(BusinessException ex) {
        RestVo<String> restVo = RestVo.fail();
        if (ex.getResultMsg() != null) {
            restVo.setCode(ex.getResultMsg().getCode());
        }
        restVo.setMsg(ex.getMessage());
        log.error("业务异常 [code => {}, msg => {}]", restVo.getCode(), restVo.getMsg());
        return restVo;
    }

    /**
     * 处理系统异常
     *
     * @param ex      {@link Exception}
     * @param request 请求
     * @return RestVo
     */
    @ExceptionHandler(Exception.class)
    public RestVo<String> handleException(Exception ex, HttpServletRequest request) {
        StringBuilder message = new StringBuilder();
        message.append("\n######################### Error #########################\n");
        message.append("RequestURI: ").append(request.getRequestURI()).append("\n");
        message.append("Method: ").append(request.getMethod()).append("\n");
        message.append("Headers: \n");
        Iterator<String> headerIterator = CollectionUtils.toIterator(request.getHeaderNames());
        while (headerIterator.hasNext()) {
            String name = headerIterator.next();
            message.append("\t").append(name).append(": ").append(request.getHeader(name)).append("\n");
        }
        log.info(message.toString(), ex);
        return RestVo.error();
    }

}
