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
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.TeachPlanClassRepository;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.repository.dto.TeachPlanDto;
import com.project.teachplan.repository.verify.PlanFileVerityRepository;
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

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
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

    private final PlanFileVerityRepository planFileVerityRepository;
    private final TeachPlanVerifyRepository teachPlanVerifyRepository;
    private final TeachPlanClassVerifyRepository teachPlanClassVerifyRepository;
    private final TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository;

    @Autowired
    public TeachService(StudentOnLineService studentOnLineService, TeachPlanRepository teachPlanRepository,
                        TeachPlanCourseRepository teachPlanCourseRepository, TbClassService tbClassService,
                        TeachPlanClassRepository teachPlanClassRepository, TeacherService teacherService,
                        PlanFileService planFileService, TeachPlanCourseService teachPlanCourseService,
                        OnLineCourseDicService onLineCourseDicService, PlanFileVerityRepository planFileVerityRepository,
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
        this.planFileVerityRepository = planFileVerityRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlan(TeachPlan teachPlan) {
        if (StrUtil.isBlank(teachPlan.getPlanId())) {
            String planId = IdUtil.fastSimpleUUID();
            teachPlan.setPlanId(planId);
            return teachPlanRepository.save(teachPlan);
        } else {
            Optional<TeachPlan> optional = teachPlanRepository.findById(teachPlan.getPlanId());
            if (optional.isPresent()) {
                TeachPlan t = optional.get();
                BeanUtil.copyProperties(teachPlan, t);
                return teachPlanRepository.save(t);
            }
            MyAssert.isNull(null, DefineCode.ERR0010, "要修改的计划不存在");
            return null;
        }
    }

    private void setTeachPlanNumber(TeachPlan teachPlan, List<String> classIds, List<String> courseIds) {
        if (!courseIds.isEmpty()) {
            teachPlan.setCourseNumber(courseIds.size());
        }
        if (!classIds.isEmpty()) {
            teachPlan.setClassNumber(classIds.size());
        }
    }

    private void saveTeachPlanCourse(String planId, List<TeachPlanCourseVo> courses, String centerAreaId, String userId) {
        List<TeachPlanCourse> planCourseList = courses.parallelStream().filter(Objects::nonNull)
                .map(t -> createTeachPlanCourse(planId, t, centerAreaId, userId))
                .collect(toList());
        teachPlanCourseService.saveAll(planCourseList);
    }

    private TeachPlanCourse createTeachPlanCourse(String planId, TeachPlanCourseVo vo, String centerAreaId, String userId) {
        String teacherName = teacherService.findById(vo.getTeacherId()).getTeacherName();
        return new TeachPlanCourse(planId, vo.getCourseId(), onLineCourseDicService.findId(vo.getCourseId()).getCourseName(),
                vo.getCredit(), vo.getOnLinePercentage(), vo.getLinePercentage(), vo.getTeacherId(), teacherName, centerAreaId, userId);
    }

    private void saveTeachPlanClass(String planId, TeachPlan teachPlan, List<String> classIds, String centerAreaId, String userId) {
        List<TeachPlanClass> planClassList = classIds.parallelStream().filter(Objects::nonNull)
                .map(c -> new TeachPlanClass(c, planId, tbClassService.findClassByClassId(c).getClassName(), teachPlan.getPlanName(), studentOnLineService.countByClassId(c), centerAreaId, userId))
                .collect(toList());
        teachPlanClassRepository.saveAll(planClassList);
        int sumNumber = planClassList.stream().mapToInt(TeachPlanClass::getClassNumber).sum();
        teachPlan.setSumNumber(sumNumber);
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlanClass(String planId, List<String> classIds, String centerAreaId, String userId) {
        Optional<TeachPlan> teachPlanOptional = teachPlanRepository.findById(planId);
        if (teachPlanOptional.isPresent()) {
            TeachPlan teachPlan = teachPlanOptional.get();
            teachPlanClassRepository.deleteAllByPlanId(planId);
            saveTeachPlanClass(planId, teachPlan, classIds, centerAreaId, userId);
            setTeachPlanNumber(teachPlan, classIds, new ArrayList<>());
            return teachPlanRepository.save(teachPlan);
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应的计划编号");
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlanCourse(String planId, List<TeachPlanCourseVo> courses, String centerAreaId, String userId) {
        Optional<TeachPlan> teachPlanOptional = teachPlanRepository.findById(planId);
        if (teachPlanOptional.isPresent()) {
            TeachPlan teachPlan = teachPlanOptional.get();
            teachPlanCourseRepository.deleteAllByPlanId(planId);
            saveTeachPlanCourse(planId, courses, centerAreaId, userId);
            if (!courses.isEmpty()) {
                teachPlan.setCourseNumber(courses.size());
            }
            return teachPlanRepository.save(teachPlan);
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应的计划编号");
        return null;
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
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        teachPlanClassRepository.deleteAllByPlanId(planId);
        teachPlanRepository.deleteByPlanId(planId);
    }

    public List<TeachPlanClass> findAllClassByPlanId(String planId) {
        return teachPlanClassRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId);
    }

    public Page<TeachPlanDto> findAllPageDto(Pageable pageable) {
        return teachPlanRepository.findAllPageDto(pageable);
    }

    public Page<TeachPlanDto> findAllPageByPlanIdDto(String planId, Pageable pageable) {
        return teachPlanRepository.findAllPageByPlanIdDto(planId, pageable);
    }

    public Page<TeachPlanDto> findAllPageDtoByCenterAreaId(String centerAreaId, Pageable pageable) {
        return teachPlanRepository.findAllPageByCenterAreaIdDto(centerAreaId, pageable);
    }

    public Page<TeachPlanDto> findAllPageDtoByCenterAreaIdAndPlanId(String centerAreaId, String planId, Pageable pageable) {
        return teachPlanRepository.findAllPageByCenterAreaIdAndPlanIdDto(centerAreaId, planId, pageable);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateClassByPlanId(String planId, String status, String userId) {
        List<TeachPlanClass> list = teachPlanClassRepository.findAllByPlanId(planId).stream()
                .peek(c -> {
                    c.setIsValidated(status);
                    c.setUpdateUser(userId);
                }).collect(Collectors.toList());
        teachPlanClassRepository.saveAll(list);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateCourseByPlanId(String planId, String status, String userId) {
        List<TeachPlanCourse> list = teachPlanCourseRepository.findAllByPlanId(planId).stream()
                .peek(c -> {
                    c.setIsValidated(status);
                    c.setUpdateUser(userId);
                }).collect(Collectors.toList());
        teachPlanCourseRepository.saveAll(list);
    }

    public void updateStatus(String planId, String userId) {
        Optional<TeachPlan> optionalTeachPlan = teachPlanRepository.findById(planId);
        if (optionalTeachPlan.isPresent()) {
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
                } else {
                    t.setIsValidated(TAKE_EFFECT_CLOSE);
                    updateClassByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                    updateCourseByPlanId(planId, TAKE_EFFECT_CLOSE, userId);
                    planFileService.updateStatus(planId, TAKE_EFFECT_CLOSE, userId);
                }
                t.setUpdateUser(userId);
                teachPlanRepository.save(t);
            });
        } else {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在对应的计划信息");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachPlan(String planId, String isValidated, String remark, String userId) {
        updateVerifyTeachPlan(planId, isValidated, remark, userId);

        updateVerifyPlanClass(planId, isValidated, remark, userId);

        teachPlanCourseService.updateVerifyPlanCourse(planId, isValidated, remark, userId);
    }


    void updateVerifyPlanClass(String planId, String isValidated, String remark, String userId) {
        List<TeachPlanClass> teachPlanClassList = new ArrayList<>();
        List<TeachPlanClassVerify> teachPlanClassVerifyList = teachPlanClassVerifyRepository
                .findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_CLOSE, planId)
                .stream()
                .filter(Objects::nonNull)
                .peek(t -> {
                    t.setRemark(remark);
                    t.setIsValidated(isValidated);
                    t.setUpdateUser(userId);
                    if (TAKE_EFFECT_OPEN.equals(isValidated)) {
                        teachPlanClassRepository.findById(t.getPlanId()).ifPresent(p -> {
                            p.setIsValidated(TAKE_EFFECT_OPEN);
                            p.setUpdateUser(userId);
                            BeanUtil.copyProperties(t, p);
                            teachPlanClassList.add(p);
                        });
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
    void updateVerifyTeachPlan(String planId, String isValidated, String remark, String userId) {
        List<TeachPlan> teachPlanList = new ArrayList<>();
        List<TeachPlanVerify> teachPlanVerifies = teachPlanVerifyRepository
                .findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_CLOSE, planId)
                .stream()
                .filter(Objects::nonNull)
                .peek(t -> {
                    t.setRemark(remark);
                    t.setIsValidated(isValidated);
                    t.setUpdateUser(userId);
                    if (TAKE_EFFECT_OPEN.equals(isValidated)) {
                        teachPlanRepository.findById(t.getPlanId()).ifPresent(p -> {
                            p.setIsValidated(TAKE_EFFECT_OPEN);
                            p.setUpdateUser(userId);
                            BeanUtil.copyProperties(t, p);
                            teachPlanList.add(p);
                        });
                    }
                }).collect(Collectors.toList());
        if (!teachPlanVerifies.isEmpty()) {
            teachPlanVerifyRepository.saveAll(teachPlanVerifies);
        }
        if (!teachPlanList.isEmpty()) {
            teachPlanRepository.saveAll(teachPlanList);
        }
    }
}