package com.lz.sword.system.service;

import com.lz.sword.common.constant.BaseEnum;
import com.lz.sword.common.constant.Constant;
import com.lz.sword.common.constant.SystemEnum;
import com.lz.sword.common.domain.PageBo;
import com.lz.sword.common.domain.PageVo;
import com.lz.sword.common.domain.system.bo.KeywordSearchBo;
import com.lz.sword.common.domain.system.vo.UserVo;
import com.lz.sword.common.exception.BusinessException;
import com.lz.sword.common.utils.JpaUtils;
import com.lz.sword.common.utils.MyObjectUtil;
import com.lz.sword.common.utils.RandomGenerateUtil;
import com.lz.sword.system.entity.Role;
import com.lz.sword.system.entity.User;
import com.lz.sword.system.repository.UserRepository;
import com.lz.sword.system.repository.UserRoleRepository;
import com.lz.sword.basic.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/15 12:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends BaseServiceImpl<UserRepository, User, Long> {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * 分页
     *
     * @param pageBo 分页对象
     * @return {@link PageVo < UserVo >}
     */
    public PageVo<UserVo> findPage(PageBo<KeywordSearchBo> pageBo) {
        Page<User> page = findPage(pageBo, (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("delFlag"), BaseEnum.DelFlagEnum.NORMAL.getCode()));
            KeywordSearchBo searchBo = pageBo.getParams();
            if (!StringUtils.hasText(searchBo.getKeyword())) {
                String keyword = JpaUtils.addWildcard(searchBo.getKeyword());
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("username"), keyword),
                        criteriaBuilder.like(root.get("nickname"), keyword),
                        criteriaBuilder.like(root.get("phone"), keyword)));
            }
            if (!CollectionUtils.isEmpty(searchBo.getNotInId())) {
                predicates.add(criteriaBuilder.not(root.get("userId").in(searchBo.getNotInId())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        List<UserVo> vos = new ArrayList<>();

        page.getContent().forEach(it -> {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(it, vo);
            vo.setGmtCreatedStr(it.getGmtCreated().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN)));
            vo.setGmtModifiedStr(it.getGmtModified().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN)));
            vo.setRoleNames(userRoleRepository.selectRoleByUserIds(it.getUserId()).stream().map(Role::getName).collect(Collectors.joining(", ")));
            vos.add(vo);
        });
        return new PageVo<>(pageBo.getCurrentPage(), pageBo.getPageSize(), page.getTotalElements(), page.getTotalPages(), vos);
    }

    /**
     * 保存
     *
     * @param bo 实体
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean save(User bo) {
        User entity = new User();
        User old = userRepository.findTopByUsernameAndDelFlag(bo.getUsername(), BaseEnum.DelFlagEnum.NORMAL.getCode());
        if (old != null && !old.getUserId().equals(bo.getUserId())) {
            throw BusinessException.of("账号已存在");
        }
        if (bo.getUserId() != null) {
            entity = userRepository.findById(bo.getUserId()).orElse(entity);
            if (!StringUtils.hasText(bo.getPassword())) {
                String salt = RandomGenerateUtil.getRandomS(0, 6);
                bo.setPassword(DigestUtils.md5Hex(bo.getPassword() + salt));
                bo.setSalt(salt);
            }
        } else {
            String salt = RandomGenerateUtil.getRandomS(0, 6);
            bo.setPassword(DigestUtils.md5Hex(bo.getPassword() + salt));
            bo.setSalt(salt);
            bo.setStatus(SystemEnum.Status.ENABLE.getCode());
            bo.setDelFlag(BaseEnum.DelFlagEnum.NORMAL.getCode());
        }
        BeanUtils.copyProperties(bo, entity, MyObjectUtil.isNullField(bo).toArray(new String[0]));
        userRepository.saveAndFlush(entity);
        return true;
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteById(Long id) {
        userRepository.deleteById(BaseEnum.DelFlagEnum.DELETE.getCode(), id);
        return true;
    }

}
