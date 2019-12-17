package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.TeachPlanCourseRepository;
import com.project.teachplan.repository.TeachPlanRepository;
import com.project.teachplan.repository.dto.CourseTeacherDto;
import com.project.teachplan.repository.dto.IPlanStatusDto;
import com.project.teachplan.repository.verify.TeachPlanCourseVerifyRepository;
import com.project.teachplan.repository.verify.TeachPlanVerifyRepository;
import com.project.teachplan.vo.CourseTeacherVo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.base.common.keyword.Dic.VERIFY_STATUS_AGREE;
import static java.util.stream.Collectors.*;

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

    public List<CourseTeacherVo> findCourseIdAndTeacherIdByClassId(String classId) {
        //查询计划Id
//        List<IPlanStatusDto> dtos = teachPlanRepository.findAllByClassId(DateUtil.today(), classId);
//        List<String> planIds = dtos.stream().filter(Objects::nonNull).map(IPlanStatusDto::getPlanId).distinct().collect(toList());
//        return teachPlanCourseRepository.findAllByIsValidatedEqualsAndPlanIdIn(TAKE_EFFECT_OPEN, planIds)
//                .stream()
//                .filter(Objects::nonNull)
//                .map(d -> findCourseTeacherVo(d, dtos))
//                .filter(Objects::nonNull)
//                .collect(toList());
        return new ArrayList<>();
    }

    private CourseTeacherVo findCourseTeacherVo(CourseTeacherDto dto, List<IPlanStatusDto> dtos) {
        for (IPlanStatusDto i : dtos) {
            if (dto.getPlanId().equals(i.getPlanId())) {
                return new CourseTeacherVo(dto.getCourseId(), dto.getTeacherId(), i.getStatus(), i.getCountStatus());
            }
        }
        return null;
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
                }).collect(toList());
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

    /**
     * 查询对应的教师信息是否有正在执行的计划课程
     *
     * @param teacherId
     * @return
     */
    public boolean isAfterOrEqualsByTeacherId(String teacherId) {
        List<TeachPlanCourseVerify> courseVerifyList = teachPlanCourseVerifyRepository.findAllByTeacherId(teacherId);
        return isValidated(courseVerifyList);
    }

    /**
     * 查询课程对应的计划是有正在执行的计划
     *
     * @param courseNumber
     * @param teacherId
     * @return
     */
    public boolean isAfterOrEqualsCourseNumberAndTeacherId(String courseNumber, String teacherId) {
        List<TeachPlanCourseVerify> teachPlanCourseVerifyList = teachPlanCourseVerifyRepository.findAllByCourseIdAndTeacherId(courseNumber, teacherId);
        return isValidated(teachPlanCourseVerifyList);
    }

    private boolean isValidated(List<TeachPlanCourseVerify> teachPlanCourseVerifyList) {
        Set<String> planIds = teachPlanCourseVerifyList.stream().filter(Objects::nonNull).map(TeachPlanCourseVerify::getPlanId).collect(toSet());
        //查询对应的计划
        List<TeachPlanVerify> allById = teachPlanVerifyRepository.findAllById(planIds);
        //循环判断有在计划内的课程返回true
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