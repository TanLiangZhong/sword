package com.lz.sword.system.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Entity
@Table(name = "sys_dict")
@Data
public class Dict implements Serializable {

    /**
     * 字典Id
     */
    @Id
    @Column(name = "dict_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dictId;

    /**
     * 父级Id(顶级为0)
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 字典key值
     */
    @Column(name = "tag")
    private String tag;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

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
}