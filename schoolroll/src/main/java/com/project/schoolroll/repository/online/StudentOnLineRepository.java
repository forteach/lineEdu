package com.project.schoolroll.repository.online;


import com.project.schoolroll.domain.online.StudentOnLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentOnLineRepository extends JpaRepository<StudentOnLine, String> {


    List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus);

    Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus, Pageable pageable);

    List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId);

    Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId, Pageable pageable);

    int countAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId, Pageable pageable);

    List<StudentOnLine> findAllByIsValidatedEqualsAndStuIDCardAndStudentNameOrderByCreateTimeDesc(String isValidated, String stuIDCard, String studentName);

    @Query(value = "select " +
            " s.studentId as studentId," +
            " s.studentName as studentName," +
            " s.gender as gender," +
            " s.stuIDCard as stuIDCard," +
            " s.stuPhone as stuPhone," +
            " s.classId as classId," +
            " s.className as className," +
            " s.enrollmentDate as enrollmentDate," +
            " s.nation as nation, " +
            " s.learningModality as learningModality, " +
            " s.importStatus as importStatus," +
            " s.centerAreaId as centerAreaId," +
            " lc.centerName as centerName, " +
            " s.createTime as createTime, " +
            " s.isValidated as isValidated " +
            " from StudentOnLine as s " +
            " left join LearnCenter as lc on lc.centerId = s.centerAreaId " +
            " where s.centerAreaId = ?1 order by s.classId, s.createTime desc ")
    @Transactional(readOnly = true)
    Page<StudentOnLineDto> findAllByCenterAreaIdDto(String centerAreaId, Pageable pageable);

    @Query(value = "select " +
            " s.studentId as studentId," +
            " s.studentName as studentName," +
            " s.gender as gender," +
            " s.stuIDCard as stuIDCard," +
            " s.stuPhone as stuPhone," +
            " s.classId as classId," +
            " s.className as className," +
            " s.enrollmentDate as enrollmentDate," +
            " s.nation as nation, " +
            " s.learningModality as learningModality, " +
            " s.importStatus as importStatus, " +
            " s.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName," +
            " s.createTime as createTime, " +
            " s.isValidated as isValidated " +
            " from StudentOnLine as s " +
            " left join LearnCenter as lc on lc.centerId = s.centerAreaId " +
            " order by s.classId, s.createTime desc ")
    @Transactional(readOnly = true)
    Page<StudentOnLineDto> findAllDto(Pageable pageable);

    @Query(value = "select " +
            " s.studentId as studentId," +
            " s.studentName as studentName," +
            " s.gender as gender," +
            " s.stuIDCard as stuIDCard," +
            " s.stuPhone as stuPhone," +
            " s.classId as classId," +
            " s.className as className," +
            " s.enrollmentDate as enrollmentDate," +
            " s.nation as nation, " +
            " s.learningModality as learningModality, " +
            " s.importStatus as importStatus," +
            " s.centerAreaId as centerAreaId," +
            " lc.centerName as centerName, " +
            " s.createTime as createTime, " +
            " s.isValidated as isValidated " +
            " from StudentOnLine as s " +
            " left join LearnCenter as lc on lc.centerId = s.centerAreaId " +
            " where s.isValidated = ?1 and s.centerAreaId = ?2 order by s.classId, s.createTime desc ")
    @Transactional(readOnly = true)
    List<StudentOnLineDto> findAllByIsValidatedAndCenterAreaIdDto(String isValidated, String centerAreaId);

    @Query(value = "select " +
            " s.studentId as studentId," +
            " s.studentName as studentName," +
            " s.gender as gender," +
            " s.stuIDCard as stuIDCard," +
            " s.stuPhone as stuPhone," +
            " s.classId as classId," +
            " s.className as className," +
            " s.enrollmentDate as enrollmentDate," +
            " s.nation as nation, " +
            " s.learningModality as learningModality, " +
            " s.importStatus as importStatus, " +
            " s.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName," +
            " s.createTime as createTime, " +
            " s.isValidated as isValidated " +
            " from StudentOnLine as s " +
            " left join LearnCenter as lc on lc.centerId = s.centerAreaId " +
            " where s.isValidated = ?1 order by s.classId, s.createTime desc ")
    @Transactional(readOnly = true)
    List<StudentOnLineDto> findAllIsValidatedDto(String isValidated);
}