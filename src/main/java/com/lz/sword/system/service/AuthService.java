package com.lz.sword.system.service;

import com.lz.sword.common.components.RedisComponent;
import com.lz.sword.common.constant.*;
import com.lz.sword.common.domain.system.bo.AuthBo;
import com.lz.sword.common.domain.system.vo.AuthVo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.system.entity.*;
import com.lz.sword.system.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 身份认证
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-1 16:01:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisComponent redisComponent;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleLinkRepository roleLinkRepository;
    private final MenuRepository menuRepository;
    private final ElementRepository elementRepository;

    /**
     * 登录
     *
     * @return token
     */
    public AuthVo login(AuthBo bo) {
        String imgCode = (String) redisComponent.getValue(RedisKey.CAPTCHA_CODE_KEY.getKey(bo.getToken()));
        if (StringUtils.isEmpty(bo.getCaptcha()) || !bo.getCaptcha().equals(imgCode)) {
            throw BusinessException.of(ResultMsg.CAPTCHA_ERROR);
        }
        User user = userRepository.findTopByUsernameAndDelFlag(bo.getUsername(), BaseEnum.DelFlagEnum.NORMAL.getCode());
        if (user == null) {
            throw BusinessException.of(ResultMsg.LOGIN_FAIL_WRONG_PASSWORD);
        }
        if (!SystemEnum.Status.ENABLE.getCode().equals(user.getStatus()))
            throw BusinessException.of(ResultMsg.LOGIN_FAIL_ACCOUNT_ABNORMAL);

        if (!DigestUtils.md5Hex(bo.getPassword() + user.getSalt()).equals(user.getPassword())) {
            throw BusinessException.of(ResultMsg.LOGIN_FAIL_WRONG_PASSWORD);
        }
        String token = generateAccessToken();
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        redisComponent.setValue(RedisKey.AUTH_PREFIX.getKey(token), vo, Constant.ACCESS_TOKEN_EXPIRE, TimeUnit.DAYS);

        bo.setUserId(user.getUserId());
        return new AuthVo(token, null);
    }

    /**
     * 生成访问令牌
     *
     * @return token
     */
    private String generateAccessToken() {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(UUID.randomUUID().toString().getBytes());
            byte[] messageDigest = algorithm.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new BusinessException("生成Access Token失败", e);
        }
    }

    /**
     * 登出
     *
     * @param token 令牌
     * @return true
     */
    public Boolean logout(String token) {
        return redisComponent.delete(RedisKey.AUTH_PREFIX.getKey(token));
    }

    /**
     * 获取用户所有权限
     *
     * @param userId 用户id
     * @return 权限
     */
    public List<String> getUserAuthorities(Long userId) {
        Set<Long> roleIds = userRoleRepository.findAllByUserId(userId).stream().map(UserRole::getUserId).collect(Collectors.toSet());
        List<RoleLink> roleLinks = roleLinkRepository.findAllByRoleIdIn(roleIds);
        Set<Long> menuIds = new HashSet<>();
        Set<Long> elementIds = new HashSet<>();
        roleLinks.forEach(it -> {
            if (it.getElementId() != null) {
                elementIds.add(it.getElementId());
            }
            if (it.getMenuId() != null) {
                menuIds.add(it.getMenuId());
            }
        });
        List<String> authorities = menuRepository.findAllByMenuIdInAndDelFlagOrderBySort(menuIds, BaseEnum.DelFlagEnum.NORMAL.getCode()).stream().map(Menu::getAuthority).collect(Collectors.toList());
        authorities.addAll(elementRepository.findAllByElementIdIn(elementIds).stream().map(Element::getAuthority).collect(Collectors.toList()));
        return authorities;
    }
}
