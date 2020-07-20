package com.lz.sword.common.utils;

/**
 * 枚举
 *
 * @author Tan
 * @version 1.0, 2020/5/24 14:58
 */
public interface CodeEnum {
    /**
     * code
     *
     * @param <T> t
     * @return t
     */
    <T> T getCode();

    /**
     * 描述
     *
     * @return 描述
     */
    String getText();
}