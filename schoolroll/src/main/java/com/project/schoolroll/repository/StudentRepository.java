package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Student;
import com.project.schoolroll.repository.dto.StudentExportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-28 17:06
 * @version: 1.0
 * @description:
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {


    @Transactional(readOnly = true)
    List<Student> findAllByIsValidatedEquals(String isValidated);

    @Query(value = "select " +
            "s.studentName AS studentName, " +
            "sp.gender AS gender, " +
            "sp.stuBirthDate AS stuBirthDate, " +
            "sp.stuCardType AS stuCardType, " +
            "sp.stuIDCard AS stuIDCard, " +
            "sp.namePinYin AS namePinYin, " +
            "s.className AS className, " +
            "s.studentId AS studentId, " +
            "s.studentCategory AS studentCategory, " +
            "s.learningModality AS learningModality, " +
            "s.waysEnrollment AS waysEnrollment, " +
            "s.waysStudy AS waysStudy, " +
            "sp.nationality AS nationality, " +
            "sp.nationalityType AS nationalityType, " +
            "sp.marriage AS marriage, " +
            "sp.isImmigrantChildren AS isImmigrantChildren, " +
            "sp.householdType AS householdType, " +
            "s.specialtyName AS specialtyName, " +
            "s.educationalSystem AS educationalSystem, " +
            "sp.nation AS nation, " +
            "sp.politicalStatus AS politicalStatus, " +
            "sp.stuPhone AS stuPhone, " +
            "s.entranceCertificateNumber AS entranceCertificateNumber, " +
            "s.candidateNumber AS candidateNumber, " +
            "s.totalExaminationAchievement AS totalExaminationAchievement, " +
            "s.enrollmentDate AS enrollmentDate " +
            " from Student as s left join StudentPeople as sp on sp.peopleId = s.peopleId " +
            " where s.isValidated = '0' and sp.isValidated = '0'")
    @Transactional(readOnly = true)
    public List<StudentExportDto> findByIsValidatedEqualsDto();

}
