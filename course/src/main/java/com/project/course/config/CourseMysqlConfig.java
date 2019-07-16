package com.project.course.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-16 15:22
 * @version: 1.0
 * @description:
 */
@Configurable
@EnableJpaRepositories(basePackages = "com.project.course.repository")
public class CourseMysqlConfig {

}
