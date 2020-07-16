package com.lz.sword.common.utils;

/**
 * jpa 工具类
 *
 * @author 谭良忠
 * @version 1.0 2020/4/7 14:08
 */
public class JpaUtils {

    /**
     * 转换参数 , 前后添加通配符
     *
     * @param param 参数
     * @return "%" + param + "%"
     */
    public static String addWildcard(String param) {
        return param == null ? null : "%" + param + "%";
    }

}
