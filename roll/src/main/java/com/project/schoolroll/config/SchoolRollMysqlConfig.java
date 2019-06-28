package com.project.schoolroll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 学籍信息MYSQL数据扫描配置文件
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.schoolroll.repository")
public class SchoolRollMysqlConfig {


}
