package com.project.portal.export.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import com.project.portal.response.WebResult;
import com.project.portal.util.MyExcleUtil;
import com.project.token.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 08:25
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@RestController
@Api(value = "导出接口", tags = {"导出相关数据接口"})
@RequestMapping(path = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExportController {

    @UserLoginToken
    @ApiOperation(value = "导出学生需要信息模版")
    @GetMapping(path = "/exportStudentTemplate")
    public WebResult leadingOutStudentTemplate(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String fileName = "导入学生数据模版.xlsx";
        InputStream inputStream = new ClassPathResource("template/导入学生数据模版.xlsx").getStream();
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型为下载类型
        MyExcleUtil.setResponse(response, request, fileName);
        //用 common-io 工具 将输入流拷贝到输出流
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        return WebResult.okResult();
    }
}