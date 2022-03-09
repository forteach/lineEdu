package com.project.schoolroll.service.online;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TbClassServiceTest {

    @Autowired
    private TbClassService tbClassService;

    @Test
    public void getClassIdByClassName() {
        tbClassService.getClassIdByClassName("banji", "1213", "iii23", "专业", "2020").toString();
    }
}