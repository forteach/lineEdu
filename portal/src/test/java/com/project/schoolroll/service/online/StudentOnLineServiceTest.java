package com.project.schoolroll.service.online;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentOnLineServiceTest {

    @Autowired
    private StudentOnLineService studentOnLineService;

    @Test
    public void importStudent() {
        studentOnLineService.importStudent(FileUtil.getInputStream(""), "2323", "AAAA");
    }
}