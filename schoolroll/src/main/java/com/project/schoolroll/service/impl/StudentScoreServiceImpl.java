package com.project.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.repository.StudentScoreRepository;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.web.vo.OffLineScoreUpdateVo;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:38
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class StudentScoreServiceImpl extends BaseMySqlService implements StudentScoreService {
    private final StudentScoreRepository studentScoreRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public StudentScoreServiceImpl(StudentScoreRepository studentScoreRepository) {
        this.studentScoreRepository = studentScoreRepository;
    }

    @Override
    public StudentScore findByStudentIdAndCourseId(String studentId, String courseId) {
        return studentScoreRepository.findAllByIsValidatedEqualsAndStudentIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId)
                .orElseGet(StudentScore::new);
    }

    @Override
    public List<StudentScore> findByStudentId(String studentId) {
        return studentScoreRepository.findAllByIsValidatedEqualsAndStudentIdOrderByUpdateTime(TAKE_EFFECT_OPEN, studentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudentScoreById(String scoreId) {
        studentScoreRepository.deleteById(scoreId);
    }

    @Override
    public Page<StudentScore> findStudentScorePageAll(StudentScorePageAllVo pageAllVo, PageRequest of) {

        StringBuilder dateSql = new StringBuilder("select * from student_score ");
        StringBuilder whereSql = new StringBuilder("where is_validated = '0'");
        StringBuilder countSql = new StringBuilder("select count(1) from student_score ");
        if (StrUtil.isNotBlank(pageAllVo.getStudentId())){
            whereSql.append(" and student_id = :studentId");
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseId())){
            whereSql.append(" and course_id = :courseId");
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseType())){
            whereSql.append(" and course_type = :courseType");
        }
        if (StrUtil.isNotBlank(pageAllVo.getTerm()))
        {
            whereSql.append(" and term = :term");
        }
        if (StrUtil.isNotBlank(pageAllVo.getSchoolYear())){
            whereSql.append(" and school_year = :schoolYear");
        }
        if (StrUtil.isNotBlank(pageAllVo.getStartDate())){
            whereSql.append(" and u_time >= ").append(pageAllVo.getStartDate());
        }
        if (StrUtil.isNotBlank(pageAllVo.getEndDate())){
            whereSql.append(" and u_time <= ").append(pageAllVo.getEndDate());
        }

        dateSql.append(whereSql).append(" order by u_time desc");
        countSql.append(whereSql);

        Query dataQuery = entityManager.createNativeQuery(dateSql.toString(), StudentScore.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());

        if (StrUtil.isNotBlank(pageAllVo.getStudentId())){
            dataQuery.setParameter("studentId", pageAllVo.getStudentId());
            countQuery.setParameter("studentId", pageAllVo.getStudentId());
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseId())){
            dataQuery.setParameter("courseId", pageAllVo.getCourseId());
            countQuery.setParameter("courseId", pageAllVo.getCourseId());
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseType())){
            dataQuery.setParameter("courseType", pageAllVo.getCourseType());
            countQuery.setParameter("courseType", pageAllVo.getCourseType());
        }
        if (StrUtil.isNotBlank(pageAllVo.getTerm())) {
            dataQuery.setParameter("term", pageAllVo.getTerm());
            countQuery.setParameter("term", pageAllVo.getTerm());
        }
        if (StrUtil.isNotBlank(pageAllVo.getSchoolYear())){
            dataQuery.setParameter("schoolYear", pageAllVo.getSchoolYear());
            countQuery.setParameter("schoolYear", pageAllVo.getSchoolYear());
        }

        //设置分页
        dataQuery.setFirstResult((int) of.getOffset());
        dataQuery.setMaxResults(of.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<StudentScore> content2 = total > of.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        return new PageImpl<>(content2, of, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOffLineScore(OffLineScoreUpdateVo vo) {
        studentScoreRepository.findById(vo.getScoreId()).ifPresent(s -> {
            s.setOffLineScore(vo.getOffLineScore());
            s.setUpdateUser(vo.getUpdateUser());
            studentScoreRepository.save(s);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<StudentScore> list) {
        studentScoreRepository.saveAll(list);
    }

    @Override
    public List<List<String>> exportScore(String centerId) {
        List<String> head = setExportHead();
        List<List<String>> list = findStudentScoreLists(centerId);
        list.add(0, head);
        return list;
    }

    private List<List<String>> findStudentScoreLists(String centerId){
        return studentScoreRepository.findAllByIsValidatedEqualsAndCenterAreaId(centerId)
                .stream()
                .map(o -> CollUtil.newArrayList(o.getStudentId(), o.getStudentName(), o.getGender(), o.getCourseName(), o.getSchoolYear(), o.getTerm(),
                        String.valueOf(o.getCourseScore()), o.getOnLineScore(), o.getOffLineScore(), o.getCourseType()))
                .collect(toList());
    }

    private List<String> setExportHead(){
        return CollUtil.newLinkedList("学号",
                "姓名",
                "性别",
                "课程",
                "学年",
                "学期",
                "课程分数",
                "线上成绩",
                "线下成绩",
                "课程类别(必修(bx)、选修(xx)、实践(sj)");
    }
}
