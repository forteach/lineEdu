package com.project.schoolroll.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-6 16:14
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentPeopleRepositoryTest {

    @Autowired
    private StudentPeopleRepository studentPeopleRepository;

    @Test
    public void findByIsValidatedEquals() {
        studentPeopleRepository.findByIsValidatedEquals(TAKE_EFFECT_OPEN)
                .parallelStream()
                .forEach(
                        System.out::println
                );
    }
}