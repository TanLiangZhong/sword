package com.lz.sword.common.annotations;

import java.lang.annotation.*;

/**
 * 权限认证注解
 *
 * @author 谭良忠
 * @version 1.0 2020-4-14 17:32:47
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    /**
     * 权限标识
     *
     * @return 权限
     */
    String value() default "";

}
