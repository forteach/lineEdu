//package com.project.portal.schoolroll.controller;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.StrUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.project.base.common.keyword.DefineCode;
//import com.project.base.exception.MyAssert;
//import com.project.portal.response.WebResult;
//import com.project.portal.schoolroll.request.FamilySaveUpdateRequest;
//import com.project.schoolroll.domain.Family;
//import com.project.schoolroll.repository.FamilyRepository;
//import com.project.schoolroll.service.FamilyService;
//import com.project.token.annotation.UserLoginToken;
//import com.project.token.service.TokenService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-4 09:36
// * @version: 1.0
// * @description: 学生成员信息
// */
//@RestController
//@Api(value = "学生家庭成员信息管理", tags = {"学生家庭成员管理"})
//@RequestMapping(path = "/family", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//public class FamilyController {
//
//
//    private final FamilyService familyService;
//    private final FamilyRepository familyRepository;
//    private final TokenService tokenService;
//    private FamilyController(FamilyService familyService, FamilyRepository familyRepository, TokenService tokenService){
//        this.familyService = familyService;
//        this.familyRepository = familyRepository;
//        this.tokenService = tokenService;
//    }
//
//    @UserLoginToken
//    @PostMapping("/saveOrUpdate")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "familyId", value = "家庭成员id", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "form", example = "添加不能为空"),
//            @ApiImplicitParam(name = "familyName", value = "姓名", dataType = "string", paramType = "form", example = "添加不能为空"),
//            @ApiImplicitParam(name = "familyRelationship", value = "家庭关系", dataType = "string", paramType = "form", example = "添加不能为空"),
//            @ApiImplicitParam(name = "familyPhone", value = "电话", dataType = "string", paramType = "form", example = "添加不能为空"),
//            @ApiImplicitParam(name = "isGuardian", value = "是都是监护人", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "familyCardType", value = "身份证件类型", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "familyIDCard", value = "身份证号码", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "familyBirthDate", value = "出生日期", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "healthCondition", value = "健康状态", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "companyOrganization", value = "工作单位", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "form"),
//    })
//    public WebResult saveOrUpdate(@RequestBody FamilySaveUpdateRequest request, HttpServletRequest httpServletRequest){
//        String token = httpServletRequest.getHeader("token");
//        String userId = tokenService.getUserId(token);
//        if (StrUtil.isNotBlank(request.getFamilyId())){
//            //是修改
//            familyRepository.findById(request.getFamilyId()).ifPresent(family -> {
//                BeanUtil.copyProperties(request, family);
//                family.setUpdateUser(userId);
//                familyRepository.save(family);
//            });
//        }else {
//            //添加
//            MyAssert.isNull(request.getStudentId(), DefineCode.ERR0010, "学生id不为空");
//            MyAssert.isNull(request.getFamilyRelationship(), DefineCode.ERR0010, "家庭成员关系不为空");
//            MyAssert.isNull(request.getFamilyName(), DefineCode.ERR0010, "家庭成员名称不为空");
//            MyAssert.isNull(request.getFamilyPhone(), DefineCode.ERR0010, "电话号码不为空");
//            Family family = new Family();
//            BeanUtil.copyProperties(request, family);
//            family.setFamilyId(IdUtil.fastSimpleUUID());
//            String centerAreaId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
//            family.setCenterAreaId(centerAreaId);
//            family.setCreateUser(userId);
//            familyRepository.save(family);
//        }
//        return WebResult.okResult();
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "查询基本家庭成员信息")
//    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "query")
//    @PostMapping("/findFamilyDtoList")
//    public WebResult findFamilyDtoList(@RequestBody String studentId){
//        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生id不为空");
//        return WebResult.okResult(familyService.findFamilyDtoList(JSONObject.parseObject(studentId).getString("studentId")));
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "查询全部有效家庭成员信息")
//    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "query")
//    @PostMapping("/findFamilies")
//    public WebResult findFamilies(@RequestBody String studentId){
//        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生id不为空");
//        return WebResult.okResult(familyService.findFamilies(JSONObject.parseObject(studentId).getString("studentId")));
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "移除家庭成员信息")
//    @ApiImplicitParam(name = "familyId", value = "家庭成员id", dataType = "string", required = true, paramType = "form")
//    @PostMapping("/removeFamilyById")
//    public WebResult removeFamilyById(@RequestBody String familyId){
//        MyAssert.isNull(familyId, DefineCode.ERR0010, "家庭成员id不为空");
//        familyService.removeFamilyById(JSONObject.parseObject(familyId).getString("familyId"));
//        return WebResult.okResult();
//    }
//}
