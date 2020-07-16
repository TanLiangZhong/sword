package com.lz.sword.basic;

import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.Constant;
import com.lz.sword.common.constant.RedisKey;
import com.lz.sword.common.constant.ResultMsg;
import com.lz.sword.common.domain.RestVo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Controller 基础
 *
 * @author 谭良忠
 * @version 1.0 2020/4/1 16:49
 */
public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisComponent redisComponent;

    /**
     * 获取令牌, 不存在返回null
     *
     * @return token
     */
    protected String getToken() {
        return Optional.ofNullable(request.getHeader(Constant.TOKEN)).orElse(request.getParameter(Constant.TOKEN));
    }

    /**
     * 获取用户Id
     *
     * @return 用户Id
     */
    protected Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    protected UserVo getUser() {
        Object user = redisComponent.getValue(RedisKey.AUTH_PREFIX.getKey(getToken()));
        if (user == null) {
            throw BusinessException.of(ResultMsg.LOGIN_FAIL_CREDENTIALS_EXPIRED);
        }
        return (UserVo) redisComponent.getValue(RedisKey.AUTH_PREFIX.getKey(getToken()));
    }

    /**
     * 发送结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return {@link RestVo}
     */
    protected <T> RestVo<T> sendResult(T data) {
        return RestVo.success(data);
    }

    /**
     * 发送结果, success
     *
     * @param <T> 数据类型
     * @return {@link RestVo}
     */
    protected <T> RestVo<T> sendResult() {
        return RestVo.success();
    }

}
