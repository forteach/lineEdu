package com.project.course.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.course.domain.Course;
import com.project.course.domain.CourseEntity;
import com.project.course.domain.CourseImages;
import com.project.course.repository.CourseEntrityRepository;
import com.project.course.repository.CourseRepository;
import com.project.course.repository.CourseStudyRepository;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.service.CourseService;
import com.project.course.web.req.CourseImagesReq;
import com.project.course.web.resp.CourseListResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:59
 * @Version: 1.0
 * @Description:　课程管理
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    /**
     * 课程基本信息
     */
    @Resource
    private CourseRepository courseRepository;

    /**
     * 课程轮播图
     */
    @Resource
    private CourseImagesServiceImpl courseImagesServiceImpl;

    @Resource
    private CourseEntrityRepository courseEntrityRepository;

    @Resource
    private CourseStudyRepository courseStudyRepository;

    /**
     * 保存课程基本信息
     *
     * @param course 课程基本信息
     *               //     * @param teachers 集体备课教师信息
     * @return 课程编号和集体备课资源编号
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public String saveUpdate(Course course) {
        //1、保存课程基本信息
        if (StrUtil.isBlank(course.getCourseId())) {
            course.setCourseId(IdUtil.fastSimpleUUID());
            course.setUpdateUser(course.getCreateUser());
            return courseRepository.save(course).getCourseId();
        } else {
            courseRepository.findById(course.getCourseId()).ifPresent(c -> {
                UpdateUtil.copyProperties(course, c);
                c.setUpdateUser(course.getCreateUser());
                courseRepository.save(c);
            });
        }
        return course.getCourseId();
//        return courseRepository.save(course).getCourseId();

        //2、如果是集体备课，保存集体备课基本信息
//        String shareId = courseShareService.save(course, teachers);

        //3、设置返回数据
//        List<String> result = new ArrayList<String>();
//        result.add(course.getCourseId());
//        result.add(shareId);
//        return course.getCourseId();
    }

//    @Override
//    @Transactional(rollbackForClassName = "Exception")
//    public String edit(Course course) {

    //修改课程信息
//        courseRepository.save(course);

    //判断原有的备课类型是否是集体备课，并修改集体备课信息
//        return courseShareService.update(course.getLessonPreparationType(), oldShareId, course);
//    }

    /**
     * 获得所有可用课程列表
     *
     * @param page
     * @return
     */
    @Override
    public List<ICourseListDto> findAll(PageRequest page) {
        return courseRepository.findByIsValidated(TAKE_EFFECT_OPEN, page)
                .getContent();
    }

    @Override
    public Course findByCourseId(String courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    /**
     * 分页查询我的课程科目
     *
     * @param page
     * @return
     */
    @Override
    public List<ICourseListDto> findMyCourse(String userId, PageRequest page) {
        return courseRepository.findByCreateUserAndIsValidatedOrderByCreateTimeDesc(userId, TAKE_EFFECT_OPEN, page)
                .getContent();
    }

    /**
     * 根据课程编号，获得课程基本信息
     *
     * @param id
     * @return
     */
    @Override
    public Course getById(String id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()){
            return optionalCourse.get();
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "编号对应的课程信息不存在");
        return null;
    }


    /**
     * 学生端查询我的课程信息
     * @param classId
     * @return
     */
    @Override
    @Cacheable(value = "myCourseList", key = "#classId", sync = true, unless = "#result eq null")
    public List<CourseListResp> myCourseList(String classId) {
        List<CourseListResp> listRespList = CollUtil.newArrayList();
        courseRepository.findByIsValidatedEqualsAndCourseIdInOrderByCreateTime(classId)
                .stream()
                .filter(Objects::nonNull)
                .forEach(iCourseChapterListDto -> {
                    listRespList.add(CourseListResp.builder()
                            .courseId(iCourseChapterListDto.getCourseId())
                            .courseName(iCourseChapterListDto.getCourseName())
                            .alias(iCourseChapterListDto.getAlias())
                            .topPicSrc(iCourseChapterListDto.getTopPicSrc())
                            .courseDescribe(iCourseChapterListDto.getCourseDescribe())
                            .joinChapterId(iCourseChapterListDto.getChapterId())
                            .joinChapterName(iCourseChapterListDto.getChapterName())
                            .teacherId(iCourseChapterListDto.getTeacherId())
                            .teacherName(iCourseChapterListDto.getTeacherName())
                            .build());
                });
        return listRespList;
    }

    /**
     * 根据课程ID，获得课程基本信息和集体备课共享编号
     *
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getCourseById(String courseId) {
        Map<String, Object> result = new HashMap<>(2);
        courseRepository.findById(courseId).ifPresent(course -> {
//            String shareId = "";
            //课程为集体备课
//            if (course.getLessonPreparationType().equals(LESSON_PREPARATION_TYPE_GROUP)) {
//                CourseShare cs = courseShareService.findByCourseIdAll(course.getCourseId());
//                shareId = cs.getShareId();
//            }
            result.put("course", course);
//            result.put("shareId", shareId);
        });
        return result;
    }
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void deleteIsValidById(String courseId) {
        courseRepository.findById(courseId)
                .ifPresent(course -> {
                    course.setIsValidated(TAKE_EFFECT_CLOSE);
                    courseRepository.save(course);
                });
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void saveCourseImages(CourseImagesReq courseImagesReq) {
        courseImagesServiceImpl.saveCourseImages(courseImagesReq.getCourseId(), courseImagesReq.getImages());
    }


    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void deleteById(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    /**
     * 查询封面图片信息
     *
     * @param courseId
     * @return
     */
    @Override
    public List<CourseImages> findImagesByCourseId(String courseId) {
        return courseImagesServiceImpl.findImagesByCourseId(courseId);
    }


    /**
     * 查询同步过来的课程信息
     *
     * @return
     */
    @Override
    @Cacheable(value = "allCourseEntity", key = "#root.targetClass", unless = "#result eq null ")
    public List<CourseEntity> findCourseList() {
        return courseEntrityRepository.findByIsValidated(TAKE_EFFECT_OPEN);
    }

    @Override
    public List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus) {
        return courseStudyRepository.findByIsValidatedEqualsAndStudentId(studentId, studyStatus);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteImagesByCourseId(String courseId) {
        return courseImagesServiceImpl.deleteImagesByCourseId(courseId);
    }

    @Override
    public Course findByCourseNumberAndTeacherId(String courseId, String teacherId){
        List<Course> list = courseRepository.findAllByIsValidatedEqualsAndCourseNumberAndCreateUserOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, courseId, teacherId);
        if (!list.isEmpty()){
            return list.get(0);
        }else {
            return new Course();
        }
    }
}