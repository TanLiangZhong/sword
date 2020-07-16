package com.lz.sword.aop;

import com.alibaba.fastjson.JSON;
import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.RedisKey;
import com.lz.sword.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 幂等 - Aop
 *
 * @author Tan
 * @version 1.1 2020/7/9 15:44
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAop {

    private final HttpServletRequest request;
    private final RedisComponent redisComponent;

    private final static long KEY_EXPIRE = 5L;

    @Around("@annotation(com.lz.sword.common.annotations.Idempotent)")
    public Object doAround(final ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse(request.getParameter(HttpHeaders.AUTHORIZATION));
        log.info("Method:`{}#{}`,\nToken: {} ,\nArgs: {}", clazz.getName(), method.getName(), token, JSON.toJSONString(args));
        String key = RedisKey.IDEMPOTENT_PREFIX.getKey(DigestUtils.md5Hex(clazz.getName() + method.getName() + token + JSON.toJSONString(args)));
        log.info("Key : {}", key);
        if (redisComponent.setNx(key, method.getName(), KEY_EXPIRE)) {
            Object result = pjp.proceed();
            redisComponent.delete(key);
            return result;
        } else {
            log.info("handleSubmit ===> 重复操作, Method:`{}#{}`,\nToken: {} ,\nArgs: {}", clazz.getName(), method.getName(), token, JSON.toJSONString(args));
            throw BusinessException.of("亲，您已操作过，请勿重复操作");
        }
    }

}
