package com.project.portal.schoolroll.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.FamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:36
 * @version: 1.0
 * @description: 学生成员信息
 */
@RestController
@Api(value = "学生家庭成员信息管理", tags = {"学生家庭成员管理"})
@RequestMapping(path = "/family", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FamilyController {


    private final FamilyService familyService;
    private FamilyController(FamilyService familyService){
        this.familyService = familyService;
    }

    @ApiOperation(value = "查询基本家庭成员信息")
    @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", required = true, paramType = "query")
    @GetMapping("/findFamilyDtoList")
    public WebResult findFamilyDtoList(@RequestBody String stuId){
        MyAssert.isNull(stuId, DefineCode.ERR0010, "学生id不为空");
        return WebResult.okResult(familyService.findFamilyDtoList(JSONObject.parseObject(stuId).getString("stuId")));
    }

    @ApiOperation(value = "查询全部有效家庭成员信息")
    @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", required = true, paramType = "query")
    @GetMapping("/findFamilies")
    public WebResult findFamilies(@RequestBody String stuId){
        MyAssert.isNull(stuId, DefineCode.ERR0010, "学生id不为空");
        return WebResult.okResult(familyService.findFamilies(JSONObject.parseObject(stuId).getString("stuId")));
    }

    @ApiOperation(value = "移除家庭成员信息")
    @ApiImplicitParam(name = "familyId", value = "家庭成员id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/removeFamilyById")
    public WebResult removeFamilyById(@RequestBody String familyId){
        MyAssert.isNull(familyId, DefineCode.ERR0010, "家庭成员id不为空");
        familyService.removeFamilyById(JSONObject.parseObject(familyId).getString("familyId"));
        return WebResult.okResult();
    }
}
