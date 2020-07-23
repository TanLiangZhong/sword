package com.lz.sword.common.constant;

import com.lz.sword.common.utils.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果消息
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/4 12:12
 */
@Getter
@AllArgsConstructor
public enum ResultMsg implements CodeEnum {

    SUCCESS("S0000", "操作成功"),
    ERROR("E0000", "系统异常"),
    ARGUMENT_NOT_VALID("E0001", "Argument Not Valid Exception"),

    FAIL("F0000", "操作失败"),
    LOGIN_FAIL_WRONG_PASSWORD("F0001", "账户名或者密码输入错误!"),
    LOGIN_FAIL_CREDENTIALS_EXPIRED("F0002", "登录过期,请重新登录!"),
    ACCESS_DENIED("F0003", "无权访问!"),
    TOKEN_IS_NULL("F0004", "令牌为空!"),
    CAPTCHA_ERROR("F0005", "验证码错误!"),
    DATA_NOT_FOUND("F00006", "没有找到记录"),
    IMG_CODE_ERROR("F0007", "图片验证码错误或已过期"),
    LOGIN_FAIL_ACCOUNT_ABNORMAL("F0008", "登录失败帐户异常,请与管理员联系!"),
    PARAM_IS_NULL("F0009", "参数为空"),
    ;

    private final String code;
    private final String text;
}
