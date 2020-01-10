package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.Course;
import com.project.course.domain.CourseStudy;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.repository.CourseStudyRepository;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.service.CourseService;
import com.project.course.service.OnLineCourseDicService;
import com.project.schoolroll.domain.StudentFinishSchool;
import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.dto.StudentOnLineDto;
import com.project.schoolroll.service.StudentFinishSchoolService;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.schoolroll.service.online.TbClassService;
import com.project.teachplan.domain.TeachPlan;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.repository.dto.TeachCenterDto;
import com.project.teachplan.repository.verify.TeachPlanCourseVerifyRepository;
import com.project.teachplan.repository.verify.TeachPlanVerifyRepository;
import com.project.teachplan.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author zhang
 * @apiNote 在线教学计划
 */
@Service
public class TeachService {
    @PersistenceContext
    private EntityManager entityManager;
    private final StudentOnLineService studentOnLineService;
    private final TeachPlanCourseService teachPlanCourseService;
    private final TeachPlanRepository teachPlanRepository;
    private final TbClassService tbClassService;
    private final TeachPlanCourseRepository teachPlanCourseRepository;
    private final OnLineCourseDicService onLineCourseDicService;
    private final PlanFileService planFileService;
    private final CourseStudyRepository courseStudyRepository;
//    private final StringRedisTemplate redisTemplate;
    private final TeachPlanVerifyRepository teachPlanVerifyRepository;
    private final TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository;
    private final StudentScoreService studentScoreService;
    private final TeachPlanFileService teachPlanFileService;
    private final CourseService courseService;
    private final StudentFinishSchoolService studentFinishSchoolService;
//    private final TeacherService teacherService;
//    private final TeachPlanClassRepository teachPlanClassRepository;
//    private final TeachPlanClassVerifyRepository teachPlanClassVerifyRepository;

    @Autowired
    public TeachService(
            StudentOnLineService studentOnLineService, CourseService courseService,
            TeachPlanRepository teachPlanRepository, StudentFinishSchoolService studentFinishSchoolService,
            TeachPlanFileService teachPlanFileService,
            TeachPlanCourseRepository teachPlanCourseRepository, TbClassService tbClassService,
//            TeachPlanClassRepository teachPlanClassRepository, TeacherService teacherService,
            StudentScoreService studentScoreService,
            PlanFileService planFileService, TeachPlanCourseService teachPlanCourseService,
//            StringRedisTemplate redisTemplate,
            OnLineCourseDicService onLineCourseDicService, CourseStudyRepository courseStudyRepository,
            TeachPlanVerifyRepository teachPlanVerifyRepository, TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository
//                        TeachPlanClassVerifyRepository teachPlanClassVerifyRepository
    ) {
        this.studentOnLineService = studentOnLineService;
        this.teachPlanRepository = teachPlanRepository;
        this.tbClassService = tbClassService;
        this.courseService = courseService;
        this.teachPlanFileService = teachPlanFileService;
//        this.teacherService = teacherService;
//        this.teachPlanClassRepository = teachPlanClassRepository;
        this.teachPlanCourseService = teachPlanCourseService;
        this.teachPlanCourseRepository = teachPlanCourseRepository;
        this.onLineCourseDicService = onLineCourseDicService;
        this.planFileService = planFileService;
        this.teachPlanVerifyRepository = teachPlanVerifyRepository;
        this.teachPlanCourseVerifyRepository = teachPlanCourseVerifyRepository;
//        this.teachPlanClassVerifyRepository = teachPlanClassVerifyRepository;
        this.courseStudyRepository = courseStudyRepository;
//        this.redisTemplate = redisTemplate;
        this.studentScoreService = studentScoreService;
        this.studentFinishSchoolService = studentFinishSchoolService;
    }


    @Transactional(rollbackFor = Exception.class)
    public TeachPlanVerify saveUpdatePlan(TeachPlanVerify teachPlan, List<TeachPlanCourseVo> courses, String remark) {
        teachPlan.setVerifyStatus(VERIFY_STATUS_APPLY);
        MyAssert.isNull(teachPlan.getClassId(), DefineCode.ERR0010, "班级id不能为空");
        if (StrUtil.isBlank(teachPlan.getPlanId())) {
            MyAssert.isTrue(courses.isEmpty(), DefineCode.ERR0010, "课程信息不为空");
            MyAssert.isTrue(StrUtil.isBlank(teachPlan.getStartDate()), DefineCode.ERR0010, "计划开始时间不能为空");
            MyAssert.isTrue(StrUtil.isBlank(teachPlan.getEndDate()), DefineCode.ERR0010, "计划结束时间不能为空");
            MyAssert.isTrue(StrUtil.isBlank(teachPlan.getPlanAdmin()), DefineCode.ERR0010, "负责人不能为空");
            setPlan(teachPlan);
            String planId = IdUtil.fastSimpleUUID();
            teachPlan.setPlanId(planId);
            saveTeachPlanCourse(planId, courses, remark, teachPlan.getCenterAreaId(), teachPlan.getCreateUser());
            if (!courses.isEmpty()) {
                teachPlan.setCourseNumber(courses.size());
            }
            //设置对应的班级和专业对应的属性
            return teachPlanVerifyRepository.save(teachPlan);
        } else {
            Optional<TeachPlanVerify> optional = teachPlanVerifyRepository.findById(teachPlan.getPlanId());
            MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "要修改的计划不存在");
            TeachPlanVerify t = optional.get();
            Integer courseNumber = t.getCourseNumber();
//            String classId = t.getClassId();
//            Integer sumNumber = t.getSumNumber();
//            Integer classNumber = t.getClassNumber();

            BeanUtil.copyProperties(teachPlan, t);
            setPlan(t);
//            if (!classId.equals(teachPlan.getClassId())){
            //修改了班级id需要重新判断
//            }
//            t.setSumNumber(sumNumber);
//            t.setClassNumber(classNumber);

            t.setCourseNumber(courseNumber);
            teachPlan.setVerifyStatus(VERIFY_STATUS_APPLY);
            teachPlanCourseVerifyRepository.deleteAllByPlanId(t.getPlanId());
            saveTeachPlanCourse(t.getPlanId(), courses, remark, teachPlan.getCenterAreaId(), teachPlan.getCreateUser());
            if (!courses.isEmpty()) {
                t.setCourseNumber(courses.size());
            }
            return teachPlanVerifyRepository.save(t);
        }
    }


    private void setPlan(TeachPlanVerify teachPlan) {
        TbClasses tbClass = tbClassService.findById(teachPlan.getClassId());
        //修改计划名称需要判断是否存在同名计划存在不能修改
        String planName = tbClass.getSpecialtyName() + tbClass.getGrade() + "级";
        MyAssert.isTrue(teachPlanVerifyRepository.existsAllByPlanName(planName), DefineCode.ERR0010, "已经存在同名计划,请修改");
        teachPlan.setClassName(tbClass.getClassName());
        teachPlan.setSpecialtyName(tbClass.getSpecialtyName());
        teachPlan.setGrade(tbClass.getGrade());
        teachPlan.setPlanName(planName);
    }

    private void saveTeachPlanCourse(String planId, List<TeachPlanCourseVo> courses, String remark, String centerAreaId, String userId) {
        List<TeachPlanCourseVerify> planCourseList = courses.stream().filter(Objects::nonNull)
                .map(t -> createTeachPlanCourse(planId, t, remark, centerAreaId, userId))
                .collect(toList());
        if (!planCourseList.isEmpty()) {
            teachPlanCourseService.saveAllVerify(planCourseList);
        }
    }

    private TeachPlanCourseVerify createTeachPlanCourse(String planId, TeachPlanCourseVo vo, String remark, String centerAreaId, String userId) {
//        String teacherName = teacherService.findById(vo.getTeacherId()).getTeacherName();
        OnLineCourseDic onLineCourseDic = onLineCourseDicService.findId(vo.getCourseId());
        return new TeachPlanCourseVerify(planId, vo.getCourseId(), onLineCourseDicService.findId(vo.getCourseId()).getCourseName(),
                vo.getCredit(), vo.getOnLinePercentage(), vo.getLinePercentage(),
                // vo.getTeacherId(), teacherName,
                centerAreaId, remark, userId, VERIFY_STATUS_APPLY, onLineCourseDic.getType());
    }

//    private void saveTeachPlanClass(String planId, TeachPlanVerify teachPlan, List<String> classIds, String remark, String centerAreaId, String userId) {
//        List<TeachPlanClassVerify> planClassList = classIds.stream().filter(Objects::nonNull)
//                .map(c -> new TeachPlanClassVerify(c, planId, tbClassService.findClassByClassId(c).getClassName(), teachPlan.getPlanName(), studentOnLineService.countByClassId(c), centerAreaId, remark, userId, VERIFY_STATUS_APPLY))
//                .collect(toList());
//        teachPlanClassVerifyRepository.saveAll(planClassList);
//        int sumNumber = planClassList.stream().mapToInt(TeachPlanClassVerify::getClassNumber).sum();
//        teachPlan.setSumNumber(sumNumber);
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public TeachPlanVerify saveUpdatePlanClass(String planId, List<String> classIds, String remark, String centerAreaId, String userId) {
//        Optional<TeachPlanVerify> teachPlanOptional = teachPlanVerifyRepository.findById(planId);
//        MyAssert.isFalse(teachPlanOptional.isPresent(), DefineCode.ERR0010, "不存在对应的计划编号");
//        TeachPlanVerify teachPlan = teachPlanOptional.get();
//        teachPlanClassVerifyRepository.deleteAllByPlanId(planId);
//        saveTeachPlanClass(planId, teachPlan, classIds, remark, centerAreaId, userId);
//        if (!classIds.isEmpty()) {
//            teachPlan.setClassNumber(classIds.size());
//        }
//        teachPlan.setVerifyStatus(VERIFY_STATUS_APPLY);
//        return teachPlanVerifyRepository.save(teachPlan);
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public TeachPlanVerify saveUpdatePlanCourse(String planId, List<TeachPlanCourseVo> courses, String remark, String centerAreaId, String userId) {
//        Optional<TeachPlanVerify> teachPlanOptional = teachPlanVerifyRepository.findById(planId);
//        MyAssert.isFalse(teachPlanOptional.isPresent(), DefineCode.ERR0010, "不存在对应的计划编号");
//        TeachPlanVerify teachPlan = teachPlanOptional.get();
//        teachPlanCourseVerifyRepository.deleteAllByPlanId(planId);
//        saveTeachPlanCourse(planId, courses, remark, centerAreaId, userId);
//        if (!courses.isEmpty()) {
//            teachPlan.setCourseNumber(courses.size());
//        }
//        teachPlan.setVerifyStatus(VERIFY_STATUS_APPLY);
//        return teachPlanVerifyRepository.save(teachPlan);
//    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByPlanId(String planId) {
        teachPlanRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
        teachPlanVerifyRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
        teachPlanCourseRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
        teachPlanCourseVerifyRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        Optional<TeachPlanVerify> optional = teachPlanVerifyRepository.findById(planId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在要删除的计划信息");
        TeachPlanVerify teachPlanVerify = optional.get();
        MyAssert.isTrue(DateUtil.parseDate(teachPlanVerify.getEndDate()).isAfterOrEquals(new Date()), DefineCode.ERR0010, "正在进行的计划不能删除");
        teachPlanCourseRepository.deleteAllByPlanId(planId);
        teachPlanRepository.deleteAllByPlanId(planId);
        teachPlanVerifyRepository.deleteById(planId);
        teachPlanCourseVerifyRepository.deleteAllByPlanId(planId);
//        teachPlanClassRepository.deleteAllByPlanId(planId);
//        teachPlanClassVerifyRepository.deleteAllByPlanId(planId);
    }

//    public List<TeachPlanClass> findAllClassByPlanId(String planId) {
//        return teachPlanClassRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId);
//    }
//
//    public List<TeachPlanClassVerify> findAllClassVerifyByPlanId(String planId) {
//        return teachPlanClassVerifyRepository.findAllByPlanId(planId);
//    }

//    public Page<TeachPlanDto> findAllPageByPlanIdAndVerifyStatus(String planId, String verifyStatus, Pageable pageable) {
//        if (StrUtil.isNotBlank(planId) && StrUtil.isNotBlank(verifyStatus)) {
//            return teachPlanVerifyRepository.findAllPageByPlanIdAndVerifyStatusDto(planId, verifyStatus, pageable);
//        } else if (StrUtil.isNotBlank(planId) && StrUtil.isBlank(verifyStatus)) {
//            return teachPlanVerifyRepository.findAllPageByPlanIdDto(planId, pageable);
//        } else if (StrUtil.isNotBlank(verifyStatus) && StrUtil.isBlank(planId)) {
//            return teachPlanVerifyRepository.findAllPageByVerifyStatusDto(verifyStatus, pageable);
//        } else {
//            return teachPlanVerifyRepository.findAllPageDto(pageable);
//        }
//    }


//    public Page<TeachPlanDto> findAllPageDtoByCenterAreaId(String centerAreaId, String verifyStatus, Pageable pageable) {
//        if (StrUtil.isBlank(verifyStatus)) {
//            return teachPlanVerifyRepository.findAllPageByCenterAreaIdDto(centerAreaId, pageable);
//        } else {
//            return teachPlanVerifyRepository.findAllPageByVerifyStatusAndCenterAreaIdDto(verifyStatus, centerAreaId, pageable);
//        }
//    }

//    public Page<TeachPlanDto> findAllPageDtoByCenterAreaIdAndPlanId(String centerAreaId, String planId, String verifyStatus, Pageable pageable) {
//        if (StrUtil.isBlank(verifyStatus)) {
//            return teachPlanVerifyRepository.findAllPageByCenterAreaIdAndPlanIdDto(centerAreaId, planId, pageable);
//        } else {
//            return teachPlanVerifyRepository.findAllPageByPlanIdAndCenterAreaIdDto(planId, verifyStatus, centerAreaId, pageable);
//        }
//    }

//    @Async
//    @Transactional(rollbackFor = Exception.class)
//    void updateClassByPlanId(String planId, String status, String userId) {
//        List<TeachPlanClass> list = teachPlanClassRepository.findAllByPlanId(planId).stream().peek(c -> {
//            c.setIsValidated(status);
//            c.setUpdateUser(userId);
//        }).collect(Collectors.toList());
//        teachPlanClassRepository.saveAll(list);
//    }

    public Page<TeachPlanVerify> findAllPage(TeachPlanVo vo, Pageable of) {
        StringBuffer dataSql = new StringBuffer(" select " +
                " tpv.plan_id as plan_id, " +
                " tpv.plan_name as plan_name," +
                " tpv.start_date as start_date," +
                " tpv.end_date as end_date," +
                " tpv.plan_admin as plan_admin," +
                " tpv.course_number as course_number, " +
                " tpv.grade as grade, " +
                " tpv.specialty_name as specialty_name, " +
                " tpv.class_id as class_id, " +
                " tpv.class_name as class_name, " +
                " tpv.center_area_id as center_area_id, " +
                " lc.center_name as center_name, " +
                " tpv.is_validated as is_validated, " +
                " tpv.c_time as c_time," +
                " tpv.u_time as u_time," +
                " tpv.u_user as u_user," +
                " tpv.c_user as c_user," +
                " tpv.verify_status as verify_status, " +
                " tpv.remark as remark " +
                " from teach_plan_verify as tpv left join learn_center as lc on lc.center_id = tpv.center_area_id ");
        StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
        StringBuffer countSql = new StringBuffer(" select count(*) from teach_plan_verify as tpv left join learn_center as lc on lc.center_id = tpv.center_area_id ");
        if (StrUtil.isNotBlank(vo.getCenterAreaId())) {
            whereSql.append(" and tpv.center_area_id = :centerAreaId ");
        }
        if (StrUtil.isNotBlank(vo.getClassName())) {
            whereSql.append(" and tpv.class_name = :className ");
        }
        if (StrUtil.isNotBlank(vo.getGrade())) {
            whereSql.append(" and tpv.grade = :grade ");
        }
        if (StrUtil.isNotBlank(vo.getVerifyStatus())) {
            whereSql.append(" and tpv.verify_status = :verifyStatus");
        }
        if (StrUtil.isNotBlank(vo.getSpecialtyName())) {
            whereSql.append(" and tpv.specialty_name = :specialtyName");
        }
        if (StrUtil.isNotBlank(vo.getCenterName())) {
            whereSql.append(" and lc.center_name = :centerName");
        }
        dataSql.append(whereSql).append(" order by tpv.c_time desc ");
        countSql.append(whereSql);
        Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), TeachPlanVerify.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        if (StrUtil.isNotBlank(vo.getCenterAreaId())) {
            dataQuery.setParameter("centerAreaId", vo.getCenterAreaId());
            countQuery.setParameter("centerAreaId", vo.getCenterAreaId());
        }
        if (StrUtil.isNotBlank(vo.getClassName())) {
            dataQuery.setParameter("className", vo.getClassName());
            countQuery.setParameter("className", vo.getClassName());
        }
        if (StrUtil.isNotBlank(vo.getGrade())) {
            dataQuery.setParameter("grade", vo.getGrade());
            countQuery.setParameter("grade", vo.getGrade());
        }
        if (StrUtil.isNotBlank(vo.getVerifyStatus())) {
            dataQuery.setParameter("verifyStatus", vo.getVerifyStatus());
            countQuery.setParameter("verifyStatus", vo.getVerifyStatus());
        }
        if (StrUtil.isNotBlank(vo.getSpecialtyName())) {
            dataQuery.setParameter("specialtyName", vo.getSpecialtyName());
            countQuery.setParameter("specialtyName", vo.getSpecialtyName());
        }
        if (StrUtil.isNotBlank(vo.getCenterName())) {
            dataQuery.setParameter("centerName", vo.getCenterName());
            countQuery.setParameter("centerName", vo.getCenterName());
        }

        //设置分页
        dataQuery.setFirstResult((int) of.getOffset());
        dataQuery.setMaxResults(of.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<TeachPlanVerify> content = total > of.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        return new PageImpl<>(content, of, total);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateCourseByPlanId(String planId, String status, String userId) {
        List<TeachPlanCourse> list = teachPlanCourseRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        if (!list.isEmpty()) {
            teachPlanCourseRepository.saveAll(list);
        }
        List<TeachPlanCourseVerify> collect = teachPlanCourseVerifyRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            teachPlanCourseVerifyRepository.saveAll(collect);
        }
    }

    @Transactional
    public void updateStatus(String planId, String userId) {
        Optional<TeachPlanVerify> optionalTeachPlan = teachPlanVerifyRepository.findById(planId);
        MyAssert.isFalse(optionalTeachPlan.isPresent(), DefineCode.ERR0014, "不存在对应的计划信息");
        optionalTeachPlan.ifPresent(t -> {
            String status = t.getIsValidated();
            if (TAKE_EFFECT_CLOSE.equals(status)) {
                t.setIsValidated(TAKE_EFFECT_OPEN);
                // 修改班级计划状态
//                updateClassByPlanId(planId, TAKE_EFFECT_OPEN, userId);
                //修改课程计划状态
                updateCourseByPlanId(planId, TAKE_EFFECT_OPEN, userId);
                //修改计划文件状态
                planFileService.updateStatus(planId, TAKE_EFFECT_OPEN, userId);
                teachPlanFileService.updateStatus(planId, TAKE_EFFECT_OPEN, userId);
                //修改审核的计划信息
                setVerifyStatus(planId, TAKE_EFFECT_OPEN, userId);
                //修改计划
                updatePlanStatus(planId, TAKE_EFFECT_OPEN, userId);
            } else {
                t.setIsValidated(TAKE_EFFECT_CLOSE);
//                updateClassByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                updateCourseByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                planFileService.updateStatus(planId, TAKE_EFFECT_CLOSE, userId);
                teachPlanFileService.updateStatus(planId, TAKE_EFFECT_CLOSE, userId);
                //修改审核的计划信息
                setVerifyStatus(planId, TAKE_EFFECT_CLOSE, userId);
                //修改计划
                updatePlanStatus(planId, TAKE_EFFECT_CLOSE, userId);
            }
            t.setUpdateUser(userId);
            teachPlanVerifyRepository.save(t);
        });
    }

    @Transactional
    public void updatePlanStatus(String planId, String status, String userId) {
        teachPlanRepository.findById(planId).ifPresent(p -> {
            p.setIsValidated(status);
            p.setUpdateUser(userId);
            teachPlanRepository.save(p);
        });
    }

    private void setVerifyStatus(String planId, String status, String userId) {
        //修改教学计划
        updateTeachPlanVerify(planId, status, userId);
        //修改教学计划班级
//        updateClassVerifyByPlanId(planId, status, userId);
        //修改教学计划课程
        updateVerfyCourseByPlanId(planId, status, userId);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateTeachPlanVerify(String planId, String status, String userId) {
        teachPlanVerifyRepository.findById(planId).ifPresent(t -> {
            t.setIsValidated(status);
            t.setUpdateUser(userId);
            teachPlanVerifyRepository.save(t);
        });
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateVerfyCourseByPlanId(String planId, String status, String userId) {
        List<TeachPlanCourseVerify> list = teachPlanCourseVerifyRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        if (!list.isEmpty()) {
            teachPlanCourseVerifyRepository.saveAll(list);
        }
    }

//    @Async
//    @Transactional(rollbackFor = Exception.class)
//    void updateClassVerifyByPlanId(String planId, String status, String userId) {
//        List<TeachPlanClassVerify> list = teachPlanClassVerifyRepository.findAllByPlanId(planId).stream().peek(c -> {
//            c.setIsValidated(status);
//            c.setUpdateUser(userId);
//        }).collect(Collectors.toList());
//        if (!list.isEmpty()) {
//            teachPlanClassVerifyRepository.saveAll(list);
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachPlan(String planId, String verifyStatus, String remark, String userId) {
        //修改对应的计划信息
        updateVerifyTeachPlan(planId, verifyStatus, remark, userId);
        //修改对应的班级信息
//        updateVerifyPlanClass(planId, verifyStatus, remark, userId);
        //修改对应的课程信息
        teachPlanCourseService.updateVerifyPlanCourse(planId, verifyStatus, remark, userId);
    }


//    private void updateVerifyPlanClass(String planId, String verifyStatus, String remark, String userId) {
    // 审核通过 删除原来的班级
//        if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
//            teachPlanClassRepository.deleteAllByPlanId(planId);
//        }
//        List<TeachPlanClass> teachPlanClassList = new ArrayList<>();
//        List<TeachPlanClassVerify> teachPlanClassVerifyList = teachPlanClassVerifyRepository.findAllByPlanId(planId).stream().filter(Objects::nonNull)
//                .peek(t -> {
//                    t.setRemark(remark);
//                    t.setVerifyStatus(verifyStatus);
//                    t.setUpdateUser(userId);
//                    if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
//                        TeachPlanClass p = new TeachPlanClass();
//                        BeanUtil.copyProperties(t, p);
//                        p.setUpdateUser(userId);
//                        teachPlanClassList.add(p);
//                    }
//                }).collect(Collectors.toList());
//        if (!teachPlanClassVerifyList.isEmpty()) {
//            teachPlanClassVerifyRepository.saveAll(teachPlanClassVerifyList);
//        }
//        if (!teachPlanClassList.isEmpty()) {
//            teachPlanClassRepository.saveAll(teachPlanClassList);
//        }
//    }

    /**
     * 修改计划信息
     */
    private void updateVerifyTeachPlan(String planId, String verifyStatus, String remark, String userId) {
        Optional<TeachPlanVerify> optional = teachPlanVerifyRepository.findById(planId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0014, "不存在对应的计划信息");
        TeachPlanVerify t = optional.get();
        t.setUpdateUser(userId);
        t.setVerifyStatus(verifyStatus);
        if (StrUtil.isNotBlank(remark)) {
            t.setRemark(remark);
        }
        //审核通过
        if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
            TeachPlan p = new TeachPlan();
            BeanUtil.copyProperties(t, p);
            teachPlanRepository.save(p);
        }
        teachPlanVerifyRepository.save(t);
    }

//    @SuppressWarnings(value = "all")
//    public Page<TeachCourseVo> findAllPageDtoByPlanId(String planId, String key, Pageable pageable) {
//        //查询redis缓存
//        if (redisTemplate.hasKey(key)) {
//            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(key));
//            //分页数据保存json转换为分页信息返回显示
//            return new PageImpl(jsonObject.getJSONArray("content").toJavaList(TeachCourseVo.class), pageable, jsonObject.getLong("totalElements"));
//        }
//        //设置redis缓存
//        Page<TeachCourseVo> page = findAllPageByPlanId(planId, pageable);
//        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(page), Duration.ofMinutes(1));
//        return page;
//    }

    @SuppressWarnings(value = "all")
    public Page<TeachCourseVo> findAllPageDtoByPlanId(String studentName, String className, String grade, String specialtyName, String centerAreaId, PageRequest request) {
        Page<StudentOnLineDto> page = studentOnLineService.findStudentOnLineDto(request, studentName, TAKE_EFFECT_OPEN, centerAreaId, grade, specialtyName, className);
        List<StudentOnLineDto> list = page.getContent();
        if (list.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        String classId = list.get(0).getClassId();
        Optional<TeachCenterDto> optional = teachPlanRepository.findAllByClassId(classId);
        if (!optional.isPresent()) {
            return new PageImpl<>(new ArrayList<>());
        }
        TeachCenterDto teachCenterDto = optional.get();
        List<TeachCourseVo> courseVoList = list.stream().filter(Objects::nonNull)
                .map(d -> new TeachCourseVo(d.getStudentId(), d.getStuId(), d.getSpecialtyName(),
                        d.getGrade(), d.getClassId(), d.getClassName(), d.getStudentName(), d.getStuPhone(), d.getCenterAreaId(),
                        teachCenterDto.getCenterName(), teachCenterDto.getPlanId(), teachCenterDto.getPlanName(),
                        teachCenterDto.getStartDate(), teachCenterDto.getEndDate(),
                        toListStudy(d.getStudentId(), teachCenterDto.getPlanId())))
                .collect(toList());
        return new PageImpl<>(courseVoList, request, page.getTotalElements());
    }

    public Page<CourseScoreVo> findScoreAllPageDtoByPlanId(String studentName, String className, String grade, String specialtyName, String centerAreaId, PageRequest request) {
        Page<StudentOnLineDto> page = studentOnLineService.findStudentOnLineDto(request, studentName, TAKE_EFFECT_OPEN, centerAreaId, grade, specialtyName, className);
        List<StudentOnLineDto> list = page.getContent();
        if (list.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        String classId = list.get(0).getClassId();
        Optional<TeachCenterDto> optional = teachPlanRepository.findAllByClassId(classId);
        if (!optional.isPresent()) {
            return new PageImpl<>(new ArrayList<>());
        }
        TeachCenterDto teachCenterDto = optional.get();
        List<CourseScoreVo> courseScoreVoList = list.stream().filter(Objects::nonNull)
                .map(d -> new CourseScoreVo(d.getStudentId(), d.getStuId(), d.getSpecialtyName(),
                        d.getGrade(), d.getClassId(), d.getClassName(), d.getStudentName(), d.getStuPhone(),
                        d.getCenterAreaId(), teachCenterDto.getCenterName(), teachCenterDto.getPlanId(),
                        teachCenterDto.getPlanName(), teachCenterDto.getStartDate(), teachCenterDto.getEndDate(),
                        toListScore(d.getStudentId(), teachCenterDto.getPlanId()), isFinishSchool(d.getStudentId())))
                .collect(toList());
        return new PageImpl<>(courseScoreVoList, request, page.getTotalElements());
    }

    private String isFinishSchool(String studentId) {
        return studentFinishSchoolService
                .findById(studentId)
                .orElse(new StudentFinishSchool(NO))
                .getIsFinishSchool();
    }

//    @SuppressWarnings(value = "all")
//    public Page<CourseScoreVo> findScoreAllPageDtoByPlanId(String planId, String key, Pageable pageable) {
//        Optional<TeachCenterDto> optional = teachPlanRepository.findAllByPlanId(planId);
//        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在对应的计划信息");
//        //查询redis缓存
//        TeachCenterDto teachCenterDto = optional.get();
//        if (redisTemplate.hasKey(key)) {
//            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(key));
//            return new PageImpl(jsonObject.getJSONArray("content").toJavaList(TeachCourseVo.class), pageable, jsonObject.getLong("totalElements"));
//        }
//        //设置redis缓存
//        Page<CourseScoreVo> page = findScoreAllPageByPlanId(teachCenterDto, pageable);
//        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(page), Duration.ofMinutes(1));
//        return page;
//    }

//    private Page<CourseScoreVo> findScoreAllPageByPlanId(TeachCenterDto teachCenterDto, Pageable pageable) {
////        Page<PlanCourseStudyDto> page = teachPlanRepository.findAllPageDtoByPlanId(planId, pageable);
//        Page<StudentOnLine> page = studentOnLineService.findAllPageByClassId(teachCenterDto.getClassId(), pageable);
//        List<CourseScoreVo> list = page.getContent()
//                .stream()
//                .filter(Objects::nonNull)
//                .map(d -> new CourseScoreVo(d.getStudentId(), d.getStudentName(), d.getStuPhone(), d.getCenterAreaId(),
//                        teachCenterDto.getCenterName(), teachCenterDto.getPlanId(), teachCenterDto.getPlanName(), teachCenterDto.getStartDate(), teachCenterDto.getEndDate(),
//                        toListScore(d.getStudentId(), teachCenterDto.getPlanId())))
//                .collect(toList());
//        return new PageImpl<>(list, pageable, page.getTotalElements());
//    }

//    private Page<TeachCourseVo> findAllPageByPlanId(String classId) {
////    private Page<TeachCourseVo> findAllPageByPlanId(String planId, Pageable pageable) {
////        Page<PlanCourseStudyDto> page = teachPlanRepository.findAllPageDtoByPlanId(planId, pageable);
//        Optional<TeachCenterDto> optional = teachPlanRepository.findAllByClassId(classId);
//        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在对应的计划信息");
//        //查询redis缓存
//        TeachCenterDto teachCenterDto = optional.get();
//        Page<StudentOnLine> page = studentOnLineService.findAllPageByClassId(teachCenterDto.getClassId(), pageable);
//        List<TeachCourseVo> list = page.getContent()
//                .stream()
//                .filter(Objects::nonNull)
//                .map(d -> new TeachCourseVo(d.getStudentId(), d.getStudentName(), d.getStuPhone(), d.getCenterAreaId(),
//                        teachCenterDto.getCenterName(), teachCenterDto.getPlanId(), teachCenterDto.getPlanName(), teachCenterDto.getStartDate(), teachCenterDto.getEndDate(),
//                        toListStudy(d.getStudentId(), teachCenterDto.getPlanId())))
//                .collect(toList());
//        return new PageImpl<>(list, pageable, page.getTotalElements());
//    }

    private List<StudyVo> toListStudy(String studentId, String planId) {
        List<StudyVo> list = new ArrayList<>(16);
        teachPlanCourseRepository.findAllPlanCourseDtoByPlanId(planId)
                .forEach(s -> {
                    Optional<ICourseStudyDto> optional = courseStudyRepository.findStudyDto(studentId, s.getCourseId());
                    if (optional.isPresent()) {
                        ICourseStudyDto d = optional.get();
                        list.add(new StudyVo(d.getCourseId(), d.getCourseName(), d.getOnLineTime(),
                                d.getOnLineTimeSum(), d.getAnswerSum(), d.getCorrectSum(), s.getCourseType()));
                    } else {
                        //没有学习直接返回对应的课程信息
                        if (StrUtil.isNotBlank(s.getCourseId())) {
                            list.add(new StudyVo(s.getCourseId(), s.getCourseName(), s.getCourseType()));
                        }
                    }
                });
        return list;
    }

    private List<ScoreVo> toListScore(String studentId, String planId) {
        List<ScoreVo> list = CollUtil.newArrayList();
        teachPlanCourseRepository.findAllPlanCourseDtoByPlanId(planId)
                .forEach(s -> {
                    Optional<ICourseStudyDto> optional = courseStudyRepository.findStudyDto(studentId, s.getCourseId());
                    if (optional.isPresent()) {
                        ICourseStudyDto d = optional.get();
                        //线上成绩 (学习时长/课程总时长) * 视频占比 + (习题回答正确数量/总习题数量) * 练习占比
                        BigDecimal videoScore = new BigDecimal("0");
                        BigDecimal jobsScore = new BigDecimal("0");
                        String videoPercentage = d.getVideoPercentage() == null ? "0" : d.getVideoPercentage();
                        String jobsPercentage = d.getJobsPercentage() == null ? "0" : d.getJobsPercentage();
                        if (0 != d.getOnLineTimeSum()) {
                            videoScore = NumberUtil.mul(NumberUtil.div(d.getOnLineTime(), d.getOnLineTimeSum(), 2), Double.valueOf(videoPercentage) / 100);
                        }
                        if (0 != d.getAnswerSum()) {
                            jobsScore = NumberUtil.mul(NumberUtil.div(d.getCorrectSum(), d.getAnswerSum(), 2), Double.valueOf(jobsPercentage) / 100);
                        }
                        String score = NumberUtil.toStr(NumberUtil.mul(NumberUtil.add(videoScore, jobsScore), 100));
                        list.add(new ScoreVo(d.getCourseId(), d.getCourseName(), score, d.getCourseType()));
                    } else {
                        //不存在学习成绩设置为 0
                        if (StrUtil.isNotBlank(s.getCourseId())) {
                            Course course = courseService.getById(s.getCourseId());
                            list.add(new ScoreVo(course.getCourseId(), course.getCourseName(), "0", s.getCourseType()));
                        }
                    }
                });
        return list;
    }

    @Transactional
    public void taskPlanStatus() {
        // 查询今天之前的要到期的计划并改变计划结束状态　从进行中1 改为 0
        List<TeachPlan> list = teachPlanRepository.findAllByStatusAndEndDateBefore(PLAN_STATUS_ONGOING, DateUtil.today()).stream()
                .filter(Objects::nonNull)
                .peek(t -> t.setStatus(PLAN_STATUS_SUCCESS))
                .collect(toList());
        if (!list.isEmpty()) {
            teachPlanRepository.saveAll(list);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void taskOnLineCourseScore() {
        //查询已经完成的计划并且没有计算过成绩的计划信息
        List<TeachPlan> planList = teachPlanRepository.findAllByIsValidatedEqualsAndStatusAndCountStatus(TAKE_EFFECT_OPEN, PLAN_STATUS_SUCCESS, PLAN_COUNT_STATUS_ONGOING);
        //获取对应的计划id
        Set<String> planIds = planList.stream().filter(Objects::nonNull).map(TeachPlan::getPlanId).collect(toSet());
        //查询计划课程对应的课程信息包含(课程名称,Id,平时成绩,线上，线下占比,平时作业占比,观看视频占百分比 等)
        planIds.forEach(p -> teachPlanCourseRepository.findAllPlanCourseDtoByPlanId(p)
                .forEach(c -> {
                    List<StudentScore> list = findAllStudentScore(p, c.getCourseId(), c.getCourseType(), c.getOnLinePercentage(), c.getLinePercentage(), c.getVideoPercentage(), c.getJobsPercentage());
                    if (!list.isEmpty()) {
                        studentScoreService.saveAll(list);
                    }
                }));
        if (!planList.isEmpty()) {
            //修改计划计算状态为完成
            teachPlanRepository.saveAll(planList.stream().peek(t -> t.setCountStatus(PLAN_COUNT_STATUS_SUCCESS)).collect(toList()));
        }
    }

    private List<StudentScore> findAllStudentScore(String planeId, String courseId, Integer courseType, int onLinePercentage, int linePercentage, String videoPercentage, String jobsPercentage) {
        return teachPlanRepository.findAllStudentIdsByPlanId(planeId)
                .stream()
                .filter(Objects::nonNull)
                .map(s -> findSetStudentScore(s, courseId, courseType, onLinePercentage, linePercentage, videoPercentage, jobsPercentage))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private StudentScore findSetStudentScore(String studentId, String courseId, Integer courseType, int onLinePercentage,
                                             int linePercentage, String videoPercentage, String jobsPercentage) {
        Optional<CourseStudy> optional = courseStudyRepository.findAllByCourseIdAndStudentId(courseId, studentId);
        if (optional.isPresent()) {
            CourseStudy c = optional.get();
            StudentScore studentScore = studentScoreService.findStudentIdAndCourseId(studentId, courseId);
            studentScore.setCourseId(courseId);
            studentScore.setStudentId(studentId);
            studentScore.setUpdateUser(c.getStudentId());
            studentScore.setCreateUser(c.getStudentId());
            studentScore.setCenterAreaId(c.getCenterAreaId());
            //设置课程类型
            String type = String.valueOf(courseType);
            if (StrUtil.isBlank(studentScore.getType())) {
                studentScore.setType(type);
            }
            //不是线下课程需要计算成绩占比和计算总成绩
            if (!COURSE_TYPE_2.equals(type)) {
                //线上观看时间
                int onLineTime = c.getOnLineTime();
                //课程总时间
                int onLineTimeSum = c.getOnLineTimeSum();
                //观看视频成绩 (观看视频时长/视频总时长) * 观看视频占比
                double videoScore = NumberUtil.mul(NumberUtil.mul(NumberUtil.div(onLineTime, onLineTimeSum, 2), 100F), Double.valueOf(videoPercentage) / 100);
                //平时作业成绩 (回答正确题目数量/总题目数量) * 平时作业占比
                BigDecimal jobScore = new BigDecimal("0");
                if (0 != c.getAnswerSum()) {
                    jobScore = NumberUtil.mul(NumberUtil.mul(NumberUtil.div(c.getCorrectSum(), c.getAnswerSum(), 2), 100F), Double.valueOf(jobsPercentage) / 100);
                }
                //线上成绩 = (观看视频时长/视频总时长) * 观看视频占比 + (回答正确题目数量/总题目数量) * 平时作业占比
                BigDecimal onLineScore = NumberUtil.add(videoScore, jobScore);
                studentScore.setOnLineScore(onLineScore.toPlainString());
                //判断课程类别是线上课程直接计算成绩是线上学习结果成绩,计算结果直接完成
                if (COURSE_TYPE_1.equals(type)) {
                    studentScore.setCourseScore(Float.valueOf(studentScore.getOnLineScore()));
                    studentScore.setCompleteStatus(1);
                } else if (COURSE_TYPE_3.equals(type)) {
                    //是混合课程 计算课程成绩 线上成绩部分 = 线上成绩 * 线上成绩占比
                    //线上总成绩
                    BigDecimal onLineScoreSum = NumberUtil.mul(onLineScore, NumberUtil.div(onLinePercentage, 100, 2));
                    //线下总成绩
                    String offLineScore = studentScore.getOffLineScore();
                    if (StrUtil.isNotBlank(offLineScore)) {
                        //计算课程总成绩
                        //线下总成绩
                        BigDecimal offLineScoreSum = NumberUtil.mul(new BigDecimal(offLineScore), NumberUtil.div(linePercentage, 100, 2));
                        //课程总成绩
                        BigDecimal courseScore = NumberUtil.add(onLineScoreSum, offLineScoreSum);
                        studentScore.setCourseScore(courseScore.floatValue());
                    }
                    studentScore.setCompleteStatus(0);
                }
                //线下成绩占比 %
                studentScore.setLinePercentage(linePercentage);
                //线上成绩占比 %
                studentScore.setOnLinePercentage(onLinePercentage);
            }
            return studentScore;
        }
        return null;
    }

    public List<TeachPlanVerify> findAllPlan() {
        return teachPlanVerifyRepository.findAllByIsValidatedEqualsAndVerifyStatus(TAKE_EFFECT_OPEN, VERIFY_STATUS_AGREE);
    }

    public List<TeachPlanVerify> findAllPlanByCenterId(String centerId) {
        return teachPlanVerifyRepository.findAllByIsValidatedEqualsAndVerifyStatusAndCenterAreaId(TAKE_EFFECT_OPEN, VERIFY_STATUS_AGREE, centerId);
    }

    public Set<String> findAllClassIdAndPlanByCenterId(String centerId) {
        return teachPlanVerifyRepository.findAllByCenterAreaId(centerId)
                .stream().filter(Objects::nonNull)
                .map(TeachPlanVerify::getClassId)
                .collect(toSet());
    }

//    /**
//     * 判断对应班级的计划结束日期是否在当前日期之前
//     * @param classId 班级Id
//     * @return true 在当前日期之前, false 不在今天之前
//     */
//    public boolean checkPlanDate(String classId) {
//        List<TeachPlanVerify> planVerifies = teachPlanVerifyRepository.findAllByClassId(classId);
//        MyAssert.isTrue(planVerifies.isEmpty(), DefineCode.ERR0010, "不存在对应的计划课程");
//        TeachPlanVerify teachPlanVerify = planVerifies.get(0);
//        MyAssert.isTrue(TAKE_EFFECT_CLOSE.equals(teachPlanVerify.getIsValidated()), DefineCode.ERR0010, "对应的班级计划已经失效");
//        return DateUtil.parseDate(teachPlanVerify.getEndDate()).isBefore(new Date());
//    }

    public void computeFinishSchool() {
        //查询对应已经完成的计划Id集合
        List<String> planIds = teachPlanRepository.findAllByIsValidatedEqualsAndEndDateBefore(TAKE_EFFECT_OPEN, DateUtil.today());
        planIds.forEach(p -> {
            //计划对应的课程信息
            List<TeachPlanCourse> planCourses = teachPlanCourseRepository.findAllByPlanId(p);
            List<StudentFinishSchool> list = teachPlanRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, p).stream().filter(Objects::nonNull)
                    .map(v -> buildStudentFinishSchool(planCourses, v))
                    .collect(toList());
            if (!list.isEmpty()) {
                studentFinishSchoolService.saveAll(list);
            }
        });
    }

    private StudentFinishSchool buildStudentFinishSchool(List<TeachPlanCourse> planCourses, PlanStudentVo vo) {
        List<StudentScore> list = studentScoreService.findByStudentId(vo.getStudentId());
        StudentFinishSchool finishSchool = studentFinishSchoolService.findByStudentId(vo.getStudentId());
        if (planCourses.size() != list.size()) {
            //计划的课程数量和实际有成绩的课程数量不同，说明不能毕业
            finishSchool.setIsFinishSchool(NO);
        } else {
            //有成绩的数量和计划课程的数量相同，需要判断对应的成绩是否符合毕业条件即，每门课成绩都需要大于等于60分
            long count = list.stream()
                    .filter(Objects::nonNull)
                    .filter(s -> null != s.getCourseScore())
                    .mapToDouble(StudentScore::getCourseScore)
                    .filter(s -> s >= 60F)
                    .count();
            //计算过的课程成绩全部大于等于60分成绩合格否则不合格
            if (count == list.size()) {
                finishSchool.setIsFinishSchool(YES);
            } else {
                finishSchool.setIsFinishSchool(NO);
            }
        }
        finishSchool.setCenterAreaId(vo.getCenterAreaId());
        finishSchool.setStudentId(vo.getStudentId());
        finishSchool.setStudentName(vo.getStudentName());
        return finishSchool;
    }
}