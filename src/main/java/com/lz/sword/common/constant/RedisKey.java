package com.lz.sword.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存key
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/4 12:12
 */
@Getter
@AllArgsConstructor
public enum RedisKey {

    IDEMPOTENT_PREFIX("IDEMPOTENT:%s", "提交幂等"),
    AUTH_PREFIX("USER:AUTH:%s", "认证Token前缀"),
    CAPTCHA_CODE_KEY("AUTH:IMG:%s", "图片验证码"),
    ;

    private final String key;
    private final String desc;

    public String getKey(Object... keys) {
        return String.format(key, keys);
    }
}
