package com.project.portal.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.project.schoolroll.domain.StudentExpandDictionary;
import com.project.schoolroll.repository.StudentExpandDictionaryRepository;
import com.project.schoolroll.service.StudentExpandDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.schoolroll.domain.excel.StudentEnum.*;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-30 16:42
 * @version: 1.0
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentExpandDictionaryServiceImplTest {
    @Resource
    private StudentExpandDictionaryService studentExpandDictionaryService;
    @Resource
    private StudentExpandDictionaryRepository studentExpandDictionaryRepository;

    @Test
    public void findDto() {
        studentExpandDictionaryRepository.findByIsValidatedEquals(TAKE_EFFECT_OPEN)
                .stream().map(s -> {
            System.out.println("dicName : " + s.getDicName());
            System.out.println("explain : " + s.getDicExplain());
            return new ArrayList();
        }).collect(toList());
    }

    @Test
    public void findAll() {
    }
    @Test
    public void save(){
        List<StudentExpandDictionary> list = CollUtil.newArrayList(
                StudentExpandDictionary.builder().dicName(trainSpace.name()).dicExplain(trainSpace.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentFormAdministrativeCode.name()).dicExplain(studentFormAdministrativeCode.getName()).build(),
                StudentExpandDictionary.builder().dicName(birthplaceAdministrativeCode.name()).dicExplain(birthplaceAdministrativeCode.getName()).build(),
                StudentExpandDictionary.builder().dicName(familyAddress.name()).dicExplain(familyAddress.getName()).build(),
                StudentExpandDictionary.builder().dicName(familyPostalCode.name()).dicExplain(familyPostalCode.getName()).build(),
                StudentExpandDictionary.builder().dicName(familyPhone.name()).dicExplain(familyPhone.getName()).build(),
                StudentExpandDictionary.builder().dicName(householdAdministrativeCode.name()).dicExplain(householdAdministrativeCode.getName()).build(),
                StudentExpandDictionary.builder().dicName(householdAddressDetails.name()).dicExplain(householdAddressDetails.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentHabitationType.getName()).dicExplain(studentHabitationType.getName()).dicName(localPoliceStation.name()).dicExplain(localPoliceStation.getName()).build(),
                StudentExpandDictionary.builder().dicName(healthCondition.name()).dicExplain(healthCondition.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentSource.name()).dicExplain(studentSource.getName()).build(),
                StudentExpandDictionary.builder().dicName(recruit.name()).dicExplain(recruit.getName()).build(),
                StudentExpandDictionary.builder().dicName(school.name()).dicExplain(school.getName()).build(),
                StudentExpandDictionary.builder().dicName(isDestituteFamily.name()).dicExplain(isDestituteFamily.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentRecruitingWays.name()).dicExplain(studentRecruitingWays.getName()).build(),
                StudentExpandDictionary.builder().dicName(jointRecruitmentCooperationType.name()).dicExplain(jointRecruitmentCooperationType.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentSpeciality.name()).dicExplain(studentSpeciality.getName()).build(),
                StudentExpandDictionary.builder().dicName(studentMedicalHistory.name()).dicExplain(studentMedicalHistory.getName()).build(),
                StudentExpandDictionary.builder().dicName(healthReport.name()).dicExplain(healthReport.getName()).build(),
                StudentExpandDictionary.builder().dicName(jointRecruitmentCooperationStyle.name()).dicExplain(jointRecruitmentCooperationStyle.getName()).build(),
                StudentExpandDictionary.builder().dicName(jointRecruitmentCooperationSchoolCode.name()).dicExplain(jointRecruitmentCooperationSchoolCode.getName()).build(),
                StudentExpandDictionary.builder().dicName(offCampusTeachingAddress.name()).dicExplain(offCampusTeachingAddress.getName()).build(),
                StudentExpandDictionary.builder().dicName(stageByStageEducationType.name()).dicExplain(stageByStageEducationType.getName()).build(),
                StudentExpandDictionary.builder().dicName(englishName.name()).dicExplain(englishName.getName()).build(),
                StudentExpandDictionary.builder().dicName(stuEmail.name()).dicExplain(stuEmail.getName()).build()
        );
        studentExpandDictionaryRepository.saveAll(list);
    }
}