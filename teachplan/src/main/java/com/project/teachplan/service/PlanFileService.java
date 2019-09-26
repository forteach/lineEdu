package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.OnLineCourseDicService;
import com.project.mysql.service.BaseMySqlService;
import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.domain.TeachPlanFileList;
import com.project.teachplan.repository.PlanFileRepository;
import com.project.teachplan.repository.TeachPlanClassRepository;
import com.project.teachplan.repository.TeachPlanFileListRepository;
import com.project.teachplan.repository.dto.PlanFileDto;
import com.project.teachplan.repository.dto.TeachPlanClassDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

/**
 * 班级资料
 */

@Slf4j
@Service
public class PlanFileService extends BaseMySqlService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PlanFileRepository planFileRepository;
    private final TeachPlanClassRepository teachPlanClassRepository;
    private final OnLineCourseDicService onLineCourseDicService;
    private final TeachPlanFileListRepository teachPlanFileListRepository;

    @Autowired
    public PlanFileService(PlanFileRepository planFileRepository,OnLineCourseDicService onLineCourseDicService,
                           TeachPlanClassRepository teachPlanClassRepository, TeachPlanFileListRepository teachPlanFileListRepository) {
        this.planFileRepository = planFileRepository;
        this.teachPlanClassRepository = teachPlanClassRepository;
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
        return planFileRepository.save(classFile);
    }

//    @Async
    @Transactional(rollbackFor = Exception.class)
    void saveTeachPlanFileList(PlanFile classFile){
        TeachPlanFileList teachPlanFileList = new TeachPlanFileList();
        BeanUtil.copyProperties(classFile, teachPlanFileList);
        String courseName = onLineCourseDicService.findId(teachPlanFileList.getCourseId()).getCourseName();
        MyAssert.isTrue(StrUtil.isBlank(courseName), DefineCode.ERR0014, "课程名称为空");
        teachPlanFileList.setCourseName(courseName);
        teachPlanFileListRepository.save(teachPlanFileList);
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
        return obj.orElse(new PlanFile());
    }


    public Page<PlanFile> findByPlanIdPageAll(String planId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, pageable);
    }

    public Page<PlanFile> findAllPage(Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }

    public List<PlanFile> findAllPlanIdAndClassId(String planId, String classId) {
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, classId);
    }


    public Page<TeachPlanClassDto> findAllPagePlanFileDtoByCenterAreaId(String centerAreaId, Pageable pageable) {
        return teachPlanClassRepository.findAllByCenterAreaIdDto(centerAreaId, pageable);
    }

    public Page<TeachPlanClassDto> findAllPagePlanFileDtoByCenterAreaIdAndClassId(String centerAreaId, String classId, Pageable pageable) {
        return teachPlanClassRepository.findAllByCenterAreaIdAndClassIdDto(centerAreaId, classId, pageable);
    }

//    public Page<TeachPlanClassDto> findAllPagePlanId(Pageable of) {
//        StringBuilder dateSql = new StringBuilder("select * from student_score ");
//        StringBuilder whereSql = new StringBuilder("where is_validated = '0'");
//        StringBuilder countSql = new StringBuilder("select count(1) from student_score ");
//        if (StrUtil.isNotBlank(pageAllVo.getStudentId())){
//            whereSql.append(" and student_id = :studentId");
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getCourseId())){
//            whereSql.append(" and course_id = :courseId");
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getCourseType())){
//            whereSql.append(" and course_type = :courseType");
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getTerm()))
//        {
//            whereSql.append(" and term = :term");
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getSchoolYear())){
//            whereSql.append(" and school_year = :schoolYear");
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getStartDate())){
//            whereSql.append(" and u_time >= ").append(pageAllVo.getStartDate());
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getEndDate())){
//            whereSql.append(" and u_time <= ").append(pageAllVo.getEndDate());
//        }
//
//        dateSql.append(whereSql).append(" order by u_time desc");
//        countSql.append(whereSql);
//
//        Query dataQuery = entityManager.createNativeQuery(dateSql.toString(), PlanFileDto.class);
//        Query countQuery = entityManager.createNativeQuery(countSql.toString());
//
//        if (StrUtil.isNotBlank(pageAllVo.getStudentId())){
//            dataQuery.setParameter("studentId", pageAllVo.getStudentId());
//            countQuery.setParameter("studentId", pageAllVo.getStudentId());
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getCourseId())){
//            dataQuery.setParameter("courseId", pageAllVo.getCourseId());
//            countQuery.setParameter("courseId", pageAllVo.getCourseId());
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getCourseType())){
//            dataQuery.setParameter("courseType", pageAllVo.getCourseType());
//            countQuery.setParameter("courseType", pageAllVo.getCourseType());
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getTerm())) {
//            dataQuery.setParameter("term", pageAllVo.getTerm());
//            countQuery.setParameter("term", pageAllVo.getTerm());
//        }
//        if (StrUtil.isNotBlank(pageAllVo.getSchoolYear())){
//            dataQuery.setParameter("schoolYear", pageAllVo.getSchoolYear());
//            countQuery.setParameter("schoolYear", pageAllVo.getSchoolYear());
//        }

        //设置分页
//        dataQuery.setFirstResult((int) of.getOffset());
//        dataQuery.setMaxResults(of.getPageSize());
//        BigInteger count = (BigInteger) countQuery.getSingleResult();
//        long total = count.longValue();
//        List<PlanFileDto> content2 = total > of.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
//        return new PageImpl<>(content2, of, total);
//        return null;
//    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        Optional<PlanFile> optionalPlanFile = planFileRepository.findById(fileId);
        MyAssert.isFalse(optionalPlanFile.isPresent(), DefineCode.ERR0010, "不存在要删除的文件");
        optionalPlanFile.ifPresent(p -> {
            List<PlanFile> list = planFileRepository.findAllByPlanIdAndClassIdAndCourseIdAndCreateDate(p.getPlanId(), p.getClassId(), p.getCourseId(), p.getCreateDate());
            if (1 == list.size()){
                teachPlanFileListRepository.deleteAllByPlanIdAndClassIdAndCourseIdAndCreateDate(p.getPlanId(), p.getClassId(), p.getCourseId(), p.getCreateDate());
            }
            planFileRepository.deleteById(fileId);
        });
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String planId, String status, String userId) {
        List<PlanFile> fileList = planFileRepository.findAllByPlanId(planId).stream()
                .peek(p -> {
                    p.setUpdateUser(userId);
                    p.setIsValidated(status);
                }).collect(toList());
        planFileRepository.saveAll(fileList);
    }

    public List<PlanFile> findAllFileByDate(String date){
        return planFileRepository.findAllByIsValidatedEqualsAndCreateTime(date);
    }

    public Page<TeachPlanFileList> findAllPageFileListByCreateDate(String planId, String classId, String createDate, Pageable pageable){
        return teachPlanFileListRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdAndCreateDateOrderByCreateDateDesc(TAKE_EFFECT_OPEN, planId, classId, createDate, pageable);
    }
    public Page<TeachPlanFileList> findAllPageFileList(String planId, String classId, Pageable pageable){
        return teachPlanFileListRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateDateDesc(TAKE_EFFECT_OPEN, planId, classId, pageable);
    }

    public List<PlanFile> findAllByCourseIdAndCreateDate(String planId, String classId, String courseId, String createDate){
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdAndCourseIdAndCreateDateOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, classId, courseId, createDate);
    }
}