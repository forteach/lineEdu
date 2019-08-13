package com.project.schoolroll.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static org.junit.Assert.*;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-6 15:09
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentExpandRepositoryTest {

    @Autowired
    private StudentExpandRepository studentExpandRepository;
    @Test
    public void findByIsValidatedEquals() {
        studentExpandRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN).parallelStream()
//                .forEach(System.out::println);
                .forEach(d -> {
                    System.out.println(d.getExpandName()+"\r\n");
                    System.out.println(d.getExpandValue()+"\r\n");
                });
    }
}