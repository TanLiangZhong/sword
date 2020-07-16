package com.lz.sword.system.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Data
@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable {
    /**
     * 菜单Id
     */
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 父级主键(顶级为0）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 链接
     */
    @Column(name = "component")
    private String href;

    /**
     * 组件
     */
    @Column(name = "href")
    private String component;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 是否显示(1-显示,0-不显示)
     */
    @Column(name = "is_show", length = 1)
    private Integer isShow;

    /**
     * 权限标识
     */
    @Column(name = "authority")
    private String authority;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "gmt_created", updatable = false)
    private LocalDateTime gmtCreated;

    /**
     * 创建人
     */
    @Column(name = "creator", updatable = false)
    private Long creator;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    /**
     * 更新人
     */
    @Column(name = "updater")
    private Long updater;

    /**
     * 删除标记 (1-正常 0-删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

}