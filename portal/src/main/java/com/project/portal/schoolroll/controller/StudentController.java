package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.StudentDtoFindPageAllRequest;
import com.project.portal.schoolroll.request.StudentExpandRequest;
import com.project.portal.schoolroll.request.StudentSaveUpdateRequest;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.service.StudentExpandService;
import com.project.schoolroll.service.StudentService;
import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
import com.project.schoolroll.web.vo.StudentExpandVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    private final StudentExpandService studentExpandService;

    public StudentController(StudentService studentService, StudentExpandService studentExpandService) {
        this.studentService = studentService;
        this.studentExpandService = studentExpandService;
    }

    @ApiOperation(value = "保存修改学生信息")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", paramType = "form", example = "有值为修改，否则为新增"),
            @ApiImplicitParam(name = "stuName", value = "学生名字", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "namePinYin", value = "姓名拼音", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "cardType", value = "身份证类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "IDCard", value = "身份证号码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "birthDate", value = "出生日期(年月)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nationality", value = "国籍/地区", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nationalityType", value = "港澳台侨外", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "marriage", value = "婚姻状态", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nation", value = "民族", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "householdType", value = "户口性质/类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "isImmigrantChildren", value = "是否随迁子女", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerId", value = "学习中心Id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "studentCategory", value = "学习类别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "班级Id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "className", value = "班级名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "specialtyName", value = "专业简称(名称)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "educationalSystem", value = "学制", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "waysStudy", value = "就读方式/学习方式", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "learningModality", value = "学习形式", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "waysEnrollment", value = "入学方式", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "entranceCertificateNumber", value = "准考证号码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "candidateNumber", value = "考生号码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "totalExaminationAchievement", value = "考试总成绩", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "enrollmentDate", value = "入学时间(年/月)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "form"),
    })
    public WebResult saveUpdate(@RequestBody StudentSaveUpdateRequest request) {
        Student student = new Student();
        UpdateUtil.copyNullProperties(request, student);
        StudentPeople studentPeople = new StudentPeople();
        UpdateUtil.copyNullProperties(request, studentPeople);
        studentService.saveOrUpdate(student, studentPeople);
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
            @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "educationalSystem", value = "学制", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysStudy", value = "就读方式/学习方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "learningModality", value = "学习形式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysEnrollment", value = "入学方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateStartDate", value = "开始入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateEndDate", value = "结束入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "年级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortVo", value = "分页排序字段", dataTypeClass = SortVo.class, required = true, paramType = "query")
    })
    public WebResult findStudentsPageAll(@RequestBody StudentDtoFindPageAllRequest request) {
        MyAssert.blank(String.valueOf(request.getSortVo().getPage()), DefineCode.ERR0010, "页码参数不为空");
        MyAssert.blank(String.valueOf(request.getSortVo().getSize()), DefineCode.ERR0010, "每页条数不为空");
        MyAssert.gt(request.getSortVo().getPage(), 0, DefineCode.ERR0010, "页码参数不正确");
        MyAssert.gt(request.getSortVo().getSize(), 0, DefineCode.ERR0010, "每页显示条数不正确");
        FindStudentDtoPageAllVo vo = new FindStudentDtoPageAllVo();
        BeanUtil.copyProperties(request, vo);
        vo.setPageable(PageRequest.of(request.getSortVo().getPage(), request.getSortVo().getSize()));
        return WebResult.okResult(studentService.findStudentsPageAll(vo));
    }

    @ApiOperation(value = "删除学生信息")
    @PostMapping(path = "/deleteStudentByStuId")
    @ApiImplicitParam(name = "stuId", value = "学生id", required = true, dataType = "string", paramType = "form")
    public WebResult deleteStudentByStuId(@RequestBody String stuId){
        MyAssert.isNull(stuId, DefineCode.ERR0010, "删除学生信息");
        studentService.deleteById(JSONObject.parseObject(stuId).getString("stuId"));
        return WebResult.okResult();
    }

    @ApiOperation(value = "查询扩展学生信息")
    @GetMapping(path = "/findStudentExpandInfo")
    @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", required = true, paramType = "form")
    public WebResult findStudentExpandInfo(@RequestBody String stuId){
        MyAssert.isNull(stuId, DefineCode.ERR0010, "学生Id不能为空");
        return WebResult.okResult(studentExpandService.findStudentExpandInfo(JSONObject.parseObject(stuId).getString("stuId")));
    }

    @ApiOperation(value = "删除学生对应的扩展信息")
    @ApiImplicitParam(name = "stuId", value = "学生id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/deleteAllStudentExpandByStuId")
    public WebResult deleteAllStudentExpandByStuId(@RequestBody String stuId){
        MyAssert.isNull(stuId, DefineCode.ERR0010, "学生Id不能为空");
        return WebResult.okResult(studentExpandService.deleteAllStudentExpandByStuId(JSONObject.parseObject(stuId).getString("stuId")));
    }

    @ApiOperation(value = "删除扩展字段")
    @PostMapping(path = "/deleteStudentExpandById")
    @ApiImplicitParam(name = "expandId", value = "扩展编号", required = true, paramType = "form")
    public WebResult deleteStudentExpandById(@RequestBody String expandId){
        MyAssert.isNull(expandId, DefineCode.ERR0010, "扩展编号不为空");
        studentExpandService.deleteById(JSONObject.parseObject(expandId).getString("expandId"));
        return WebResult.okResult();
    }

    @ApiOperation(value = "保存修改学生扩展字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "expandId", value = "补充编号/扩展编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "expandName", value = "补充字段名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "expandValue", value = "补充字段的值", dataType = "string", paramType = "form")
    })
    @PostMapping("/saveUpdateStudentExpand")
    public WebResult saveUpdateStudentExpand(@RequestBody StudentExpandRequest request){
        StudentExpandVo vo = new StudentExpandVo();
        BeanUtil.copyProperties(request, vo);
        studentExpandService.saveUpdateStudentExpand(vo);
        return WebResult.okResult();
    }
}