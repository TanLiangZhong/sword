package com.lz.sword.interceptor;

import com.lz.sword.common.annotations.Permission;
import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.Constant;
import com.lz.sword.common.constant.RedisKey;
import com.lz.sword.common.constant.ResultMsg;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * 身份认证拦截
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/14 15:41
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final RedisComponent redisComponent;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("URI: {}", request.getRequestURI());

        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        String methodName = method.getName();
        String token = Optional.ofNullable(request.getHeader(Constant.TOKEN)).orElse(request.getParameter(Constant.TOKEN));

        /*
         * 仅处理带有 Permission 注解的函数, 若没有即视为无需登录也可访问
         * Permission.value 不等于空时, 验证用户权限. 等于空即视为所有登录用户都可访问
         */
        Permission permission = Optional.ofNullable(method.getDeclaredAnnotation(Permission.class)).orElse(clazz.getDeclaredAnnotation(Permission.class));
        if (permission == null) {
            return true;
        }

        // 验证令牌
        if (StringUtils.isEmpty(token)) {
            log.info("令牌为空");
            throw BusinessException.of(ResultMsg.TOKEN_IS_NULL);
        }

        // 令牌失效
        if (!redisComponent.hasKey(RedisKey.AUTH_PREFIX.getKey(token))) {
            log.info("登录过期,请重新登录!, Token: {}", token);
            throw BusinessException.of(ResultMsg.LOGIN_FAIL_CREDENTIALS_EXPIRED);
        }

        // 验证接口权限
        String permissionVal = permission.value();
        if (!StringUtils.isEmpty(permissionVal)) {
            UserVo user = (UserVo) redisComponent.getValue(RedisKey.AUTH_PREFIX.getKey(token));
            List<String> userAuthority = authService.getUserAuthorities(user.getUserId());
            if (!userAuthority.contains(permissionVal)) {
                log.info("无权访问, User: {}, MethodName: {}", user, methodName);
                throw BusinessException.of(ResultMsg.ACCESS_DENIED);
            }
        }
        return true;
    }

}
