package com.lz.sword.system.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 页面元素(按钮，链接) - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Entity
@Table(name = "sys_element")
@Data
public class Element implements Serializable {

    /**
     * 页面元素Id
     */
    @Id
    @Column(name = "element_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elementId;

    /**
     * 父级Id(顶级为0)
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 类型(0:其他 1:按钮 2:链接)
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 权限标识
     */
    @Column(name = "authority")
    private String authority;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 路径
     */
    @Column(name = "href")
    private String href;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 状态 (1-启用 0-禁用)
     */
    @Column(name = "status")
    private Integer status;

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

}