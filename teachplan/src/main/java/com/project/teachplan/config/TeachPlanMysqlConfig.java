package com.project.teachplan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 教学计划MYSQL数据扫描配置文件
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.teachplan.repository")
public class TeachPlanMysqlConfig {


}
