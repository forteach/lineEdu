package com.project.portal.classfee.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.service.ClassFeeInfoService;
import com.project.portal.response.WebResult;
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.service.impl.ExcelImpServiceImpl;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 08:19
 * @Version: 1.0
 * @Description: 导入相关数据 leading-in
 */
@RestController
@Slf4j
@Api(value = "导入接口", tags = {"导入相关数据接口"})
@RequestMapping(path = "/import", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImportClassFeeController {
    private final ClassFeeInfoService classFeeInfoService;
    private final ExcelImpServiceImpl excelImpService;
    private final TokenService tokenService;


    @Autowired
    public ImportClassFeeController(ClassFeeInfoService classFeeInfoService, ExcelImpServiceImpl excelImpService, TokenService tokenService) {
        this.classFeeInfoService = classFeeInfoService;
        this.excelImpService = excelImpService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "导入课时费信息")
    @PostMapping(path = "/classFee/{token}")
    @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class)
    public WebResult leadingInStudents(@RequestParam("file") MultipartFile file, @PathVariable String token) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        String centerAreaId = tokenService.getCenterAreaId(token);
        String creteUser = tokenService.getUserId(token);
        try {
            List<ClassFeeInfo> list = classFeeInfoService.excelReader(file.getInputStream(), ClassFeeInfo.class);

            //没有可导入的数据，删除键值，返回异常信息
            if (list.size() == 0) {
                classFeeInfoService.delExcelKey();
                MyAssert.isTrue(true, DefineCode.ERR0010, "没有可导入的数据");
            }
            //文件信息的数据库添加操作。
            classFeeInfoService.impFile(list, list.get(0).getCreateYear(), list.get(0).getCreateMonth(), centerAreaId, creteUser);
            return WebResult.okResult();
        } catch (IOException e) {
            //导入错误，删除REDIS键值
            classFeeInfoService.delExcelKey();
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的Excel文件数据错误");
    }

    @UserLoginToken
    @ApiOperation(value = "导入学生信息数据")
    @PostMapping(path = "/students/{token}")
    @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class)
    public WebResult inportStudents(@RequestParam("file") MultipartFile file, @PathVariable String token) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        try {

            excelImpService.checkoutKey();
            //设置导入修改时间 防止失败没有过期时间
            String type = FileUtil.extName(file.getOriginalFilename());
            //todo 需要获取上传的学生中心id数据
            String centerAreaId = tokenService.getCenterAreaId(token);
            String userId = tokenService.getUserId(token);
            if (StrUtil.isNotBlank(type) && "xlsx".equals(type)) {
                excelImpService.setStudentKey();
                excelImpService.studentsExcel07Reader(file.getInputStream(), StudentImport.class, centerAreaId, userId);
                return WebResult.okResult();
            } else if (StrUtil.isNotBlank(type) && "xls".equals(type)) {
                excelImpService.setStudentKey();
                excelImpService.studentsExcel03Reader(file.getInputStream(), StudentImport.class, centerAreaId, userId);
                return WebResult.okResult();
            }
        } catch (IOException e) {
            excelImpService.deleteKey();
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }
}