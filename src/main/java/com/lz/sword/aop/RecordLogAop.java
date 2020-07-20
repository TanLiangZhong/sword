package com.lz.sword.aop;

import com.alibaba.fastjson.JSON;
import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.Constant;
import com.lz.sword.common.constant.RedisKey;
import com.lz.sword.common.domain.system.bo.AuthBo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.utils.IpUtil;
import com.lz.sword.system.entity.Log;
import com.lz.sword.system.service.LogService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 记录日志切面
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/17 19:44
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sword.record-log", name = "enable", havingValue = "true")
public class RecordLogAop {

    private final ThreadLocal<Long> time = new ThreadLocal<>();

    private final LogService logService;
    private final HttpServletRequest request;
    private final RedisComponent redisComponent;

    @Before("@annotation(io.swagger.annotations.ApiOperation)")
    private void beforeExec(JoinPoint joinPoint) {
        time.set(System.currentTimeMillis());
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        Object[] args = joinPoint.getArgs();
        ApiOperation recordLog = method.getDeclaredAnnotation(ApiOperation.class);
        log.info("Run `{}#{}` start.", clazz.getName(), method.getName());
        log.info("Desc: {}", recordLog.value());
        log.info("Args: {}", JSON.toJSONString(args));
    }


    @After("@annotation(io.swagger.annotations.ApiOperation)")
    private void afterExec(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        Object[] args = joinPoint.getArgs();
        ApiOperation recordLog = method.getDeclaredAnnotation(ApiOperation.class);
        Long timeConsuming = System.currentTimeMillis() - time.get();
        log.info("Run `{}#{}` end.  Time consuming: {}", clazz.getName(), method.getName(), timeConsuming);

        // 日志保存至数据库
        Log sysLog = new Log();
        String token = Optional.ofNullable(request.getHeader(Constant.TOKEN)).orElse(Constant.TOKEN);
        UserVo user = (UserVo) redisComponent.getValue(RedisKey.AUTH_PREFIX.getKey(token));
        if (user != null) {
            sysLog.setUserId(user.getUserId());
            sysLog.setUsername(user.getUsername());
        } else {
            if (Constant.USER_LOGIN_OPERATION.equals(recordLog.value())) {
                AuthBo authBo = (AuthBo) args[0];
                sysLog.setUserId(authBo.getUserId());
                sysLog.setUsername(authBo.getUsername());
            }
        }
        sysLog.setOperation(recordLog.value());
        sysLog.setParams(JSON.toJSONString(args));
        sysLog.setMethod(clazz.getName() + "#" + method.getName());
        sysLog.setIp(IpUtil.getIp());
        sysLog.setTimeConsuming(timeConsuming);
        logService.save(sysLog);
        time.remove();
    }

}
