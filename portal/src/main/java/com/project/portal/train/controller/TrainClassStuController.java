package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FindTrainClassStuAllPageRequest;
import com.project.portal.train.request.TrainClassStuPageRequest;
import com.project.portal.train.request.TrainClassStuSaveUpdateRequest;
import com.project.portal.util.MyExcleUtil;
import com.project.token.annotation.PassToken;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.domain.TrainClassStu;
import com.project.train.service.TrainClassStuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:43
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@Api(value = "培训项目班级学生管理", tags = {"培训项目班级学生管理"})
@RequestMapping(path = "/trainClassStu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainClassStuController {
    private final TrainClassStuService trainClassStuService;
    private final TokenService tokenService;

    public TrainClassStuController(TrainClassStuService trainClassStuService, TokenService tokenService) {
        this.trainClassStuService = trainClassStuService;
        this.tokenService = tokenService;
    }
    @UserLoginToken
    @ApiOperation(value = "保存培训项目修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainStuId", value = "培训班级学生编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassName", value = "培训班级名称", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "系统用户编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainProjectName", value = "培训项目名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuName", value = "姓名", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nation", value = "民族", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "jobTitle", value = "单位职务", dataType = "string"),
            @ApiImplicitParam(name = "stuIdCard", value = "身份证号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuPhone", value = "联系方式", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainClassStuSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        TrainClassStu trainClassStu = new TrainClassStu();
        BeanUtil.copyProperties(request, trainClassStu);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        trainClassStu.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getTrainStuId())) {
            String centerAreaId = tokenService.getCenterAreaId(token);
            trainClassStu.setCreateUser(userId);
            trainClassStu.setCenterAreaId(centerAreaId);
            return WebResult.okResult(trainClassStuService.save(trainClassStu));
        } else {
            return WebResult.okResult(trainClassStuService.update(trainClassStu));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "项目计划课程列表")
    @PostMapping(path = "/findByClassIdAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody TrainClassStuPageRequest request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级id不为空");
        return WebResult.okResult(trainClassStuService.findClassPage(request.getClassId(), PageRequest.of(request.getPage(), request.getSize())));
    }

//    @ApiOperation(value = "培训项目班级学生列表")
//    @PostMapping(path = "/findById")
//    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
//    public WebResult findById(@RequestBody String planId) {
//        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
//        return WebResult.okResult(trainClassStuService.findId(JSONObject.parseObject(planId).getString("planId")));
//    }

    @UserLoginToken
    @ApiOperation(value = "根据计划id分页查询班级学生")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15"),
            @ApiImplicitParam(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "query")
    })
    @PostMapping(path = "/findAllPageAll")
    public WebResult findByPjPlanIdPageAll(@RequestBody FindTrainClassStuAllPageRequest request){
        valideSort(request.getPage(), request.getPage());
        if (StrUtil.isNotBlank(request.getAgoDay()) && StrUtil.isNotBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findByCenterAreaIdAndAgoDayAll(request.getCenterAreaId(), request.getPjPlanId(), Integer.parseInt(request.getAgoDay()), PageRequest.of(request.getPage(), request.getSize())));
        }else if (StrUtil.isBlank(request.getAgoDay()) && StrUtil.isNotBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findByPlanIdPageAll(request.getPjPlanId(), PageRequest.of(request.getPage(), request.getSize())));
        }else if (StrUtil.isNotBlank(request.getAgoDay()) && StrUtil.isBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findAgoDay(Integer.parseInt(request.getAgoDay()), PageRequest.of(request.getPage(), request.getSize())));
        }else {
            return WebResult.okResult(trainClassStuService.findAllPage(PageRequest.of(request.getPage(), request.getSize())));
        }
    }

    @PassToken
    @ApiOperation(value = "导入培训的学生信息数据")
    @PostMapping(path = "/saveImport")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form")
    })
    public WebResult saveImportStu(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest){
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        String token = httpServletRequest.getParameter("token");
        MyAssert.isTrue(StrUtil.isBlank(token), DefineCode.ERR0004, "token is null");
        MyAssert.isFalse(tokenService.checkToken(token), DefineCode.ERR0010, "401");
        try {
            trainClassStuService.checkoutKey();
            //设置导入修改时间 防止失败没有过期时间
            String type = FileUtil.extName(file.getOriginalFilename());
            if (StrUtil.isNotBlank(type) && "xlsx".equals(type) || "xls".equals(type)) {
                String centerAreaId = tokenService.getCenterAreaId(token);
                String userId = tokenService.getUserId(token);
                String pjPlanId = httpServletRequest.getParameter("pjPlanId");
                String trainClassId = httpServletRequest.getParameter("trainClassId");
                MyAssert.isTrue(StrUtil.isBlank(trainClassId), DefineCode.ERR0010, "班级id不能是空");
                MyAssert.isTrue(StrUtil.isBlank(pjPlanId), DefineCode.ERR0010, "计划id不能是空");
                int size = trainClassStuService.saveImportAll(trainClassId, pjPlanId, centerAreaId, userId, file.getInputStream());
                Map<String, Object> map = new HashMap<>(2);
                map.put("size", size);
                return WebResult.okResult(map);
            }
        } catch (IOException e) {
            trainClassStuService.deleteKey();
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }

    @PassToken
    @GetMapping(path = "/exportTrainStudentTemplate")
    @ApiOperation(value = "学员导入模板")
    public WebResult exportTemplate(HttpServletRequest request, HttpServletResponse response) {
        //设置导入数据模板
        List<List<String>> lists = CollUtil.toList(CollUtil.newArrayList("姓名", "性别", "联系方式", "民族", "身份证号码", "单位职务"));
        MyExcleUtil.getExcel(response, request, lists, "学员导入模板.xlsx");
        return WebResult.okResult();
    }
}