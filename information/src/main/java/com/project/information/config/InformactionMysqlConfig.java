package com.project.information.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 资讯MYSQL数据扫描配置文件
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.information.repository")
public class InformactionMysqlConfig {


}
