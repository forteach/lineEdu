package com.project.coursedata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-16 15:15
 * @version: 1.0
 * @description:
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.coursedata.repository")
public class CourseDataMysqlConfig {

}
