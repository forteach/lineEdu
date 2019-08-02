package com.project.portal.importexport.controller;


import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.service.ClassFeeInfoService;
import com.project.portal.response.WebResult;
import com.project.redis.service.ObjectRedisService;
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.service.impl.ExcelImpServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.project.schoolroll.domain.excel.Dic.IMPORT_CLASS_FREE;
import static com.project.schoolroll.domain.excel.Dic.IMPORT_STUDENTS;

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


    @Autowired
    public ImportClassFeeController(ClassFeeInfoService classFeeInfoService) {
        this.classFeeInfoService = classFeeInfoService;
    }

    //    @UserLoginToken
    @ApiOperation(value = "导入课时费信息")
    @PostMapping(path = "/classFee")
    @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class)
    public WebResult leadingInStudents(@RequestParam("file") MultipartFile file) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        //TODO 需要从公共的REDIS信息里面取值
        String centerId="01";
        try {
            List<ClassFeeInfo> list= classFeeInfoService.excelReader(file.getInputStream(), ClassFeeInfo.class);

            //没有可导入的数据，删除键值，返回异常信息
            if(list.size()==0){
                classFeeInfoService.delExcelKey();
                MyAssert.isTrue(true, DefineCode.ERR0010, "没有可导入的数据");
            }
            //文件信息的数据库添加操作。
            classFeeInfoService.impFile(list,list.get(0).getCreateYear(),list.get(0).getCreateMonth(),centerId) ;
        return WebResult.okResult();
        } catch (IOException e) {
            //导入错误，删除REDIS键值
            classFeeInfoService.delExcelKey();
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的Excel文件数据错误");
    }
}
