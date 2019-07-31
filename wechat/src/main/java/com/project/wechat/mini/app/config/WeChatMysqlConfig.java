package com.project.wechat.mini.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/1 00:03
 * @Version: 1.0
 * @Description:
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.project.wechat.mini.app.repository")
public class WeChatMysqlConfig {
}
