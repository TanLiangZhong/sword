package com.lz.sword.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关系表, 赋予用户的角色 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Entity
@Data
@Table(name = "sys_user_role")
@NoArgsConstructor
public class UserRole implements Serializable {
    /**
     * 用户角色关系表主键
     */
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色Id
     */
    @Column(name = "role_id")
    private Long roleId;

    @CreationTimestamp
    @Column(name = "gmt_created", updatable = false)
    private LocalDateTime gmtCreated;

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}