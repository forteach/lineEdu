package com.project.schoolroll.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-6 16:38
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void findByIsValidatedEqualsDto() {
        studentRepository.findByIsValidatedEqualsDto()
                .parallelStream()
                .forEach(d -> {
                    System.out.println(d.getStuIDCard());
                    System.out.println(d.getStudentId());
                    System.out.println(d.getStudentName());
                    System.out.println(d.getCandidateNumber());
                    System.out.println(d.getEducationalSystem());
                    System.out.println(d.getWaysStudy());
                });
    }
}