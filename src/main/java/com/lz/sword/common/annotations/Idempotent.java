package com.lz.sword.common.annotations;

import java.lang.annotation.*;

/**
 * 幂等提交
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/8 10:22
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

}
