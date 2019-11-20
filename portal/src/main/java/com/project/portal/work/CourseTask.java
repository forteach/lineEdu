package com.project.portal.work;

import com.project.course.service.CourseRecordsService;
import com.project.course.service.CourseService;
import com.project.databank.service.CourseVerifyVoService;
import com.project.teachplan.service.TeachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import javax.annotation.Resource;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-1 14:53
 * @version: 1.0
 * @description:
 */
@Slf4j
@Configuration
public class CourseTask {
    @Resource
    private CourseVerifyVoService courseVerifyVoService;
    @Resource
    private CourseRecordsService courseRecordsService;
    @Resource
    private CourseService courseService;
    @Resource
    private TeachService teachService;

    /**
     * 同步教师端修改的习题记录
     */
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
    })
    @Async
    public void asyncRedisQuestion() {
        log.info("start course question async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseVerifyVoService.taskQuestionRedis();
        log.info(" <== end course question async ");
    }

    /**
     * 同步学生学习的时间
     */
    @Schedules({
//            @Scheduled(cron = "0 0 0/2 * * ?")
            @Scheduled(cron = "0 0/1 * * * ?")
    })
    @Async
    public void asyncCourseRecordsCount() {
        log.info("start course Records async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseRecordsService.taskCourseRecordsSum();
        log.info(" <== end course Records async ");
    }


    /**
     * 统计学习课程占比
     */
    @Schedules({
//            @Scheduled(cron = "0 0 0/2 * * ?")
            @Scheduled(cron = "0 0/1 * * * ?")
    })
    @Async
    public void asyncCourseStudyCount() {
        log.info("start course study async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseService.taskCourseStudy();
        log.info(" <== end course study async ");
    }


    /**
     * 统计学习课程占比
     */
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
//            @Scheduled(cron = "0 0 0/1 * * ?")
    })
    @Async
    public void asyncCourseQuestions() {
        log.info("start course questions async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseService.taskCourseQuestions();
        log.info(" <== end course questions async ");
    }

    /**
     * 统计学习课程占比
     */
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
//            @Scheduled(cron = "0 0 0/1 * * ?")
    })
    @Async
    public void asyncCourseStudentScore() {
        log.info("start course student score async ==> ");
        // 定时查询习题信息
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        teachService.taskPlanStatus();
        teachService.taskOnLineCourseScore();
        log.info(" <== end course student score async ");
    }
}