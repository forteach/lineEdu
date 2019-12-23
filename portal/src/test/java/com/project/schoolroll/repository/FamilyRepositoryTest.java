//package com.project.schoolroll.repository;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
//import static org.junit.Assert.*;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-8-6 15:29
// * @version: 1.0
// * @description:
// */
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class FamilyRepositoryTest {
//    @Autowired
//    private FamilyRepository familyRepository;
//    @Test
//    public void findByIsValidatedEquals() {
//        familyRepository.findByIsValidatedEqualsDto(TAKE_EFFECT_OPEN)
//                .parallelStream()
//                .forEach(f -> {
//                    System.out.println(f.getFamilyIDCard());
//                    System.out.println(f.getFamilyBirthDate());
//                    System.out.println(f.getFamilyCardType());
//                    System.out.println(f.getFamilyCompanyOrganization());
//                    System.out.println(f.getFamilyHealthCondition());
//                    System.out.println(f.getFamilyName());
//                    System.out.println(f.getFamilyNation());
//                    System.out.println(f.getFamilyPhone());
//                    System.out.println(f.getFamilyIsGuardian());
//                    System.out.println(f.getFamilyPoliticalStatus());
//                    System.out.println(f.getFamilyPosition());
//                    System.out.println(f.getFamilyRelationship());
//                });
//    }
//}