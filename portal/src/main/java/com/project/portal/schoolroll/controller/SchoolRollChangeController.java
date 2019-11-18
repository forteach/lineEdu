//package com.project.portal.schoolroll.controller;
//
//import cn.hutool.json.JSONUtil;
//import com.project.base.common.keyword.DefineCode;
//import com.project.base.exception.MyAssert;
//import com.project.portal.response.WebResult;
//import com.project.portal.schoolroll.request.SchoolRollFindRequest;
//import com.project.schoolroll.service.SchoolRollChangeService;
//import com.project.token.annotation.UserLoginToken;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-12 12:22
// * @version: 1.0
// * @description:
// */
//@RestController
//@Api(value = "学籍异动管理", tags = {"学籍移动管理"})
//@RequestMapping(path = "/schoolRollChange", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//public class SchoolRollChangeController {
//
//    private final SchoolRollChangeService schoolRollChangeService;
//
//    public SchoolRollChangeController(SchoolRollChangeService schoolRollChangeService) {
//        this.schoolRollChangeService = schoolRollChangeService;
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "查询学籍异动信息")
//    @PostMapping(path = "/findSchoolRollChange")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "changId", value = "异动编号", dataType = "string"),
//            @ApiImplicitParam(name = "studentId", value = "学生编号", dataType = "string"),
//            @ApiImplicitParam(name = "typeName", value = "异动类型", dataType = "string")
//    })
//    // todo 需要审批流程向后推
//    public WebResult findSchoolRollChange(@RequestBody SchoolRollFindRequest request){
//        return WebResult.okResult();
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "学生中心申请学生退学")
//    @PostMapping(path = "/applyChangeStudent")
//    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "form")
//    public WebResult applyChangeStudent(@RequestBody String studentId){
//        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生Id不为空");
//        schoolRollChangeService.applyChangeStudent(JSONUtil.parseObj(studentId).getStr("studentId"));
//        return WebResult.okResult();
//    }
//}
