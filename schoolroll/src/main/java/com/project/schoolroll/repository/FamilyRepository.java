package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Family;
import com.project.schoolroll.repository.dto.FamilyDto;
import com.project.schoolroll.repository.dto.FamilyExportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:33
 * @version: 1.0
 * @description: 家庭成员
 */
public interface FamilyRepository extends JpaRepository<Family, String> {

    @Transactional(readOnly = true)
    public List<Family> findAllByIsValidatedEqualsAndStudentId(String isValidated, String studentId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT familyId AS familyId, studentId AS studentId, familyName AS familyName, familyPhone AS familyPhone, familyRelationship AS familyRelationship, isGuardian as isGuardian FROM Family WHERE isValidated = '0' AND studentId = ?1")
    public List<FamilyDto> findAllByStudentIdDto(String studentId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    public int deleteAllByStudentId(String studentId);

    @Query(value = "select " +
            " studentId as studentId," +
            "familyName AS familyName, " +
            "familyRelationship as familyRelationship, " +
            "isGuardian AS familyIsGuardian, " +
            "familyPhone AS familyPhone, " +
            "familyBirthDate AS familyBirthDate, " +
            "familyCardType AS familyCardType, " +
            "familyIDCard AS familyIDCard, " +
            "familyNation AS familyNation, " +
            "familyPoliticalStatus AS familyPoliticalStatus, " +
            "familyHealthCondition AS familyHealthCondition, " +
            "familyCompanyOrganization AS familyCompanyOrganization, " +
            "familyPosition AS familyPosition " +
            " from Family where isValidated = '0'")
    @Transactional(readOnly = true)
    List<FamilyExportDto> findByIsValidatedEqualsDto(String isValidated);
}
