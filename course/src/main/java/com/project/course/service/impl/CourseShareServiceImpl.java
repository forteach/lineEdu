package com.project.course.service.impl;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-5 15:36
 * @Version: 1.0
 * @Description: 课程章节分享范围  章节全部分享和部分分享，分享给的那些教师
 * <p>
 * 存储集体备课
 * <p>
 * //     * @param course      课程基本信息
 * //     * @return ShareId 课程分享编号
 * <p>
 * //     * @param course      课程基本信息
 * //     * @return ShareId 新生成课程分享编号
 * <p>
 * 根据共享编号，获得教师列表
 * <p>
 * //     * @param shareId
 * //     * @return
 * <p>
 * 获得课程共享范围是---所有的资源信息
 * <p>
 * //     * @param courseId
 * //     * @return
 */
//@Slf4j
//@Service
//public class CourseShareServiceImpl implements CourseShareService {

//    @Resource
//    private CourseShareRepository courseShareRepository;

//    @Resource
//    private CourseShareUsersRepository courseShareUsersRepository;


/**
 * 存储集体备课
 *
 //     * @param course      课程基本信息
 //     * @return ShareId 课程分享编号
 */
//    @Transactional(rollbackForClassName = "Exception")
//    @Override
//    public String save(Course course) {

//1、保存课程分享范围（ALL全部分享 PART部分分享）
//        CourseShare courseShare = CourseShare.builder()
//分享整套课程
//                .shareArea(COURSE_SHARE_AREA_ALL)
//                .build();
//        courseShare.setCreateTime(course.getCreateTime());
//        BeanUtil.copyProperties(course, courseShare);
//        CourseShare newCourseShare = courseShareRepository.save(courseShare);
//        MyAssert.blank(newCourseShare.getShareId(), DefineCode.ERR0009, "集体课程信息存储错误");

//2、保存参与分享的教师信息
//        List<CourseShareUsers> list = new ArrayList<>();
//        teacherList.forEach(teacher -> {
//            CourseShareUsers cs = CourseShareUsers.builder()
//                    //用户编号
//                    .userId(teacher.getTeacherId())
//                    .userName(teacher.getTeacherName())
//                    .build();
//            cs.setCreateTime(courseShare.getCreateTime());
//            BeanUtil.copyProperties(courseShare, cs);
//            list.add(cs);
//        });
//        List<CourseShareUsers> newlist = courseShareUsersRepository.saveAll(list);

//        MyAssert.isTrue(newlist.size() == 0, DefineCode.ERR0009, "集体课程教师信息存储错误");

//3、集体备课返回课程分享编号
//        return newCourseShare.getShareId();
//    }

/**
 //     * @param course      课程基本信息
 //     * @return ShareId 新生成课程分享编号
 */
//    @Transactional(rollbackForClassName = "Exception")
//    @Override
//    public String update(String lessonPreType, Course course) {
//1、判断是否是集体备课
//        if (LESSON_PREPARATION_TYPE_GROUP.equals(lessonPreType)) {
//判断是否存在共享范围编码是否存在
//            if (StrUtil.isNotBlank(shareId)) {

//删除修改前的分享教师信息
//                courseShareUsersRepository.deleteByShareId(shareId);

//删除课程分享范围记录
//                courseShareRepository.deleteById(shareId);

//            }
//保存新的集体备课教师和课程共享信息
//            return save(course);
//        }
//2、不是集体备课共享编号返回空
//        return "";
//    }

/**
 * 根据共享编号，获得教师列表
 *
 //     * @param shareId
 //     * @return
 */
//    @Override
//    public List<CourseShareUsers> findByShareIdUsers(String shareId) {
//        return courseShareUsersRepository.findByShareId(shareId);
//    }

/**
 * 获得课程共享范围是---所有的资源信息
 *
 //     * @param courseId
 //     * @return
 */
//    @Override
//    public CourseShare findByCourseIdAll(String courseId) {
//        return courseShareRepository.findByCourseIdAndShareArea(courseId, COURSE_SHARE_AREA_ALL);
//    }
//}