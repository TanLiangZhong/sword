package com.lz.sword.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/7 14:08
 */
public class EnumUtil {

    /**
     * 通过code获取枚举
     *
     * @param code      code
     * @param enumClass 枚举类
     * @param <T>       枚举类
     * @return T
     */
    public static <T extends CodeEnum> T getEnumByCode(Object code, Class<T> enumClass) {
        return getEnumList(enumClass).stream().filter(it -> it.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * 获取枚举集合
     *
     * @param enumClass 枚举
     * @param <T>       枚举类
     * @return T
     */
    public static <T extends CodeEnum> List<T> getEnumList(Class<T> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }
}
