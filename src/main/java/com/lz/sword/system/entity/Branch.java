package com.lz.sword.system.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Entity
@Table(name = "sys_branch")
@Data
public class Branch implements Serializable {

    /**
     * 机构Id
     */
    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    /**
     * 父级Id(最上级为0)
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 祖先级Id , 3,2,1
     */
    @Column(name = "ancestor_Id")
    private String ancestorId;

    /**
     * 唯一编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 机构名称
     */
    @Column(name = "name")
    private String name;

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

    /**
     * 删除标记 (1-正常 0-删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

}