package com.project.portal.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.user.request.TeacherFindAllPageRequest;
import com.project.portal.user.request.TeacherSaveUpdateRequest;
import com.project.portal.user.request.TeacherUploadFileRequest;
import com.project.portal.util.MyExcleUtil;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.user.domain.TeacherFile;
import com.project.user.domain.TeacherVerify;
import com.project.user.service.TeacherService;
import com.project.user.web.vo.TeacherVerifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:45
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@Api(value = "教师信息管理", tags = {"教师管理"})
@RequestMapping(path = "/teacher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TeacherController {
    private final TeacherService teacherService;
    private final TokenService tokenService;

    public TeacherController(TeacherService teacherService, TokenService tokenService) {
        this.teacherService = teacherService;
        this.tokenService = tokenService;
    }

    private void validator(TeacherSaveUpdateRequest request) {
        if (StrUtil.isNotBlank(request.getPhone())) {
            MyAssert.isFalse(Validator.isMobile(request.getPhone()), DefineCode.ERR0002, "联系电话不是手机号码");
        }
        if (StrUtil.isNotBlank(request.getEmail())) {
            MyAssert.isFalse(Validator.isEmail(request.getEmail()), DefineCode.ERR0002, "电子邮箱格式不正确");
        }
        if (StrUtil.isNotBlank(request.getIdCard())) {
            MyAssert.isFalse(IdcardUtil.isValidCard(request.getIdCard()), DefineCode.ERR0002, "身份证格式不正确");
        }
    }

    @UserLoginToken
    @ApiOperation(value = "添加修改教师信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", paramType = "form", example = "主键，不存在添加，否则修改"),
            @ApiImplicitParam(name = "teacherName", value = "教师名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "teacherCode", value = "教师代码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "birthDate", value = "出生年月", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "idCard", value = "身份证号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "professionalTitle", value = "现任专业技术职务", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "professionalTitleDate", value = "现任专业技术职务取得时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "position", value = "工作单位及职务", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "industry", value = "所在行业", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "specialty", value = "专业", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "isFullTime", value = "是否全日制(Y/N)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "academicDegree", value = "学位", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankCardAccount", value = "银行卡账户", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankCardBank", value = "银行卡开户行", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody TeacherSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        TeacherVerify teacher = new TeacherVerify();
        BeanUtil.copyProperties(request, teacher);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        teacher.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getTeacherId())) {
            MyAssert.isNull(request.getPhone(), DefineCode.ERR0010, "联系电话不为空");
            validator(request);
            String centerAreaId = tokenService.getCenterAreaId(token);
            teacher.setCenterAreaId(centerAreaId);
            teacher.setCreateUser(userId);
            return WebResult.okResult(teacherService.save(teacher));
        } else {
            validator(request);
            return WebResult.okResult(teacherService.update(teacher));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "查询有效教师信息集合")
    @GetMapping(path = "/")
    public WebResult findAll(HttpServletRequest httpServletRequest) {
        String centerAreaId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
        return WebResult.okResult(teacherService.findAllByCenterAreaId(centerAreaId));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询教师信息")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "教师状态 0 (已经审核) 1 (未提交) 2 (已经拒绝)", dataType = "string", name = "verifyStatus", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllPage(@RequestBody TeacherFindAllPageRequest request, HttpServletRequest httpServletRequest) {
        valideSort(request.getPage(), request.getSize());
        String token = httpServletRequest.getHeader("token");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (!tokenService.isAdmin(token)) {
            //是学习中心员工
            String centerAreaId = tokenService.getCenterAreaId(token);
            if (StrUtil.isNotBlank(request.getVerifyStatus())) {
                return WebResult.okResult(teacherService.findAllPageDtoByVerifyStatusAndCenterAreaId(request.getVerifyStatus(), centerAreaId, pageRequest));
            }
            return WebResult.okResult(teacherService.findAllPageByCenterAreaIdDto(centerAreaId, pageRequest));
        } else {
            if (StrUtil.isBlank(request.getVerifyStatus())) {
                return WebResult.okResult(teacherService.findAllPageDto(pageRequest));
            }
            return WebResult.okResult(teacherService.findAllPageDtoByVerifyStatus(request.getVerifyStatus(), pageRequest));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "移除教师信息(逻辑删除)")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/removeById")
    public WebResult removeById(@RequestBody String teacherId) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不能为空");
        teacherService.removeByTeacherId(JSONObject.parseObject(teacherId).getString("teacherId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除教师信息(物理删除)")
    @DeleteMapping(path = "/teacherId/{teacherId}")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteByTeacherId(@PathVariable String teacherId) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不能为空");
        teacherService.deleteByTeacherId(teacherId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "根据教师代码删除教师信息")
    @DeleteMapping(path = "/teacherCode/{teacherCode}")
    @ApiImplicitParam(name = "teacherCode", value = "教师代码", dataType = "string", required = true, paramType = "form")
    public WebResult deleteByTeacherCode(@PathVariable String teacherCode) {
        MyAssert.isNull(teacherCode, DefineCode.ERR0010, "教师id不能为空");
        teacherService.deleteByTeacherCode(teacherCode);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "文件url", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", required = true, paramType = "form")
    })
    public WebResult uploadFile(@RequestBody TeacherUploadFileRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getTeacherId(), DefineCode.ERR0010, "教师id不为空");
        MyAssert.isNull(request.getFileUrl(), DefineCode.ERR0010, "文件不为空");
        String token = httpServletRequest.getHeader("token");
        String centerAreaId = tokenService.getCenterAreaId(token);
        String userId = tokenService.getUserId(token);
        TeacherFile teacherFile = new TeacherFile();
        BeanUtil.copyProperties(request, teacherFile);
        teacherFile.setCenterAreaId(centerAreaId);
        teacherFile.setUpdateUser(userId);
        teacherFile.setCreateUser(userId);
        return WebResult.okResult(teacherService.saveFile(teacherFile));
    }

    @UserLoginToken
    @GetMapping(path = "/files/{teacherId}")
    @ApiOperation(value = "查询教师信息的文件资料信息")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    public WebResult findAllTeacherFile(@PathVariable String teacherId) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不为空");
        return WebResult.okResult(teacherService.findTeacherFile(teacherId));
    }

    @UserLoginToken
    @ApiOperation(value = "删除教师的文件")
    @DeleteMapping(path = "/file/{fileId}")
    @ApiImplicitParam(name = "fileId", value = "文件id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteFile(@PathVariable String fileId) {
        MyAssert.isNull(fileId, DefineCode.ERR0010, "文件id不为空");
        teacherService.deleteTeacherFile(fileId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "修改教师用户信息状态")
    @PutMapping(path = "/status/{teacherId}")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String teacherId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teacherService.updateStatus(teacherId, userId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "审核教师信息")
    @PostMapping(path = "/verifyTeacher")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "verifyStatus", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "form")
    })
    public WebResult verifyTeacher(@RequestBody TeacherVerifyVo teacherVerifyVo) {
        MyAssert.isNull(teacherVerifyVo.getTeacherId(), DefineCode.ERR0010, "教师Id不能为空");
        MyAssert.isTrue(StrUtil.isBlank(teacherVerifyVo.getVerifyStatus()), DefineCode.ERR0010, "状态不能为空");
        teacherService.verifyTeacher(teacherVerifyVo);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "导出教师信息")
    @PostMapping(path = "/exportTeachers")
    public WebResult exportTeachers(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = httpServletRequest.getHeader("token");
        String centerId = tokenService.getCenterAreaId(token);
        try {
            List<List<String>> lists;
            if (tokenService.isAdmin(token)) {
                //是管理员，导出全部教师信息
                lists = teacherService.exportTeachers();
            } else {
                //不是管理员导出对应的学习中心教师信息
                lists = teacherService.exportTeachers(centerId);
            }
            MyExcleUtil.getExcel(httpServletResponse, httpServletRequest, lists, "教师信息.xlsx");
        } catch (IOException e) {
            log.error("导出教师信息失败, centerId ; [{}], message : [{}]", centerId, e.getMessage());
            MyAssert.notNull(e, DefineCode.ERR0009, "导出信息失败");
            e.printStackTrace();
        }
        return WebResult.okResult();
    }
}