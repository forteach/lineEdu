package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.ClassFileFindAllPage;
import com.project.portal.train.request.ClassFileFindByPjPlanIdRequest;
import com.project.portal.train.request.ClassFileSaveUpdateRequest;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.domain.ClassFile;
import com.project.train.service.ClassFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:38
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训班级资料管理", tags = {"培训班级资料管理"})
@RequestMapping(path = "/classFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClassFileController {
    private final ClassFileService classFileService;
    private final TokenService tokenService;

    public ClassFileController(ClassFileService classFileService, TokenService tokenService) {
        this.classFileService = classFileService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "培训班级资料保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "项目计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileId", value = "培训资料编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "培训资料名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "培训资料URL", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "培训班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody ClassFileSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        ClassFile classFile = new ClassFile();
        BeanUtil.copyProperties(request, classFile);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        classFile.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getFileId())) {
            String centerAreaId = tokenService.getCenterAreaId(token);
            classFile.setCreateUser(userId);
            classFile.setCenterAreaId(centerAreaId);
            return WebResult.okResult(classFileService.save(classFile));
        } else {
            return WebResult.okResult(classFileService.update(classFile));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "班级资料明细列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody ClassFileFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getCenterAreaId(), DefineCode.ERR0010, "归属的学习中心编号不为空");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getClassId())) {
            return WebResult.okResult(classFileService
                    .findAllPage(request.getCenterAreaId(), request.getClassId(), pageRequest));
        } else {
            return WebResult.okResult(classFileService.findAllPage(request.getCenterAreaId(), pageRequest));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "根据计划编号分页查询班级资料明细")
    @PostMapping(path = "/findByPjPlanId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findById(@RequestBody ClassFileFindByPjPlanIdRequest request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getPjPlanId(), DefineCode.ERR0010, "培训项目计划编号id不为空");
        return WebResult.okResult(classFileService.findByPjPlanIdPageAll(request.getPjPlanId(), PageRequest.of(request.getPage(), request.getSize())));
    }

    @UserLoginToken
    @ApiOperation(value = "根据班级id移除对应培训资料")
    @DeleteMapping(path = "/removeByClassId")
    @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "form", required = true)
    public WebResult removeByClassId(@RequestBody String classId){
        MyAssert.isNull(classId, DefineCode.ERR0010, "班级id不为空");
        classFileService.removeByClassId(JSONObject.parseObject(classId).getString(classId));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "根据计划id移除对应培训资料")
    @DeleteMapping(path = "/removeByPjPlanId")
    @ApiImplicitParam(name = "pjPlanId", value = "培训计划id", dataType = "string", paramType = "form", required = true)
    public WebResult removeByPjPlanId(@RequestBody String pjPlanId){
        MyAssert.isNull(pjPlanId, DefineCode.ERR0010, "班级id不为空");
        classFileService.removeByPjPlanId(JSONObject.parseObject(pjPlanId).getString(pjPlanId));
        return WebResult.okResult();
    }
}
