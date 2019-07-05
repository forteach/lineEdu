package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.FindStudentDtoPageAllRequest;
import com.project.portal.schoolroll.request.StudentSaveUpdateRequest;
import com.project.schoolroll.service.StudentService;
import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-3 14:26
 * @version: 1.0
 * @description: 学生信息
 */
@RestController
@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "管理学生", tags = {"管理学生信息"})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "保存修改学生信息")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    public WebResult saveUpdate(@RequestBody StudentSaveUpdateRequest request){
        return WebResult.okResult();
    }

    @ApiOperation(value = "查询学生信息列表")
    @PostMapping(path = "/findStudentsPageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stuName", value = "学生名字", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "studentCategory", value = "学习类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyId", value = "专业简称(名称)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "educationalSystem", value = "学制", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysStudy", value = "就读方式/学习方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "learningModality", value = "学习形式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysEnrollment", value = "入学方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateStartDate", value = "开始入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateEndDate", value = "结束入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "年级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortVo", value = "分页排序字段", dataTypeClass = SortVo.class, required = true, paramType = "query")
    })
    public WebResult findStudentsPageAll(@RequestBody FindStudentDtoPageAllRequest request){
        MyAssert.blank(String.valueOf(request.getSortVo().getPage()), DefineCode.ERR0010, "页码参数不为空");
        MyAssert.blank(String.valueOf(request.getSortVo().getSize()), DefineCode.ERR0010, "每页条数不为空");
        MyAssert.gt(request.getSortVo().getPage(), 0, DefineCode.ERR0010,"页码参数不正确");
        MyAssert.gt(request.getSortVo().getSize(), 0, DefineCode.ERR0010,"每页显示条数不正确");
        FindStudentDtoPageAllVo vo = new FindStudentDtoPageAllVo();
        BeanUtil.copyProperties(request, vo);
        vo.setPageable(PageRequest.of(request.getSortVo().getPage(), request.getSortVo().getSize()));
        return WebResult.okResult(studentService.findStudentsPageAll(vo));
    }
}
