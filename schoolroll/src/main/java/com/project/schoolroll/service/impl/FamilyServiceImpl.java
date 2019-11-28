package com.project.schoolroll.service.impl;

import com.project.schoolroll.domain.Family;
import com.project.schoolroll.repository.dto.FamilyDto;
import com.project.schoolroll.repository.FamilyRepository;
import com.project.schoolroll.service.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:56
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public List<FamilyDto> findFamilyDtoList(String studentId) {
        return familyRepository.findAllByStudentIdDto(studentId);
    }

    @Override
    public List<Family> findFamilies(String studentId) {
        return familyRepository.findAllByIsValidatedEqualsAndStudentId(TAKE_EFFECT_OPEN, studentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFamilyById(String familyId) {
        familyRepository.findById(familyId)
                .ifPresent(family -> {
                    family.setIsValidated(TAKE_EFFECT_CLOSE);
                    familyRepository.save(family);
                });
    }
}
