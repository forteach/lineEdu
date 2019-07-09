package com.project.portal.schoolroll.controller;

import com.project.schoolroll.service.LeadingInService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 08:19
 * @Version: 1.0
 * @Description: 导入相关数据 leading-in
 */
@RestController
@Api(value = "导入接口", tags = {"导入相关数据接口"})
@RequestMapping(path = "/leadingIn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LeadingInController {
    private final LeadingInService leadingInService;

    @Autowired
    public LeadingInController(LeadingInService leadingInService) {
        this.leadingInService = leadingInService;
    }
}
