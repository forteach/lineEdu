package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.dto.CourseTeacherDto;
import com.project.teachplan.repository.verify.TeachPlanCourseVerifyRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class TeachPlanCourseService {
    private final TeachPlanCourseRepository teachPlanCourseRepository;
    private final TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository;

    @Autowired
    public TeachPlanCourseService(TeachPlanCourseRepository teachPlanCourseRepository, TeachPlanCourseVerifyRepository teachPlanCourseVerifyRepository) {
        this.teachPlanCourseRepository = teachPlanCourseRepository;
        this.teachPlanCourseVerifyRepository = teachPlanCourseVerifyRepository;
    }

    public TeachPlanCourse findByCourseId(String courseId, String planId) {
        Optional<TeachPlanCourse> optionalTeachPlanCourse = teachPlanCourseRepository.findByIsValidatedEqualsAndPlanIdAndCourseId(TAKE_EFFECT_OPEN, planId, courseId);
        if (optionalTeachPlanCourse.isPresent()) {
            return optionalTeachPlanCourse.get();
        } else {
            MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应课程信息");
            return null;
        }
    }

    public void saveAll(@NonNull List<TeachPlanCourse> list) {
        teachPlanCourseRepository.saveAll(list);
    }

    public void saveAllVerify(List<TeachPlanCourseVerify> list){
        teachPlanCourseVerifyRepository.saveAll(list);
    }

    public List<TeachPlanCourse> findAllCourseByPlanId(String planId) {
        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, planId);
    }

    public List<String> findCourseIdByClassId(String classId) {
        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndClassId(classId);
    }

    public List<CourseTeacherDto> findCourseIdAndTeacherIdByClassId(String classId) {
        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndClassIdDto(classId);
    }

    void updateVerifyPlanCourse(String planId, String isValidated, String remark, String userId) {
        List<TeachPlanCourse> teachPlanCourseList = new ArrayList<>();
        List<TeachPlanCourseVerify> teachPlanCourseVerifyList = teachPlanCourseVerifyRepository
                .findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_CLOSE, planId)
                .stream()
                .filter(Objects::nonNull)
                .peek(t -> {
                    t.setRemark(remark);
                    t.setIsValidated(isValidated);
                    t.setUpdateUser(userId);
                    if (TAKE_EFFECT_OPEN.equals(isValidated)) {
                        teachPlanCourseRepository.findById(t.getPlanId()).ifPresent(p -> {
                            p.setIsValidated(TAKE_EFFECT_OPEN);
                            p.setUpdateUser(userId);
                            BeanUtil.copyProperties(t, p);
                            teachPlanCourseList.add(p);
                        });
                    }
                }).collect(Collectors.toList());
        if (!teachPlanCourseVerifyList.isEmpty()) {
            teachPlanCourseVerifyRepository.saveAll(teachPlanCourseVerifyList);
        }
        if (!teachPlanCourseList.isEmpty()) {
            teachPlanCourseRepository.saveAll(teachPlanCourseList);
        }
    }
}