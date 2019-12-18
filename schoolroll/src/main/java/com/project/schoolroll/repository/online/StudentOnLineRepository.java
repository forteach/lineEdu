package com.project.schoolroll.repository.online;


import com.project.schoolroll.domain.online.StudentOnLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentOnLineRepository extends JpaRepository<StudentOnLine, String> {

//    @Transactional(readOnly = true)
//    List<StudentOnLine> findAllByClassId(String classId);

    @Transactional(readOnly = true)
    List<StudentOnLine> findAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsAndClassId(String isValidated, String classId, Pageable pageable);

    int countAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    List<StudentOnLine> findAllByIsValidatedEqualsAndStuIDCardAndStudentNameOrderByCreateTimeDesc(String isValidated, String stuIDCard, String studentName);

    @Query(value = "select " +
            " s.studentId as studentId," +
            " s.stuId as stuId, " +
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
    List<IStudentOnLineDto> findAllByIsValidatedAndCenterAreaIdDto(String isValidated, String centerAreaId);

    @Query(value = "select " +
            " s.studentId as studentId, " +
            " s.stuId as stuId, " +
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
    List<IStudentOnLineDto> findAllIsValidatedDto(String isValidated);
}