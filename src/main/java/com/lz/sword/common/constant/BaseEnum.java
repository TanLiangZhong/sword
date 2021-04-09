package com.lz.sword.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 逻辑删除标记枚举类
 *
 * @author 谭良忠
 * @version 1.0 2020-4-14 10:42:30
 */
public interface BaseEnum {

    /**
     * 逻辑删除标记枚举类
     */
    @Getter
    @AllArgsConstructor
    enum DelFlagEnum {

        NORMAL(0, "正常"),
        DELETE(1, "删除");

        private final Integer code;
        private final String text;
    }


}
