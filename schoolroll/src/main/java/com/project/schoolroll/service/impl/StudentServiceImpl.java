package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.repository.FamilyRepository;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import com.project.schoolroll.repository.dto.StudentPeopleDto;
import com.project.schoolroll.service.StudentService;
import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
import com.project.schoolroll.web.vo.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-1 15:12
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class StudentServiceImpl extends BaseMySqlService implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentPeopleRepository studentPeopleRepository;
    private final FamilyRepository familyRepository;
    private final StudentExpandRepository studentExpandRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository,
                              StudentExpandRepository studentExpandRepository, FamilyRepository familyRepository) {
        this.studentRepository = studentRepository;
        this.studentPeopleRepository = studentPeopleRepository;
        this.studentExpandRepository = studentExpandRepository;
        this.familyRepository = familyRepository;
    }


    @Override
    public Page<StudentVo> findStudentsPageAll(FindStudentDtoPageAllVo vo) {
        StringBuilder dataSql = new StringBuilder(" SELECT s.stu_id,s.stu_name,s.specialty_id,s.specialty_name,s.people_id,s.center_id,lc.center_name,s.student_category, " +
                " s.class_id,s.class_name,s.educational_system,s.ways_study,s.learning_modality,s.ways_enrollment,s.enrollment_date, " +
                " s.grade,s.entrance_certificate_number,s.candidate_number,s.total_examination_achievement,sp.gender,sp.stu_phone,sp.stu_id_card " +
                " FROM student as s " +
                " LEFT JOIN learn_center as lc ON s.center_id = lc.center_id " +
                " LEFT JOIN student_people as sp on s.people_id = sp.people_id ");
        StringBuilder whereSql = new StringBuilder(" WHERE s.is_validated = '0' AND lc.is_validated = '0' AND sp.is_validated = '0' ");
        StringBuilder countSql = new StringBuilder(" SELECT COUNT(1) FROM student AS s LEFT JOIN learn_center AS lc ON lc.center_id = s.center_id LEFT JOIN student_people as sp on s.people_id = sp.people_id ");
        // 拼接whereSql
        if (StrUtil.isNotBlank(vo.getStuId())) {
            whereSql.append(" AND s.stu_id = :stuId");
        }
        if (StrUtil.isNotBlank(vo.getStuName())) {
            whereSql.append(" AND s.stu_name like '").append(vo.getStuName()).append("%'");
        }
        if (CollUtil.isNotEmpty(vo.getCenterIds())) {
            whereSql.append(" AND s.center_id in (");
            whereSql.append(String.join(",", vo.getCenterIds().stream().map(s -> "'".concat(s).concat("'")).collect(toList())));
            whereSql.append(")");
        }
        if (StrUtil.isNotBlank(vo.getClassId())) {
            whereSql.append(" AND s.class_id = :classId");
        }
        if (StrUtil.isNotBlank(vo.getEducationalSystem())) {
            whereSql.append(" AND s.educational_system = :educationalSystem");
        }
        if (StrUtil.isNotBlank(vo.getSpecialtyId())) {
            whereSql.append(" AND s.specialty_id = :specialtyId");
        }
        if (CollUtil.isNotEmpty(vo.getSpecialtyNames())) {
            whereSql.append(" AND s.specialty_name in (");
            whereSql.append(String.join(",", vo.getSpecialtyNames().stream().map(s -> "'".concat(s).concat("'")).collect(toList())));
            whereSql.append(")");
        }
        if (CollUtil.isNotEmpty(vo.getGrades())) {
            whereSql.append(" AND s.grade in (");
            whereSql.append(String.join(",", vo.getGrades().stream().map(s -> "'".concat(s).concat("'")).collect(toList())));
            whereSql.append(")");
        }
        if (StrUtil.isNotBlank(vo.getLearningModality())) {
            whereSql.append(" AND s.learning_modality = :learningModality");
        }
        if (StrUtil.isNotBlank(vo.getStudentCategory())) {
            whereSql.append(" AND s.student_category = :studentCategory");
        }
        if (StrUtil.isNotBlank(vo.getWaysStudy())) {
            whereSql.append(" AND s.ways_study = :waysStudy");
        }
        if (StrUtil.isNotBlank(vo.getWaysEnrollment())) {
            whereSql.append(" AND s.ways_enrollment = :waysEnrollment");
        }
        if (StrUtil.isNotBlank(vo.getEnrollmentDateStartDate())) {
            whereSql.append(" AND s.enrollment_date >= ").append(vo.getEnrollmentDateStartDate());
        }
        if (StrUtil.isNotBlank(vo.getEnrollmentDateEndDate())) {
            whereSql.append(" AND s.enrollment_date <= ").append(vo.getEnrollmentDateEndDate());
        }
        if (StrUtil.isNotBlank(vo.getStuPhone())) {
            whereSql.append(" AND sp.stu_phone = :stuPhone");
        }
        //组装sql 语句
        dataSql.append(whereSql).append(" ORDER BY s.c_time DESC");
        countSql.append(whereSql);

        //创建本地查询实例
        Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), StudentVo.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        //设置参数
        if (StrUtil.isNotBlank(vo.getStuId())) {
            dataQuery.setParameter("stuId", vo.getStuId());
            countQuery.setParameter("stuId", vo.getStuId());
        }
        if (StrUtil.isNotBlank(vo.getClassId())) {
            dataQuery.setParameter("classId", vo.getClassId());
            countQuery.setParameter("classId", vo.getClassId());
        }

        if (StrUtil.isNotBlank(vo.getEducationalSystem())) {
            dataQuery.setParameter("educationalSystem", vo.getEducationalSystem());
            countQuery.setParameter("educationalSystem", vo.getEducationalSystem());
        }
        if (StrUtil.isNotBlank(vo.getSpecialtyId())) {
            dataQuery.setParameter("specialtyId", vo.getSpecialtyId());
            countQuery.setParameter("specialtyId", vo.getSpecialtyId());
        }
        if (StrUtil.isNotBlank(vo.getLearningModality())) {
            dataQuery.setParameter("learningModality", vo.getLearningModality());
            countQuery.setParameter("learningModality", vo.getLearningModality());
        }
        if (StrUtil.isNotBlank(vo.getStudentCategory())) {
            dataQuery.setParameter("studentCategory", vo.getStudentCategory());
            countQuery.setParameter("studentCategory", vo.getStudentCategory());
        }
        if (StrUtil.isNotBlank(vo.getWaysStudy())) {
            dataQuery.setParameter("waysStudy", vo.getWaysStudy());
            countQuery.setParameter("waysStudy", vo.getWaysStudy());
        }
        if (StrUtil.isNotBlank(vo.getWaysEnrollment())) {
            dataQuery.setParameter("waysEnrollment", vo.getWaysEnrollment());
            countQuery.setParameter("waysEnrollment", vo.getWaysEnrollment());
        }
        if (StrUtil.isNotBlank(vo.getStuPhone())) {
            dataQuery.setParameter("stuPhone", vo.getStuPhone());
            countQuery.setParameter("stuPhone", vo.getStuPhone());
        }
        //设置分页
        dataQuery.setFirstResult((int) vo.getPageable().getOffset());
        dataQuery.setMaxResults(vo.getPageable().getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<StudentVo> content2 = total > vo.getPageable().getOffset() ? dataQuery.getResultList() : Collections.<StudentVo>emptyList();
        return new PageImpl<>(content2, vo.getPageable(), total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Student student, StudentPeople studentPeople) {
        Optional<Student> studentOptional = studentRepository.findById(student.getStuId());
        if (studentOptional.isPresent()) {
            Student s = studentOptional.get();
            studentPeopleRepository.findById(s.getPeopleId()).ifPresent(sp -> {
                BeanUtil.copyProperties(studentPeople, sp);
                studentPeopleRepository.save(sp);
                BeanUtil.copyProperties(student, s);
                studentRepository.save(s);
            });
        } else {
            String peopleId = IdUtil.fastSimpleUUID();
            studentPeople.setPeopleId(peopleId);
            studentPeopleRepository.save(studentPeople);
            student.setStuId(IdUtil.fastSimpleUUID());
            student.setPeopleId(peopleId);
            studentRepository.save(student);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String stuId) {
        studentRepository.findById(stuId).ifPresent(student -> {
            studentPeopleRepository.deleteById(student.getPeopleId());
            studentExpandRepository.deleteAllByStuId(stuId);
            familyRepository.deleteAllByStuId(stuId);
            studentRepository.deleteById(stuId);
        });
    }

    @Override
    public StudentPeopleDto findStudentPeopleDtoByStuId(String stuId) {
        return studentPeopleRepository.findByStuId(stuId);
    }
}
