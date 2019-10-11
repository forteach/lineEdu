package com.project.databank.work;

import com.project.databank.service.CourseVerifyVoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import javax.annotation.Resource;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-10 11:38
 * @version: 1.0
 * @description:
 */
@Slf4j
@Configuration
public class CourseVerifyTask {

    @Resource
    private CourseVerifyVoService courseVerifyVoService;
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
    })
    public void asyncRedisQuestion(){
        log.info("start course question async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()){
            log.debug("执行线程 : {}", Thread.currentThread().getName());
        }
        courseVerifyVoService.taskRedis();
        log.info(" <== end course question async ");
    }
}
