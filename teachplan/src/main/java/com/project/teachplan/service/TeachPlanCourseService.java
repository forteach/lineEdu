package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.repository.dto.CourseTeacherDto;
import com.project.teachplan.repository.verify.TeachPlanCourseVerifyRepository;
import com.project.teachplan.repository.verify.TeachPlanVerifyRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.base.common.keyword.Dic.VERIFY_STATUS_AGREE;
import static java.util.stream.Collectors.toSet;

@Service
public class TeachPlanCourseService {
    private final TeachPlanCourseRepository teachPlanCourseRepository;
    private final TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository;
    private final TeachPlanRepository teachPlanRepository;
    private final TeachPlanVerifyRepository teachPlanVerifyRepository;

    @Autowired
    public TeachPlanCourseService(TeachPlanCourseRepository teachPlanCourseRepository, TeachPlanVerifyRepository teachPlanVerifyRepository,
                                  TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository, TeachPlanRepository teachPlanRepository) {
        this.teachPlanCourseRepository = teachPlanCourseRepository;
        this.teachPlanCourseVerifyRepository = teachPlanCourseVerifyRepository;
        this.teachPlanRepository = teachPlanRepository;
        this.teachPlanVerifyRepository = teachPlanVerifyRepository;
    }

    public void saveAll(@NonNull List<TeachPlanCourse> list) {
        teachPlanCourseRepository.saveAll(list);
    }

    public void saveAllVerify(List<TeachPlanCourseVerify> list) {
        teachPlanCourseVerifyRepository.saveAll(list);
    }

    public List<TeachPlanCourse> findAllCourseByPlanId(String planId) {
        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, planId);
    }

    public List<TeachPlanCourseVerify> findAllCourseVerifyByPlanId(String planId) {
        return teachPlanCourseVerifyRepository.findAllByPlanId(planId);
    }

    public List<CourseTeacherDto> findCourseIdAndTeacherIdByClassId(String classId) {
        //查询计划Id
        List<String> planIds = teachPlanRepository.findAllByClassId(DateUtil.today(), classId);

        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndPlanIdIn(TAKE_EFFECT_OPEN, planIds);
    }

    void updateVerifyPlanCourse(String planId, String verifyStatus, String remark, String userId) {
        List<TeachPlanCourse> teachPlanCourseList = new ArrayList<>();
        List<TeachPlanCourseVerify> teachPlanCourseVerifyList = teachPlanCourseVerifyRepository.findAllByPlanId(planId).stream().filter(Objects::nonNull)
                .peek(t -> {
                    t.setRemark(remark);
                    t.setVerifyStatus(verifyStatus);
                    t.setUpdateUser(userId);
                    if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
                        TeachPlanCourse p = new TeachPlanCourse();
                        BeanUtil.copyProperties(t, p);
                        teachPlanCourseList.add(p);
                    }
                }).collect(Collectors.toList());
        if (!teachPlanCourseVerifyList.isEmpty()) {
            teachPlanCourseVerifyRepository.saveAll(teachPlanCourseVerifyList);
        }
        //审核通过 删除原来课程信息
        if (VERIFY_STATUS_AGREE.equals(verifyStatus)) {
            teachPlanCourseRepository.deleteAllByPlanId(planId);
        }
        if (!teachPlanCourseList.isEmpty()) {
            teachPlanCourseRepository.saveAll(teachPlanCourseList);
        }
    }

    public boolean isAfterOrEqualsCourseNumberAndTeacherId(String courseNumber, String teacherId) {
        //判断是否在计划内的课程
        List<TeachPlanCourseVerify> teachPlanCourseVerifyList = teachPlanCourseVerifyRepository.findAllByCourseIdAndTeacherId(courseNumber, teacherId);

        Set<String> planIds = teachPlanCourseVerifyList.stream().map(TeachPlanCourseVerify::getPlanId).collect(toSet());

        List<TeachPlanVerify> allById = teachPlanVerifyRepository.findAllById(planIds);
        for (TeachPlanVerify t : allById) {
            if (DateUtil.parseDate(t.getEndDate()).isAfterOrEquals(new Date())) {
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTeachCourseByCourseNumberAndTeacherId(String courseNumber, String teacherId) {
        teachPlanCourseVerifyRepository.deleteAllByCourseIdAndTeacherId(courseNumber, teacherId);
        teachPlanCourseRepository.deleteAllByCourseIdAndTeacherId(courseNumber, teacherId);
    }
}