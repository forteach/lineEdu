package com.project.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 用户信息MYSQL数据扫描配置文件
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.user.repository")
public class UserMysqlConfig {


}
