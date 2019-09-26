package com.project.user.repository;


import com.project.user.domain.Teacher;
import com.project.user.repository.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:45
 * @version: 1.0
 * @description:
 */
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    @Transactional(readOnly = true)
    Page<Teacher> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Teacher> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId, Pageable pageable);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEqualsAndTeacherCode(String isValidated, String teacherCode);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEqualsAndTeacherName(String isValidated, String teacherName);

    @Modifying(clearAutomatically = true)
    int deleteTeacherByTeacherCode(String teacherCode);

    @Query(value = "select " +
            " t.teacherId as teacherId, " +
            " t.teacherName as teacherName, " +
            " t.teacherCode as teacherCode, " +
            " t.gender as gender, " +
            " t.birthDate as birthDate, " +
            " t.idCard as idCard, " +
            " t.professionalTitle as professionalTitle, " +
            " t.professionalTitleDate as professionalTitleDate, " +
            " t.position as position, " +
            " t.industry as industry, " +
            " t.email as email, " +
            " t.phone as phone, " +
            " t.specialty as specialty, " +
            " t.isFullTime as isFullTime, " +
            " t.academicDegree as academicDegree, " +
            " t.bankCardAccount as bankCardAccount," +
            " t.bankCardBank as bankCardBank, " +
            " t.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName " +
            " from Teacher as t " +
            " left join LearnCenter as lc on lc.centerId = t.centerAreaId " +
            " where t.isValidated = '0' and t.centerAreaId = ?1 order by t.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeacherDto> findAllByCenterAreaIdDto(String centerAreaId, Pageable pageable);
}