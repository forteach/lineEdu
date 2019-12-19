package com.project.user.repository;

import com.project.user.domain.TeacherVerify;
import com.project.user.repository.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 14:35
 * @version: 1.0
 * @description:
 */
public interface TeacherVerifyRepository extends JpaRepository<TeacherVerify, String> {

//    @Query(value = "select " +
//            " t.teacherId as teacherId, " +
//            " t.teacherName as teacherName, " +
//            " t.teacherCode as teacherCode, " +
//            " t.gender as gender, " +
//            " t.birthDate as birthDate, " +
//            " t.idCard as idCard, " +
//            " t.professionalTitle as professionalTitle, " +
//            " t.professionalTitleDate as professionalTitleDate, " +
//            " t.position as position, " +
//            " t.industry as industry, " +
//            " t.email as email, " +
//            " t.phone as phone, " +
//            " t.specialty as specialty, " +
//            " t.isFullTime as isFullTime, " +
//            " t.academicDegree as academicDegree, " +
//            " t.bankCardAccount as bankCardAccount," +
//            " t.bankCardBank as bankCardBank," +
//            " t.isValidated as isValidated, " +
//            " t.centerAreaId as centerAreaId, " +
//            " lc.centerName as centerName, " +
//            " t.remark as remark, " +
//            " t.verifyStatus AS verifyStatus " +
//            " from TeacherVerify as t " +
//            " left join LearnCenter as lc on lc.centerId = t.centerAreaId " +
//            " where t.verifyStatus = ?1 order by t.createTime desc ")
//    @Transactional(readOnly = true)
//    Page<TeacherDto> findAllByVerifyStatusEqualsDto(String verifyStatus, Pageable pageable);

//    @Query(value = "select " +
//            " t.teacherId as teacherId, " +
//            " t.teacherName as teacherName, " +
//            " t.teacherCode as teacherCode, " +
//            " t.gender as gender, " +
//            " t.birthDate as birthDate, " +
//            " t.idCard as idCard, " +
//            " t.professionalTitle as professionalTitle, " +
//            " t.professionalTitleDate as professionalTitleDate, " +
//            " t.position as position, " +
//            " t.industry as industry, " +
//            " t.email as email, " +
//            " t.phone as phone, " +
//            " t.specialty as specialty, " +
//            " t.isFullTime as isFullTime, " +
//            " t.academicDegree as academicDegree, " +
//            " t.bankCardAccount as bankCardAccount," +
//            " t.bankCardBank as bankCardBank," +
//            " t.isValidated as isValidated, " +
//            " t.centerAreaId as centerAreaId, " +
//            " lc.centerName as centerName, " +
//            " t.remark as remark, " +
//            " t.verifyStatus AS verifyStatus " +
//            " from TeacherVerify as t " +
//            " left join LearnCenter as lc on lc.centerId = t.centerAreaId " +
//            " where t.verifyStatus = ?1 and t.centerAreaId = ?2 order by t.createTime desc ")
//    @Transactional(readOnly = true)
//    Page<TeacherDto> findAllByVerifyStatusEqualsAndCenterAreaIdDto(String verifyStatus, String centerAreaId, Pageable pageable);


//    @Query(value = "select " +
//            " t.teacherId as teacherId, " +
//            " t.teacherName as teacherName, " +
//            " t.teacherCode as teacherCode, " +
//            " t.gender as gender, " +
//            " t.birthDate as birthDate, " +
//            " t.idCard as idCard, " +
//            " t.professionalTitle as professionalTitle, " +
//            " t.professionalTitleDate as professionalTitleDate, " +
//            " t.position as position, " +
//            " t.industry as industry, " +
//            " t.email as email, " +
//            " t.phone as phone, " +
//            " t.specialty as specialty, " +
//            " t.isFullTime as isFullTime, " +
//            " t.academicDegree as academicDegree, " +
//            " t.bankCardAccount as bankCardAccount," +
//            " t.bankCardBank as bankCardBank," +
//            " t.isValidated as isValidated, " +
//            " t.centerAreaId as centerAreaId, " +
//            " lc.centerName as centerName, " +
//            " t.remark as remark, " +
//            " t.verifyStatus AS verifyStatus " +
//            " from TeacherVerify as t " +
//            " left join LearnCenter as lc on lc.centerId = t.centerAreaId " +
//            " where t.centerAreaId = ?1 order by t.createTime desc ")
//    @Transactional(readOnly = true)
//    Page<TeacherDto> findAllByCenterAreaIdDto(String centerAreaId, Pageable pageable);

//    @Query(value = "select " +
//            " t.teacherId as teacherId, " +
//            " t.teacherName as teacherName, " +
//            " t.teacherCode as teacherCode, " +
//            " t.gender as gender, " +
//            " t.birthDate as birthDate, " +
//            " t.idCard as idCard, " +
//            " t.professionalTitle as professionalTitle, " +
//            " t.professionalTitleDate as professionalTitleDate, " +
//            " t.position as position, " +
//            " t.industry as industry, " +
//            " t.email as email, " +
//            " t.phone as phone, " +
//            " t.specialty as specialty, " +
//            " t.isFullTime as isFullTime, " +
//            " t.academicDegree as academicDegree, " +
//            " t.bankCardAccount as bankCardAccount," +
//            " t.bankCardBank as bankCardBank," +
//            " t.isValidated as isValidated, " +
//            " t.centerAreaId as centerAreaId, " +
//            " lc.centerName as centerName, " +
//            " t.remark as remark, " +
//            " t.verifyStatus AS verifyStatus " +
//            " from TeacherVerify as t " +
//            " left join LearnCenter as lc on lc.centerId = t.centerAreaId " +
//            " order by t.createTime desc ")
//    @Transactional(readOnly = true)
//    Page<TeacherDto> findAllByDto(Pageable pageable);
}