package com.project.classfee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 课时费MYSQL数据扫描配置文件
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.classfee.repository")
public class ClassFeeMysqlConfig {


}
