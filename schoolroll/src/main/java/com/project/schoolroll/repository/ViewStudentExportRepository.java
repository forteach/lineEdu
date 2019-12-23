//package com.project.schoolroll.repository;

//import com.project.schoolroll.domain.excel.ViewStudentExport;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Map;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-7 15:12
 * @version: 1.0
 * @description:
 */
//public interface ViewStudentExportRepository extends JpaRepository<ViewStudentExport, String> {

//    @Query(value = "select " +
//            "    s.studentName as studentName," +
//            "    sp.gender as gender, " +
//            "    sp.stuBirthDate as stuBirthDate, " +
//            "    sp.stuCardType as stuCardType, " +
//            "    sp.stuIDCard as stuIDCard, " +
//            "    sp.namePinYin as namePinYin, " +
//            "    s.className as className, " +
//            "    s.studentId as studentId, " +
//            "    s.studentCategory as studentCategory, " +
//            "    s.learningModality as learningModality, " +
//            "    s.waysEnrollment as waysEnrollment, " +
//            "    s.waysStudy as waysStudy, " +
//            "    sp.nationality as nationality, " +
//            "    sp.nationalityType as nationalityType, " +
//            "    sp.marriage as marriage, " +
//            "    sp.isImmigrantChildren as isImmigrantChildren, " +
//            "    sp.householdType as householdType, " +
//            "    s.specialtyName as specialtyName," +
//            "    s.educationalSystem as educationalSystem, " +
//            "    sp.nation as nation, " +
//            "    sp.politicalStatus as politicalStatus, " +
//            "    sp.stuPhone as stuPhone, " +
//            "    s.entranceCertificateNumber as entranceCertificateNumber, " +
//            "    s.candidateNumber as candidateNumber, " +
//            "    s.totalExaminationAchievement as totalExaminationAchievement, " +
//            "    s.enrollmentDate as enrollmentDate " +

//            " s.studentId AS studentId, " +
//            " s.specialtyId AS specialtyId, " +
//            " s.studentName AS studentName, " +
//            " s.centerId AS centerId, " +
//            " s.studentCategory AS studentCategory, " +
//            " s.classId AS classId, " +
//            " s.className AS className, " +
//            " s.specialtyName AS specialtyName, " +
//            " s.educationalSystem AS educationalSystem, " +
//            " s.waysStudy AS waysStudy, " +
//            " s.learningModality AS learningModality, " +
//            " s.waysEnrollment AS waysEnrollment, " +
//            " s.enrollmentDate AS enrollmentDate, " +
//            " s.grade AS grade, " +
//            " s.entranceCertificateNumber AS entranceCertificateNumber, " +
//            " s.candidateNumber AS candidateNumber, " +
//            " s.totalExaminationAchievement AS totalExaminationAchievement, " +
//            " sp.namePinYin AS namePinYin, " +
//            " sp.gender AS gender, " +
//            " sp.stuCardType AS stuCardType, " +
//            " sp.stuIDCard AS stuIDCard, " +
//            " sp.stuPhone AS stuPhone, " +
//            " sp.stuBirthDate AS stuBirthDate, " +
//            " sp.nationality AS nationality, " +
//            " sp.nationalityType AS nationalityType, " +
//            " sp.marriage AS marriage, " +
//            " sp.nation AS nation, " +
//            " sp.politicalStatus AS politicalStatus, " +
//            " sp.householdType AS householdType, " +
//            " sp.isImmigrantChildren AS isImmigrantChildren, " +
//            " sp.examinationArea AS examinationArea, " +
//            " sp.arrivalDate AS arrivalDate, " +
//            " sp.recruitBatch AS recruitBatch " +


//            " from Student as s left join StudentPeople as sp on sp.peopleId = s.peopleId " +
//            " where s.isValidated = ?1 and sp.isValidated = ?1")
//    public List<Map<String,String>> findAllByIsValidatedEqualsDto(String isValidated);
//}