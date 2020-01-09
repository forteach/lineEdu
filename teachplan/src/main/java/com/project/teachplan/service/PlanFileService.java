package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.service.OnLineCourseDicService;
import com.project.mysql.service.BaseMySqlService;
import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.domain.TeachPlanFileList;
import com.project.teachplan.repository.PlanFileRepository;
import com.project.teachplan.repository.TeachPlanFileListRepository;
import com.project.teachplan.vo.TeachFileVerifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

/**
 * 班级资料
 */

@Slf4j
@Service
public class PlanFileService extends BaseMySqlService {

    private final PlanFileRepository planFileRepository;
    private final OnLineCourseDicService onLineCourseDicService;
    private final TeachPlanFileListRepository teachPlanFileListRepository;
//    private final TeachPlanClassRepository teachPlanClassRepository;

    @Autowired
    public PlanFileService(PlanFileRepository planFileRepository, OnLineCourseDicService onLineCourseDicService,
//                           TeachPlanClassRepository teachPlanClassRepository,
                           TeachPlanFileListRepository teachPlanFileListRepository) {
//        this.teachPlanClassRepository = teachPlanClassRepository;
        this.planFileRepository = planFileRepository;
        this.onLineCourseDicService = onLineCourseDicService;
        this.teachPlanFileListRepository = teachPlanFileListRepository;
    }


    /**
     * 文件明细添加
     */
    @Transactional(rollbackFor = Exception.class)
    public PlanFile save(PlanFile classFile) {
        classFile.setFileId(IdUtil.fastSimpleUUID());
        //异步保存计划资料列表
        saveTeachPlanFileList(classFile);
        classFile.setVerifyStatus(VERIFY_STATUS_APPLY);
        return planFileRepository.save(classFile);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String fileId, String userId) {
        planFileRepository.findById(fileId).ifPresent(p -> {
            if (TAKE_EFFECT_OPEN.equals(p.getIsValidated())) {
                p.setIsValidated(TAKE_EFFECT_CLOSE);
            } else {
                p.setIsValidated(TAKE_EFFECT_OPEN);
            }
            p.setUpdateUser(userId);
            planFileRepository.save(p);
        });
    }

    @Async
    void saveTeachPlanFileList(PlanFile classFile) {
        getTeachPlanFileList(classFile.getPlanId(), classFile.getClassId(), classFile.getCourseId(), classFile.getCreateDate());
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlanFileList getTeachPlanFileList(String planId, String classId, String courseId, String createDate) {
        TeachPlanFileList teachPlanFileList = teachPlanFileListRepository
                .findByPlanIdAndClassIdAndCourseIdAndCreateDate(planId, classId, courseId, createDate)
                .orElse(new TeachPlanFileList(courseId, planId, createDate, classId));
        if (StrUtil.isBlank(teachPlanFileList.getCourseName())) {
            OnLineCourseDic onLineCourseDic = onLineCourseDicService.findId(teachPlanFileList.getCourseId());
            String courseName = onLineCourseDic.getCourseName();
            String type = onLineCourseDic.getType();
            teachPlanFileList.setType(type);
            teachPlanFileList.setCourseName(courseName);
        }
        return teachPlanFileListRepository.save(teachPlanFileList);
    }

    /**
     * 文件修改
     */
    @Transactional(rollbackFor = Exception.class)
    public PlanFile update(PlanFile planFile) {
        PlanFile obj = findId(planFile.getFileId());
        BeanUtil.copyProperties(planFile, obj);
        return planFileRepository.save(obj);
    }


    /**
     * 文件 findById
     *
     * @param fileId
     * @return ClassFile
     */
    public PlanFile findId(String fileId) {
        Optional<PlanFile> obj = planFileRepository.findById(fileId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.orElseGet(PlanFile::new);
    }


    public Page<PlanFile> findByPlanIdPageAll(String planId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, pageable);
    }

//    public Page<PlanFile> findAllPage(Pageable pageable) {
//        return planFileRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
//    }

    private Map<String, List<PlanFile>> groupByType(List<PlanFile> list) {
        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(PlanFile::getType));
    }

    public Map<String, List<PlanFile>> findAllPlanIdAndClassId(String planId, String classId) {
        return groupByType(planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, classId));
    }


//    public Page<TeachPlanClassDto> findAllPagePlanFileDtoByCenterAreaId(String centerAreaId, Pageable pageable) {
//        return teachPlanClassRepository.findAllByCenterAreaIdDto(centerAreaId, pageable);
//    }

//    public Page<TeachPlanClassDto> findAllPagePlanFileDtoByCenterAreaIdAndClassId(String centerAreaId, String classId, Pageable pageable) {
//        return teachPlanClassRepository.findAllByCenterAreaIdAndClassIdDto(centerAreaId, classId, pageable);
//    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        Optional<PlanFile> optionalPlanFile = planFileRepository.findById(fileId);
        MyAssert.isFalse(optionalPlanFile.isPresent(), DefineCode.ERR0010, "不存在要删除的文件");
        planFileRepository.deleteById(fileId);
//        optionalPlanFile.ifPresent(p -> {
//            List<PlanFile> list = planFileRepository.findAllByPlanIdAndClassIdAndCourseIdAndCreateDate(p.getPlanId(), p.getClassId(), p.getCourseId(), p.getCreateDate());
//            if (1 == list.size()) {
//                teachPlanFileListRepository.deleteAllByPlanIdAndClassIdAndCourseIdAndCreateDate(p.getPlanId(), p.getClassId(), p.getCourseId(), p.getCreateDate());
//            }
//            planFileRepository.deleteById(fileId);
//        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileIdAndAdmin(String fileId) {
        Optional<PlanFile> optionalPlanFile = planFileRepository.findById(fileId);
        MyAssert.isFalse(optionalPlanFile.isPresent(), DefineCode.ERR0010, "不存在要删除的文件");
        String verifyStatus = optionalPlanFile.get().getVerifyStatus();
        MyAssert.isFalse(VERIFY_STATUS_AGREE.equals(verifyStatus), DefineCode.ERR0010, "已经审核过的信息不能删除");
        planFileRepository.deleteById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachFile(TeachFileVerifyVo verifyVo, String userId) {
        List<PlanFile> list = planFileRepository.findAllByPlanIdAndClassIdAndCourseIdAndCreateDate(verifyVo.getPlanId(), verifyVo.getClassId(), verifyVo.getCourseId(), verifyVo.getCreateDate())
                .stream().peek(p -> {
                    p.setVerifyStatus(verifyVo.getVerifyStatus());
                    p.setUpdateUser(userId);
                    p.setRemark(verifyVo.getRemark());
                }).collect(toList());
        planFileRepository.saveAll(list);
    }


    @Async
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String planId, String status, String userId) {
        List<PlanFile> fileList = planFileRepository.findAllByPlanId(planId).stream()
                .peek(p -> {
                    p.setUpdateUser(userId);
                    p.setIsValidated(status);
                }).collect(toList());
        if (!fileList.isEmpty()) {
            planFileRepository.saveAll(fileList);
        }

        List<TeachPlanFileList> collect = teachPlanFileListRepository.findAllByPlanId(planId).stream()
                .peek(p -> {
                    p.setUpdateUser(userId);
                    p.setIsValidated(status);
                }).collect(toList());
        if (!fileList.isEmpty()) {
            teachPlanFileListRepository.saveAll(collect);
        }
    }

//    public List<PlanFile> findAllFileByDate(String date) {
//        return planFileRepository.findAllByIsValidatedEqualsAndCreateTime(date);
//    }

    public Page<TeachPlanFileList> findAllPageFileListByCreateDate(String planId, String createDate, Pageable pageable) {
        return teachPlanFileListRepository.findAllByIsValidatedEqualsAndPlanIdAndCreateDateOrderByCreateDate(TAKE_EFFECT_OPEN, planId, createDate, pageable);
    }

    public Page<TeachPlanFileList> findAllPageFileList(String planId, Pageable pageable) {
        return teachPlanFileListRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateDate(TAKE_EFFECT_OPEN, planId, pageable);
    }

    public Map<String, List<PlanFile>> findAllByCourseIdAndCreateDate(String planId, String classId, String courseId, String createDate) {
        return groupByType(planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdAndCourseIdAndCreateDateOrderByCreateTimeAsc(TAKE_EFFECT_OPEN, planId, classId, courseId, createDate));
    }

    public Map<String, List<PlanFile>> findAllByCourseIdAndCreateDateAndVerifyStatus(String planId, String classId, String courseId, String createDate, String verifyStatus) {
        return groupByType(planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdAndCourseIdAndCreateDateAndVerifyStatusOrderByCreateTimeAsc(TAKE_EFFECT_OPEN, planId, classId, courseId, createDate, verifyStatus));
    }
}