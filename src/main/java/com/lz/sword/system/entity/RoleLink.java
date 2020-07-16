package com.lz.sword.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色权限关联表 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Table(name = "sys_role_link")
@Data
@Entity
@NoArgsConstructor
public class RoleLink implements Serializable {

    /**
     * 角色关系表主键
     */
    @Id
    @Column(name = "role_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleLinkId;

    /**
     * 角色Id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 页面元素Id
     */
    @Column(name = "element_id")
    private Long elementId;

    /**
     * 类型( 0:菜单 1:页面元素 )
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "gmt_created", updatable = false)
    private LocalDateTime gmtCreated;

    public RoleLink(Long roleId, Long menuId, Long elementId, Integer type) {
        this.roleId = roleId;
        this.menuId = menuId;
        this.elementId = elementId;
        this.type = type;
    }
}