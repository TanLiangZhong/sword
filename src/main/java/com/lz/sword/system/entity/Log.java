package com.lz.sword.system.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志 - Entity
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Entity
@Table(name = "sys_log")
@Data
public class Log implements Serializable {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 模块名称
     */
    @Column(name = "modules")
    private String modules;

    /**
     * 用户操作
     */
    @Column(name = "operation")
    private String operation;

    /**
     * 请求参数
     */
    @Column(name = "params")
    private String params;

    /**
     * 请求方法
     */
    @Column(name = "method")
    private String method;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 耗时
     */
    @Column(name = "time_consuming")
    private Long timeConsuming;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "gmt_created", updatable = false)
    private LocalDateTime gmtCreated;

}