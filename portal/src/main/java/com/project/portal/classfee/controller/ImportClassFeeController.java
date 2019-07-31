package com.project.portal.classfee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.service.impl.ExcelImpServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private final ExcelImpServiceImpl excelImpService;
    private final RedisTemplate redisTemplate;

    @Autowired
    public ImportClassFeeController(ExcelImpServiceImpl excelImpService, RedisTemplate redisTemplate) {
        this.excelImpService = excelImpService;
        this.redisTemplate = redisTemplate;
    }

//    @UserLoginToken
    @ApiOperation(value = "导入学生信息")
    @PostMapping(path = "/classFee")
    @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class)
    public WebResult leadingInStudents(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()){
            MyAssert.isNull(null, DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        }
        try {
            if (redisTemplate.hasKey(IMPORT_STUDENTS)){
                MyAssert.isNull(null, DefineCode.ERR0013, "有人操作，请稍后再试!");
            }
            //设置导入修改时间 防止失败没有过期时间
            redisTemplate.opsForValue().set(IMPORT_STUDENTS, DateUtil.now());
            redisTemplate.expire(IMPORT_STUDENTS, 30, TimeUnit.MINUTES);
            String type = FileUtil.extName(file.getOriginalFilename());
            if (StrUtil.isNotBlank(type) && "xlsx".equals(type)){
                excelImpService.studentsExcel07Reader(file.getInputStream(), StudentImport.class);
                return WebResult.okResult();
            }else if (StrUtil.isNotBlank(type) && "xls".equals(type)){
                excelImpService.studentsExcel03Reader(file.getInputStream(), StudentImport.class);
                return WebResult.okResult();
            }
        } catch (IOException e) {
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }
}
