package com.project.classfee;

import com.alibaba.fastjson.JSON;
import com.project.portal.classfee.controller.ClassStandardController;
import com.project.portal.classfee.request.ClassStandardListReq;
import com.project.portal.classfee.request.ClassStandardSaveReq;
import com.project.portal.response.WebResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import javax.annotation.Resource;

//http://www.leftso.com/blog/405.html  https://blog.csdn.net/HiBoyljw/article/details/82783443

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClassStandardTest {

    @Resource
    private ClassStandardController classStandardController;

    @Test
    public void save() {
        ClassStandardSaveReq request = new ClassStandardSaveReq();
        request.setCreateYear("2019");
        request.setStudentSum(20);
        request.setStudentSubsidies(100);
        request.setSubsidiesSum(2000);
        request.setClass_fee(100);
        request.setCenterAreaId("100001");
        System.out.println("json------" + JSON.toJSONString(request));
        WebResult r =classStandardController.saveOrUpdate(request);
        log.info("*********{}", JSON.toJSONString(r));
    }

    @Test
    public void findAll() {
        ClassStandardListReq request=new ClassStandardListReq();
        request.setCenterAreaId("100001");
        request.setCreateYear("2019");
        System.out.println("json------" + JSON.toJSONString(request));
        WebResult r =classStandardController.findAll(request);
        log.info("*********{}", JSON.toJSONString(r));
    }

}
