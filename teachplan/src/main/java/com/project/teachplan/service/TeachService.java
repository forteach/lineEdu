package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseService;
import com.project.course.service.OnLineCourseDicService;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.schoolroll.service.online.TbClassService;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.online.TeachPlan;
import com.project.teachplan.domain.online.TeachPlanClass;
import com.project.teachplan.repository.TeachPlanClassRepository;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.vo.TeachPlanCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CourseService courseService;
    private final OnLineCourseDicService onLineCourseDicService;

    @Autowired
    public TeachService(StudentOnLineService studentOnLineService, TeachPlanRepository teachPlanRepository,
                        TeachPlanCourseRepository teachPlanCourseRepository, CourseService courseService,
                        TbClassService tbClassService, TeachPlanClassRepository teachPlanClassRepository,
                        TeachPlanCourseService teachPlanCourseService, OnLineCourseDicService onLineCourseDicService) {
        this.studentOnLineService = studentOnLineService;
        this.teachPlanRepository = teachPlanRepository;
        this.tbClassService = tbClassService;
        this.teachPlanClassRepository = teachPlanClassRepository;
        this.teachPlanCourseService = teachPlanCourseService;
        this.teachPlanCourseRepository = teachPlanCourseRepository;
        this.courseService = courseService;
        this.onLineCourseDicService = onLineCourseDicService;
    }


    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlan(TeachPlan teachPlan) {
        if (StrUtil.isBlank(teachPlan.getPlanId())) {
            String planId = IdUtil.fastSimpleUUID();
//            saveTeachPlanClass(planId, teachPlan, classIds);
            teachPlan.setPlanId(planId);
//            setTeachPlanNumber(teachPlan, classIds, courseIds);
//            saveTeachPlanCourse(planId, courseIds);
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

    private void saveTeachPlanCourse(String planId, List<TeachPlanCourseVo> courses) {
        List<TeachPlanCourse> planCourseList = courses.parallelStream().filter(Objects::nonNull)
                .map(t -> createTeachPlanCourse(planId, t))
                .collect(toList());
        teachPlanCourseService.saveAll(planCourseList);
    }

    private TeachPlanCourse createTeachPlanCourse(String planId, TeachPlanCourseVo vo) {
        return new TeachPlanCourse(planId, vo.getCourseId(), onLineCourseDicService.findId(vo.getCourseId()).getCourseName(),
                vo.getCredit(), vo.getOnLinePercentage(), vo.getLinePercentage(), vo.getTeacherId(), vo.getTeacherName());
    }

    private void saveTeachPlanClass(String planId, TeachPlan teachPlan, List<String> classIds) {
        List<TeachPlanClass> planClassList = classIds.parallelStream().filter(Objects::nonNull)
                .map(c -> new TeachPlanClass(c, planId, tbClassService.findClassByClassId(c).getClassName(), teachPlan.getPlanName(), studentOnLineService.countByClassId(c)))
                .collect(toList());
        teachPlanClassRepository.saveAll(planClassList);
        int sumNumber = planClassList.stream().mapToInt(TeachPlanClass::getClassNumber).sum();
        teachPlan.setSumNumber(sumNumber);
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlanClass(String planId, List<String> classIds) {
        Optional<TeachPlan> teachPlanOptional = teachPlanRepository.findById(planId);
        if (teachPlanOptional.isPresent()) {
            TeachPlan teachPlan = teachPlanOptional.get();
            teachPlanClassRepository.deleteAllByPlanId(planId);
            saveTeachPlanClass(planId, teachPlan, classIds);
            setTeachPlanNumber(teachPlan, classIds, new ArrayList<>());
            return teachPlanRepository.save(teachPlan);
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应的计划编号");
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlan saveUpdatePlanCourse(String planId, List<TeachPlanCourseVo> courses) {
        Optional<TeachPlan> teachPlanOptional = teachPlanRepository.findById(planId);
        if (teachPlanOptional.isPresent()) {
            TeachPlan teachPlan = teachPlanOptional.get();
            teachPlanCourseRepository.deleteAllByPlanId(planId);
            saveTeachPlanCourse(planId, courses);
            if (!courses.isEmpty()){
                teachPlan.setCourseNumber(courses.size());
            }
            return teachPlanRepository.save(teachPlan);
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应的计划编号");
        return null;
    }

    public Page<TeachPlan> findByPlanIdPageAll(String planId, Pageable pageable) {
        return teachPlanRepository.findByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, pageable);
    }

    public Page<TeachPlan> findPageAll(Pageable pageable) {
        return teachPlanRepository.findByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
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
}