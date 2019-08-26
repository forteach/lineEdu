package com.project.train.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-26 16:58
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TrainClassStuServiceTest {

    @Autowired
    private TrainClassStuService trainClassStuService;

    @Test
    public void findAllPage() {
        trainClassStuService.findAllPage("1f184d63f76644e3bb0889d7e43d9309", "", Integer.parseInt(""), PageRequest.of(0, 2))
                .getContent().stream().forEach(System.out::println);
    }
}