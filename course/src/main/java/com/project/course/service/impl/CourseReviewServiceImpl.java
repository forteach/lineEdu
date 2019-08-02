package com.project.course.service.impl;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-9 10:14
 * @version: 1.0
 * @description: 课程评论
 * <p>
 * 学生评论及老师回复
 * @param courseReviewDescribe
 * <p>
 * 逻辑删除评论内容
 * @param reviewId
 */
//@Service
//public class CourseReviewServiceImpl implements CourseReviewService {
//
//
//    private final CourseRepository courseRepository;
//
//    private final CourseReviewDescribeRepository courseReviewDescribeRepository;
//
//    private final HashOperations<String, String, String> hashOperations;
//
//    @Autowired
//    public CourseReviewServiceImpl(CourseRepository courseRepository, CourseReviewDescribeRepository courseReviewDescribeRepository, HashOperations<String, String, String> hashOperations) {
//        this.courseRepository = courseRepository;
//        this.courseReviewDescribeRepository = courseReviewDescribeRepository;
//        this.hashOperations = hashOperations;
//    }


/**
 * 学生评论及老师回复
 *
 * @param courseReviewDescribe
 */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public CourseReviewDescribe save(CourseReviewDescribe courseReviewDescribe) {
//        CourseReviewDescribe describe = null;
//        if (StrUtil.isNotBlank(courseReviewDescribe.getReviewId())) {
//            Optional<CourseReviewDescribe> optional = courseReviewDescribeRepository.findById(courseReviewDescribe.getReviewId());
//            if (optional.isPresent()){
//                CourseReviewDescribe reviewDescribe = optional.get();
//                BeanUtil.copyProperties(courseReviewDescribe, reviewDescribe);
//                describe = courseReviewDescribeRepository.save(reviewDescribe);
//            }
//        }else {
//            describe = courseReviewDescribeRepository.save(courseReviewDescribe);
//        }
//        //　修改课程评价人数和平均评分
//        if (StrUtil.isBlank(courseReviewDescribe.getReply())) {
//            courseRepository.findById(courseReviewDescribe.getCourseId()).ifPresent(c -> {
//                Double score = courseReviewDescribeRepository.avgByCourseId(courseReviewDescribe.getCourseId());
//                c.setAverageScore(score != null ? NumberUtil.roundStr(score, 1) : "0");
//                c.setReviewAmount(c.getReviewAmount() != null ? c.getReviewAmount() + 1 : 1);
//                courseRepository.save(c);
//            });
//        }
//        return describe;
//    }

/**
 * 逻辑删除评论内容
 *
 * @param reviewId
 */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteReview(String reviewId) {
//        courseReviewDescribeRepository.findById(reviewId).ifPresent(c -> {
//            c.setIsValidated(TAKE_EFFECT_CLOSE);
//            courseReviewDescribeRepository.save(c);
//        });
//    }

//    @Override
//    public CourseReviewResp findFirstReview(String courseId) {
//        Optional<ICourseReviewDto> optional = courseReviewDescribeRepository.findFirstByIsValidatedEqualsAndCourseIdOrderByCreateTimeDesc(courseId)
//                        .stream()
//                        .filter(Objects::nonNull)
//                        .findFirst();
//        if (optional.isPresent()){
//            ICourseReviewDto iCourseReviewDto = optional.get();
//            return CourseReviewResp.builder()
//                    .courseId(iCourseReviewDto.getCourseId())
//                    .reviewAmount(iCourseReviewDto.getReviewAmount())
//                    .averageScore(iCourseReviewDto.getAverageScore())
//                    .courseReviewDescribe(builderCourseRevieDescResp(iCourseReviewDto))
//                    .build();
//        }
//        return null;
//    }

//    @Override
//    public Page<ICourseReviewDto> findReviewListPage(CourseReviewReq reviewReq, PageRequest pageRequest) {
//        return courseReviewDescribeRepository.findCourseReviewListByCourseIdOrderByCreateTime(reviewReq.getCourseId(), pageRequest);
//    }


//    private CourseReviewDescResp builderCourseRevieDescResp(ICourseReviewDto iCourseReviewDto){
//        return CourseReviewDescResp.builder()
//                .studentId(iCourseReviewDto.getStudentId())
//                .studentName(iCourseReviewDto.getStudentName())
//                .portrait(StrUtil.isNotBlank(iCourseReviewDto.getPortrait()) ? iCourseReviewDto.getPortrait() :
//                        hashOperations.get(STUDENT_ADO.concat(iCourseReviewDto.getStudentId()), "portrait"))
//                .reviewId(iCourseReviewDto.getReviewId())
//                .reviewDescribe(iCourseReviewDto.getReviewDescribe())
//                .classId(iCourseReviewDto.getClassId())
//                .className(iCourseReviewDto.getClassName())
//                .createTime(iCourseReviewDto.getCreateTime())
//                .score(iCourseReviewDto.getScore())
//                .teacherId(iCourseReviewDto.getTeacherId())
//                .teacherName(iCourseReviewDto.getTeacherName())
//                .reply(iCourseReviewDto.getReply())
//                .replyTime(iCourseReviewDto.getReplyTime())
//                .build();
//    }

//}