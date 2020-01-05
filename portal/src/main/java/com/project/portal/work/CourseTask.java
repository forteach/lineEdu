package com.project.portal.work;

import com.project.course.service.CourseRecordsService;
import com.project.course.service.CourseService;
import com.project.databank.service.CourseVerifyVoService;
import com.project.schoolroll.service.LearnCenterService;
import com.project.schoolroll.service.StudentScoreService;
import com.project.teachplan.service.TeachService;
import com.project.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private LearnCenterService learnCenterService;
    @Resource
    private UserService userService;
    @Resource
    private StudentScoreService studentScoreService;

    /**
     * 同步教师端修改的习题记录
     * TODO
     */
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
    })
    @Async
    public void asyncRedisQuestion() {
        log.info("start course redis question async ==> ");
        // 定时查询客户端修改的习题
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseVerifyVoService.taskQuestionRedis();
        log.info(" <== end course redis question async ");
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
        // 定时查询学生课程学习记录
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
        // 定时查询学生学习情况
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        courseService.taskCourseStudy();
        log.info(" <== end course study async ");
    }


    /**
     * 统计计算学习课程章节习题数量,生成快照数量,回答正确的数量
     */
    @Schedules({
            @Scheduled(cron = "0 0/1 * * * ?")
//            @Scheduled(cron = "0 0 0/1 * * ?")
    })
    @Async
    public void asyncCourseQuestions() {
        log.info("start course questions async ==> ");
        // 定时查询课程习题信息
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
    public void asyncCourseStudentScore() throws InterruptedException {
        log.info("start course student score async ==> ");
        // 定时查询计划,修改计划状态,计算学生成绩
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        //修改教学计划状态
        teachService.taskPlanStatus();
        //暂停10秒
//        Thread.sleep(10000);
        //计算计划对应的学生成绩
        teachService.taskOnLineCourseScore();
        //暂停10秒
//        Thread.sleep(10000);
        //查询计算没有完成计算的成绩
        studentScoreService.taskCompleteCourseScore();
        log.info(" <== end course student score async ");
    }

    /**
     * 修改学习中心状态，并使帐号失效
     */
    @Schedules({
//            @Scheduled(cron = "0 10 3 * * ?")
            @Scheduled(cron = "0 0 0/1 * * ?")
    })
    @Async
    public void asyncCenter() {
        log.info("start center status async ==> ");
        // 修改学习中心,修改学习中心状态
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        //修改学习中心状态
        List<String> centerList = learnCenterService.findCenterListByEndDate();
        //使学习中心的登录账号失效
        userService.updateCenterUsers(centerList);
        log.info(" <== end center update status async ");
    }

    /** 计算所有课程都完成没，是否达到毕业条件*/
    @Schedules({
//            @Scheduled(cron = "0 10 3 * * ?")
            @Scheduled(cron = "0 0 0/2 * * ?")
    })
    @Async
    public void computeFinishSchool(){
        log.info("start compute student finish school async ==> ");
        // 修改学习中心,修改学习中心状态
        if (log.isDebugEnabled()) {
            log.debug("task thread name : {}", Thread.currentThread().getName());
        }
        //查询计划结束的学生对应的课程学习情况,是否成绩及格和全部课程毕业
        teachService.computeFinishSchool();
        log.info(" <== end compute student finish school async");
    }
}