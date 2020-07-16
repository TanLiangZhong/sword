package com.lz.sword.system.service;

import com.google.code.kaptcha.Producer;
import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.RedisKey;
import com.lz.sword.common.domain.system.vo.AuthVo;
import com.lz.sword.common.kaptcha.CodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-1 16:01:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KaptchaService {

    private final Producer codeProducer;
    private final RedisComponent redisComponent;

    /**
     * 获得验证码
     *
     * @return 验证码
     */
    public AuthVo getCaptcha() {
        // 生产图片验证码
        String[] result = CodeComponent.makeBase64Code(codeProducer);
        String imgCode = result[0];
        String code = result[1];
        String token = DigestUtils.md5Hex(String.valueOf(UUID.randomUUID()));
        redisComponent.setValue(RedisKey.CAPTCHA_CODE_KEY.getKey(token), code, 3, TimeUnit.MINUTES);
        return new AuthVo(token, imgCode);
    }

}
