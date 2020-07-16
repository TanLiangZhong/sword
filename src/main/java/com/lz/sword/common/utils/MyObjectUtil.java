package com.lz.sword.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * java 对象 工具类
 *
 * @author liangzhong.tan
 * @version 1.0 2020-04-03 22:27
 */
@Slf4j
public class MyObjectUtil {


    /**
     * 获取空值字段
     *
     * @param source 资源
     * @return {@link Set} 空字段集合
     */
    public static Set<String> isNullField(Object source) {
        if (source == null) {
            return Collections.emptySet();
        }
        Set<String> isNullFields = new HashSet<>(16);
        Field[] fields = source.getClass().getDeclaredFields();
        Stream.of(fields).forEach(it -> {
            try {
                it.setAccessible(true);
                if (it.get(source) == null) {
                    isNullFields.add(it.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return isNullFields;
    }


    /**
     * 对象转换为 Map
     *
     * @param source 资源
     * @return {@link Map}
     */
    private static Map<String, Object> toMap(Object source) {
        if (source == null) {
            return Collections.emptyMap();
        }
        HashMap<String, Object> map = new HashMap<>(16);
        Field[] fields = source.getClass().getDeclaredFields();
        Stream.of(fields).forEach(it -> {
            try {
                it.setAccessible(true);
                map.put(it.getName(), it.get(source));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;
    }

}
