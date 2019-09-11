package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.repository.TbClassesRepository;
import com.project.schoolroll.service.online.TbClassService;
import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.repository.PlanFileRepository;
import com.project.teachplan.repository.dto.PlanFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final TbClassesRepository tbClassesRepository;
    private final TbClassService tbClassService;

    @Autowired
    public PlanFileService(TbClassesRepository tbClassesRepository, PlanFileRepository planFileRepository, TbClassService tbClassService) {
        this.planFileRepository = planFileRepository;
        this.tbClassesRepository = tbClassesRepository;
        this.tbClassService = tbClassService;
    }


    /**
     * 文件明细添加
     */
    @Transactional(rollbackFor = Exception.class)
    public PlanFile save(PlanFile classFile) {
        classFile.setFileId(IdUtil.fastSimpleUUID());
        return planFileRepository.save(classFile);
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

    /**
     * @param centerAreaId 获取文件列表
     * @param classId
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<PlanFile> findByCenterAreaIdAndClassIdAllPage(String centerAreaId, String classId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndCenterAreaIdAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, classId, pageable);
    }


    /**
     * @param centerAreaId 获取文件列表
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<PlanFile> findByCenterAreaIdAllPage(String centerAreaId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageable);
    }


    public Page<PlanFile> findByPlanIdPageAll(String planId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, pageable);
    }

    public Page<PlanFile> findAllPage(Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }


    public Page<PlanFile> findByClassIdPageAll(String classId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, classId, pageable);
    }

//    public Page<PlanFile> findByPlanIdAndCourseIdPageAll(String planId, String courseId, Pageable pageable){
//        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndCourseIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, courseId, pageable);
//    }

//    public Page<PlanFile> findByPlanIdAndClassIdPageAll(String planId, String classId, Pageable pageable){
//        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, classId, pageable);
//    }


    /**
     * 返回计划下的班级文件资料数量
     *
     * @param planId
     * @return
     */
    public int countClass(String planId) {
        return planFileRepository.countClass(planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByClassId(String classId) {
        List<PlanFile> list = planFileRepository.findAllByIsValidatedEqualsAndClassId(TAKE_EFFECT_OPEN, classId)
                .stream()
                .peek(classFile -> classFile.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        planFileRepository.saveAll(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByPlanId(String planId) {
        List<PlanFile> list = planFileRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, planId)
                .stream()
                .peek(classFile -> classFile.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        planFileRepository.saveAll(list);
    }

    public Page<PlanFileDto> findAllPagePlanId(Pageable of){
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
        return null;
    }
}