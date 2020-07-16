package com.lz.sword.common.constant;

import com.lz.sword.common.utils.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 逻辑删除标记枚举类
 *
 * @author 谭良忠
 * @version 1.0 2020-4-14 10:42:30
 */
public interface SystemEnum {

    /**
     * 角色关联关系类型
     */
    @Getter
    @AllArgsConstructor
    enum RoleLinkType implements CodeEnum {
        MENU(0, "菜单"),
        ELEMENT(1, "页面元素");

        private final Integer code;
        private final String text;
    }

    /**
     * 状态
     */
    @Getter
    @AllArgsConstructor
    enum Status implements CodeEnum{

        ENABLE(1, "启用"),
        DISABLE(0, "禁用");

        private final Integer code;
        private final String text;
    }

}
