package com.lz.sword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 18:00:24
 */
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class SwordApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwordApplication.class, args);
    }

}
