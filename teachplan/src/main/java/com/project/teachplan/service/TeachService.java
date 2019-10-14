package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.OnLineCourseDicService;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.schoolroll.service.online.TbClassService;
import com.project.teachplan.domain.TeachPlan;
import com.project.teachplan.domain.TeachPlanClass;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.verify.TeachPlanClassVerify;
import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.TeachPlanClassRepository;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.repository.dto.TeachPlanDto;
import com.project.teachplan.repository.verify.TeachPlanClassVerifyRepository;
import com.project.teachplan.repository.verify.TeachPlanCourseVerifyRepository;
import com.project.teachplan.repository.verify.TeachPlanVerifyRepository;
import com.project.teachplan.vo.TeachPlanCourseVo;
import com.project.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

/**
 * @author zhang
 * @apiNote 在线教学计划
 */
@Service
public class TeachService {
    private final StudentOnLineService studentOnLineService;
    private final TeachPlanCourseService teachPlanCourseService;
    private final TeachPlanRepository teachPlanRepository;
    private final TbClassService tbClassService;
    private final TeachPlanClassRepository teachPlanClassRepository;
    private final TeachPlanCourseRepository teachPlanCourseRepository;
    private final OnLineCourseDicService onLineCourseDicService;
    private final TeacherService teacherService;
    private final PlanFileService planFileService;

    private final TeachPlanVerifyRepository teachPlanVerifyRepository;
    private final TeachPlanClassVerifyRepository teachPlanClassVerifyRepository;
    private final TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository;

    @Autowired
    public TeachService(StudentOnLineService studentOnLineService, TeachPlanRepository teachPlanRepository,
                        TeachPlanCourseRepository teachPlanCourseRepository, TbClassService tbClassService,
                        TeachPlanClassRepository teachPlanClassRepository, TeacherService teacherService,
                        PlanFileService planFileService, TeachPlanCourseService teachPlanCourseService,
                        OnLineCourseDicService onLineCourseDicService,
                        TeachPlanVerifyRepository teachPlanVerifyRepository, TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository,
                        TeachPlanClassVerifyRepository teachPlanClassVerifyRepository) {
        this.studentOnLineService = studentOnLineService;
        this.teachPlanRepository = teachPlanRepository;
        this.tbClassService = tbClassService;
        this.teacherService = teacherService;
        this.teachPlanClassRepository = teachPlanClassRepository;
        this.teachPlanCourseService = teachPlanCourseService;
        this.teachPlanCourseRepository = teachPlanCourseRepository;
        this.onLineCourseDicService = onLineCourseDicService;
        this.planFileService = planFileService;
        this.teachPlanVerifyRepository = teachPlanVerifyRepository;
        this.teachPlanCourseVerifyRepository = teachPlanCourseVerifyRepository;
        this.teachPlanClassVerifyRepository = teachPlanClassVerifyRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public TeachPlanVerify saveUpdatePlan(TeachPlanVerify teachPlan) {
        teachPlan.setVerifyStatus(VERIFY_STATUS_APPLY);
        if (StrUtil.isBlank(teachPlan.getPlanId())) {
            String planId = IdUtil.fastSimpleUUID();
            teachPlan.setPlanId(planId);
            return teachPlanVerifyRepository.save(teachPlan);
        } else {
            Optional<TeachPlanVerify> optional = teachPlanVerifyRepository.findById(teachPlan.getPlanId());
            MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "要修改的计划不存在");
            TeachPlanVerify t = optional.get();
            BeanUtil.copyProperties(teachPlan, t);
            return teachPlanVerifyRepository.save(t);
        }
    }

    private void saveTeachPlanCourse(String planId, List<TeachPlanCourseVo> courses, String remark, String centerAreaId, String userId) {
        List<TeachPlanCourseVerify> planCourseList = courses.parallelStream().filter(Objects::nonNull)
                .map(t -> createTeachPlanCourse(planId, t, remark, centerAreaId, userId))
                .collect(toList());
        if (!planCourseList.isEmpty()) {
            teachPlanCourseService.saveAllVerify(planCourseList);
        }
    }

    private TeachPlanCourseVerify createTeachPlanCourse(String planId, TeachPlanCourseVo vo, String remark, String centerAreaId, String userId) {
        String teacherName = teacherService.findById(vo.getTeacherId()).getTeacherName();
        return new TeachPlanCourseVerify(planId, vo.getCourseId(), onLineCourseDicService.findId(vo.getCourseId()).getCourseName(),
                vo.getCredit(), vo.getOnLinePercentage(), vo.getLinePercentage(), vo.getTeacherId(), teacherName, centerAreaId, remark, userId, VERIFY_STATUS_APPLY);
    }

    private void saveTeachPlanClass(String planId, TeachPlanVerify teachPlan, List<String> classIds, String remark, String centerAreaId, String userId) {
        List<TeachPlanClassVerify> planClassList = classIds.parallelStream().filter(Objects::nonNull)
                .map(c -> new TeachPlanClassVerify(c, planId, tbClassService.findClassByClassId(c).getClassName(), teachPlan.getPlanName(), studentOnLineService.countByClassId(c), centerAreaId, remark, userId, VERIFY_STATUS_APPLY))
                .collect(toList());
        teachPlanClassVerifyRepository.saveAll(planClassList);
        int sumNumber = planClassList.stream().mapToInt(TeachPlanClassVerify::getClassNumber).sum();
        teachPlan.setSumNumber(sumNumber);
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlanVerify saveUpdatePlanClass(String planId, List<String> classIds, String remark, String centerAreaId, String userId) {
        Optional<TeachPlanVerify> teachPlanOptional = teachPlanVerifyRepository.findById(planId);
        MyAssert.isFalse(teachPlanOptional.isPresent(), DefineCode.ERR0010, "不存在对应的计划编号");
        TeachPlanVerify teachPlan = teachPlanOptional.get();
        teachPlanClassVerifyRepository.deleteAllByPlanId(planId);
        saveTeachPlanClass(planId, teachPlan, classIds, remark, centerAreaId, userId);
        if (!classIds.isEmpty()) {
            teachPlan.setClassNumber(classIds.size());
        }
        return teachPlanVerifyRepository.save(teachPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlanVerify saveUpdatePlanCourse(String planId, List<TeachPlanCourseVo> courses, String remark, String centerAreaId, String userId) {
        Optional<TeachPlanVerify> teachPlanOptional = teachPlanVerifyRepository.findById(planId);
        MyAssert.isFalse(teachPlanOptional.isPresent(), DefineCode.ERR0010, "不存在对应的计划编号");
        TeachPlanVerify teachPlan = teachPlanOptional.get();
        teachPlanCourseVerifyRepository.deleteAllByPlanId(planId);
        saveTeachPlanCourse(planId, courses, remark, centerAreaId, userId);
        if (!courses.isEmpty()) {
            teachPlan.setCourseNumber(courses.size());
        }
        return teachPlanVerifyRepository.save(teachPlan);
    }

    public Page<TeachPlan> findByPlanIdPageAll(String centerAreaId, String planId, Pageable pageable) {
        return teachPlanRepository.findAllByIsValidatedEqualsAndCenterAreaIdAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, planId, pageable);
    }

    public Page<TeachPlan> findPageAll(String centerAreaId, Pageable pageable) {
        return teachPlanRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByPlanId(String planId) {
        teachPlanClassRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
        teachPlanRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
        teachPlanClassRepository.updateIsValidatedByPlanId(TAKE_EFFECT_CLOSE, planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        teachPlanClassRepository.deleteAllByPlanId(planId);
        teachPlanRepository.deleteByPlanId(planId);
    }

    public List<TeachPlanClass> findAllClassByPlanId(String planId) {
        return teachPlanClassRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId);
    }

    public Page<TeachPlanDto> findAllPageByPlanIdAndVerifyStatus(String planId, String verifyStatus, Pageable pageable) {
        if (StrUtil.isNotBlank(planId) && StrUtil.isNotBlank(verifyStatus)) {
            return teachPlanVerifyRepository.findAllPageByPlanIdAndVerifyStatusDto(planId, verifyStatus, pageable);
        } else if (StrUtil.isNotBlank(planId) && StrUtil.isBlank(verifyStatus)) {
            return teachPlanVerifyRepository.findAllPageByPlanIdDto(planId, pageable);
        } else if (StrUtil.isNotBlank(verifyStatus) && StrUtil.isBlank(planId)) {
            return teachPlanVerifyRepository.findAllPageByVerifyStatusDto(verifyStatus, pageable);
        } else {
            return teachPlanVerifyRepository.findAllPageDto(pageable);
        }
    }


    public Page<TeachPlanDto> findAllPageDtoByCenterAreaId(String centerAreaId, String verifyStatus, Pageable pageable) {
        if (StrUtil.isBlank(verifyStatus)) {
            return teachPlanVerifyRepository.findAllPageByCenterAreaIdDto(centerAreaId, pageable);
        } else {
            return teachPlanVerifyRepository.findAllPageByVerifyStatusAndCenterAreaIdDto(verifyStatus, centerAreaId, pageable);
        }
    }

    public Page<TeachPlanDto> findAllPageDtoByCenterAreaIdAndPlanId(String centerAreaId, String planId, String verifyStatus, Pageable pageable) {
        if (StrUtil.isBlank(verifyStatus)) {
            return teachPlanVerifyRepository.findAllPageByCenterAreaIdAndPlanIdDto(centerAreaId, planId, pageable);
        } else {
            return teachPlanVerifyRepository.findAllPageByPlanIdAndCenterAreaIdDto(planId, verifyStatus, centerAreaId, pageable);
        }
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateClassByPlanId(String planId, String status, String userId) {
        List<TeachPlanClass> list = teachPlanClassRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        teachPlanClassRepository.saveAll(list);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateCourseByPlanId(String planId, String status, String userId) {
        List<TeachPlanCourse> list = teachPlanCourseRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        teachPlanCourseRepository.saveAll(list);
    }

    public void updateStatus(String planId, String userId) {
        Optional<TeachPlan> optionalTeachPlan = teachPlanRepository.findById(planId);
        MyAssert.isFalse(optionalTeachPlan.isPresent(), DefineCode.ERR0014, "不存在对应的计划信息");
        optionalTeachPlan.ifPresent(t -> {
            String status = t.getIsValidated();
            if (TAKE_EFFECT_CLOSE.equals(status)) {
                t.setIsValidated(TAKE_EFFECT_OPEN);
                // 修改班级计划状态
                updateClassByPlanId(planId, TAKE_EFFECT_OPEN, userId);
                //修改课程计划状态
                updateCourseByPlanId(planId, TAKE_EFFECT_OPEN, userId);
                //修改计划文件状态
                planFileService.updateStatus(planId, TAKE_EFFECT_OPEN, userId);
                //修改审核的计划信息
                setVerifyStatus(planId, TAKE_EFFECT_OPEN, userId);
            } else {
                t.setIsValidated(TAKE_EFFECT_CLOSE);
                updateClassByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                updateCourseByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                planFileService.updateStatus(planId, TAKE_EFFECT_CLOSE, userId);
                //修改审核的计划信息
                setVerifyStatus(planId, TAKE_EFFECT_CLOSE, userId);
            }
            t.setUpdateUser(userId);
            teachPlanRepository.save(t);
        });
    }

    private void setVerifyStatus(String planId, String status, String userId){
        //修改教学计划
        updateTeachPlanVerify(planId, status, userId);
        //修改教学计划班级
        updateClassVerifyByPlanId(planId, status, userId);
        //修改教学计划课程
        updateVerfyCourseByPlanId(planId, status, userId);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateTeachPlanVerify(String planId, String status, String userId){
        List<TeachPlanVerify> collect = teachPlanVerifyRepository.findAllByPlanId(planId).stream().peek(t -> {
            t.setIsValidated(status);
            t.setUpdateUser(userId);
        }).collect(toList());
        if (!collect.isEmpty()) {
            teachPlanVerifyRepository.saveAll(collect);
        }
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

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateClassVerifyByPlanId(String planId, String status, String userId) {
        List<TeachPlanClassVerify> list = teachPlanClassVerifyRepository.findAllByPlanId(planId).stream().peek(c -> {
            c.setIsValidated(status);
            c.setUpdateUser(userId);
        }).collect(Collectors.toList());
        if (!list.isEmpty()){
            teachPlanClassVerifyRepository.saveAll(list);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachPlan(String planId, String verifyStatus, String remark, String userId) {
        //修改对应的计划信息
        updateVerifyTeachPlan(planId, verifyStatus, remark, userId);
        //修改对应的班级信息
        updateVerifyPlanClass(planId, verifyStatus, remark, userId);
        //修改对应的课程信息
        teachPlanCourseService.updateVerifyPlanCourse(planId, verifyStatus, remark, userId);
    }


    void updateVerifyPlanClass(String planId, String verifyStatus, String remark, String userId) {
        // 删除原来的班级
        teachPlanClassRepository.deleteAllByPlanId(planId);
        List<TeachPlanClass> teachPlanClassList = new ArrayList<>();
        List<TeachPlanClassVerify> teachPlanClassVerifyList = teachPlanClassVerifyRepository
                .findAllByVerifyStatusAndPlanId(VERIFY_STATUS_APPLY, planId)
                .stream()
                .filter(Objects::nonNull)
                .peek(t -> {
                    t.setRemark(remark);
                    t.setVerifyStatus(verifyStatus);
                    t.setUpdateUser(userId);
                    if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
                        TeachPlanClass p = new TeachPlanClass();
                        BeanUtil.copyProperties(t, p);
                        p.setUpdateUser(userId);
                        teachPlanClassList.add(p);
                    }
                }).collect(Collectors.toList());
        if (!teachPlanClassVerifyList.isEmpty()) {
            teachPlanClassVerifyRepository.saveAll(teachPlanClassVerifyList);
        }
        if (!teachPlanClassList.isEmpty()) {
            teachPlanClassRepository.saveAll(teachPlanClassList);
        }
    }

    /**
     * 修改计划信息
     */
    void updateVerifyTeachPlan(String planId, String verifyStatus, String remark, String userId) {
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
}