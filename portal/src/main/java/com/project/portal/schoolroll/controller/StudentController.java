package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.StudentDtoFindPageAllRequest;
import com.project.portal.schoolroll.request.StudentExpandRequest;
import com.project.portal.schoolroll.request.StudentExpandValueRequest;
import com.project.portal.schoolroll.request.StudentSaveUpdateRequest;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.service.StudentExpandDictionaryService;
import com.project.schoolroll.service.StudentExpandService;
import com.project.schoolroll.service.StudentService;
import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.project.portal.request.ValideSortVo.valideSort;
import static java.util.stream.Collectors.toList;

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
    private final StudentExpandDictionaryService studentExpandDictionaryService;
    private final TokenService tokenService;

    public StudentController(StudentService studentService, TokenService tokenService,
                             StudentExpandService studentExpandService,
                             StudentExpandDictionaryService studentExpandDictionaryService) {
        this.studentService = studentService;
        this.studentExpandService = studentExpandService;
        this.studentExpandDictionaryService = studentExpandDictionaryService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改学生信息")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "form", example = "有值为修改，否则为新增"),
            @ApiImplicitParam(name = "studentName", value = "学生名字", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "namePinYin", value = "姓名拼音", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuCardType", value = "身份证类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuIDCard", value = "身份证号码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuPhone", value = "联系电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuBirthDate", value = "出生日期(年月)", dataType = "string", paramType = "form"),
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
            @ApiImplicitParam(name = "specialtyId", value = "章节Id", dataType = "string", paramType = "form"),
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
    public WebResult saveUpdate(@RequestBody StudentSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        MyAssert.isNull(request.getStudentId(), DefineCode.ERR0010, "学生id不为空");
        Student student = new Student();
        BeanUtil.copyProperties(request, student);
        student.setCreateUser(userId);
        student.setUpdateUser(userId);
        student.setCenterAreaId(centerAreaId);
        StudentPeople studentPeople = new StudentPeople();
        BeanUtil.copyProperties(request, studentPeople);
        studentPeople.setUpdateUser(userId);
        studentPeople.setCreateUser(userId);
        studentPeople.setCenterAreaId(centerAreaId);
        studentService.saveOrUpdate(student, studentPeople);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询学生信息列表")
    @PostMapping(path = "/findStudentsPageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "studentName", value = "学生名字", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerIds", value = "学习中心id", dataTypeClass = List.class, paramType = "query"),
            @ApiImplicitParam(name = "studentCategory", value = "学习类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyNames", value = "专业id", dataTypeClass = List.class, paramType = "query"),
            @ApiImplicitParam(name = "educationalSystem", value = "学制", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysStudy", value = "就读方式/学习方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "learningModality", value = "学习形式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "waysEnrollment", value = "入学方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateStartDate", value = "开始入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enrollmentDateEndDate", value = "结束入学时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grades", value = "年级", dataTypeClass = List.class, paramType = "query"),
            @ApiImplicitParam(name = "stuPhone", value = "学生手机号码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
    })
    public WebResult findStudentsPageAll(@RequestBody StudentDtoFindPageAllRequest request) {
        valideSort(request.getPage(), request.getSize());
        FindStudentDtoPageAllVo vo = new FindStudentDtoPageAllVo();
        BeanUtil.copyProperties(request, vo);
        vo.setPageable(PageRequest.of(request.getPage(), request.getSize()));
        return WebResult.okResult(studentService.findStudentsPageAll(vo));
    }

    @UserLoginToken
    @ApiOperation(value = "删除学生信息")
    @PostMapping(path = "/deleteStudentByStuId")
    @ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "string", paramType = "form")
    public WebResult deleteStudentByStudentId(@RequestBody String studentId) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "删除学生信息");
        studentService.deleteById(JSONObject.parseObject(studentId).getString("studentId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询扩展学生信息")
    @PostMapping(path = "/findStudentExpandInfo")
    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "form")
    public WebResult findStudentExpandInfo(@RequestBody String studentId) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生Id不能为空");
        return WebResult.okResult(studentExpandService.findStudentExpandInfo(JSONObject.parseObject(studentId).getString("studentId")));
    }

    @UserLoginToken
    @ApiOperation(value = "删除学生对应的扩展信息")
    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/deleteAllStudentExpandByStuId")
    public WebResult deleteAllStudentExpandByStuId(@RequestBody String studentId) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生Id不能为空");
        return WebResult.okResult(studentExpandService.deleteAllStudentExpandByStudentId(JSONObject.parseObject(studentId).getString("studentId")));
    }

    @UserLoginToken
    @ApiOperation(value = "删除扩展字段")
    @PostMapping(path = "/deleteStudentExpandById")
    @ApiImplicitParam(name = "expandId", value = "扩展编号", required = true, paramType = "form")
    public WebResult deleteStudentExpandById(@RequestBody String expandId) {
        MyAssert.isNull(expandId, DefineCode.ERR0010, "扩展编号不为空");
        studentExpandService.deleteById(JSONObject.parseObject(expandId).getString("expandId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改学生扩展字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "expandValues", value = "需要修改保存的值", required = true, dataTypeClass = StudentExpandValueRequest.class, dataType = "list", paramType = "form"),
    })
    @PostMapping("/saveUpdateStudentExpand")
    public WebResult saveUpdateStudentExpand(@RequestBody StudentExpandRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getStudentId(), DefineCode.ERR0010, "学生id不为空");
        MyAssert.isNull(request.getExpandValues(), DefineCode.ERR0010, "需要修改或修改的扩展字段不为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        List<StudentExpand> voList = request.getExpandValues()
                .stream().filter(Objects::nonNull)
                .map(v -> {
                    StudentExpand vo = new StudentExpand();
                    BeanUtil.copyProperties(v, vo);
                    vo.setStudentId(request.getStudentId());
                    vo.setExpandId(IdUtil.fastSimpleUUID());
                    vo.setUpdateUser(userId);
                    vo.setCreateUser(userId);
                    vo.setCenterAreaId(centerAreaId);
                    return vo;
                }).collect(toList());
        studentExpandService.saveUpdateStudentExpand(voList, request.getStudentId());
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询学生详细信息")
    @PostMapping(path = "/findStudentPeopleByStuId")
    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "query")
    public WebResult findStudentPeopleByStuId(@RequestBody String studentId) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生Id不能为空");
        return WebResult.okResult(studentService.findStudentPeopleDtoByStudentId(JSONObject.parseObject(studentId).getString("studentId")));
    }

    @UserLoginToken
    @ApiOperation(value = "查询扩展字段列表字典")
    @GetMapping(path = "/findStudentExpandDic")
    public WebResult findStudentExpandDic() {
        return WebResult.okResult(studentExpandDictionaryService.findDto());
    }
}