package com.project.teachplan.service;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class TeachPlanCourseService {
    private final TeachPlanCourseRepository teachPlanCourseRepository;

    @Autowired
    public TeachPlanCourseService(TeachPlanCourseRepository teachPlanCourseRepository) {
        this.teachPlanCourseRepository = teachPlanCourseRepository;
    }

    public TeachPlanCourse findByCourseId(String courseId, String planId){
        Optional<TeachPlanCourse> optionalTeachPlanCourse = teachPlanCourseRepository.findByIsValidatedEqualsAndPlanIdAndCourseId(TAKE_EFFECT_OPEN, planId, courseId);
        if (optionalTeachPlanCourse.isPresent()){
            return optionalTeachPlanCourse.get();
        }else {
            MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应课程信息");
            return null;
        }
    }

    public void saveAll(@NonNull List<TeachPlanCourse> list){
        teachPlanCourseRepository.saveAll(list);
    }

    public List<TeachPlanCourse> findAllCourseByPlanId(String planId){
        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, planId);
    }
}
