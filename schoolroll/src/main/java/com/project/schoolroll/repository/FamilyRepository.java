package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Family;
import com.project.schoolroll.repository.dto.FamilyDto;
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
    public List<Family> findAllByIsValidatedEqualsAndStuId(String isValidated, String stuId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT familyId AS familyId, stuId AS stuId, name AS name, phone AS phone, familyRelationship AS familyRelationship, isGuardian as isGuardian FROM Family WHERE isValidated = '0' AND stuId = ?1")
    public List<FamilyDto> findAllByStuIdDto(String stuId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    public int deleteAllByStuId(String stuId);
}
