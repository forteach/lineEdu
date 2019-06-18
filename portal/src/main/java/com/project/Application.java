package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/29 13:52
 */
@SpringBootApplication
public class Application {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
//        applicationContext = SpringApplication.run(Application.class, args);
        SpringApplication.run(Application.class, args);
    }
}
