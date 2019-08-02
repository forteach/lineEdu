package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.repository.dto.StudentPeopleDto;
import com.project.schoolroll.repository.dto.StuentWeChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:08
 * @version: 1.0
 * @description:
 */
public interface StudentPeopleRepository extends JpaRepository<StudentPeople, String> {

    @Query(value = "select " +
            " s.stuId as stuId, s.stuName as stuName, s.specialtyName as specialtyName, s.centerId as centerId, " +
            " lc.centerName as centerName, s.studentCategory as studentCategory, " +
            " s.classId as classId, s.className as className, s.educationalSystem as educationalSystem, s.waysStudy as waysStudy, " +
            " s.learningModality as learningModality, s.waysEnrollment as waysEnrollment, s.enrollmentDate as enrollmentDate, " +
            " s.grade as grade, s.entranceCertificateNumber as entranceCertificateNumber, s.candidateNumber as candidateNumber, " +
            " s.totalExaminationAchievement as totalExaminationAchievement, sp.namePinYin as namePinYin, sp.gender as gender, " +
            " sp.stuCardType as stuCardType, sp.stuIDCard as stuIDCard, sp.stuPhone as stuPhone, sp.stuBirthDate as stuBirthDate, " +
            " sp.nationality as nationality, sp.nation as nation, sp.politicalStatus as politicalStatus, sp.householdType as householdType, " +
            " sp.isImmigrantChildren as isImmigrantChildren, sp.remark as remark, sp.examinationArea as examinationArea " +
//            " sp.school as school " +
            " from Student as s left join StudentPeople as sp on sp.peopleId = s.peopleId" +
            " left join LearnCenter as lc on lc.centerId = s.centerId " +
            " where s.isValidated = '0' and sp.isValidated = '0'" +
            " and lc.isValidated = '0' and s.stuId = ?1")
    @Transactional(readOnly = true)
    StudentPeopleDto findByStuId(String stuId);

    @Query(value = "select " +
            " s.stuId as stuId, s.stuName as stuName, s.classId as classId , s.className as className, " +
            " sp.stuIDCard as stuIDCard " +
            " from Student as s " +
            " left join StudentPeople as sp on sp.peopleId = s.peopleId " +
            " where s.isValidated = '0' and sp.isValidated = '0' " +
            " and sp.stuName = ?1 and sp.stuIDCard = ?2")
    List<StuentWeChatDto> findWeChatUserByStuNameAndStuIDCard(String stuName, String stuIDCard);
}
