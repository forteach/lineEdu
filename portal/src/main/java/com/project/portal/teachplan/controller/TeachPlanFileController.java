package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.TeachPlanFileFinaAllRequest;
import com.project.portal.teachplan.request.TeachPlanFileSaveRequest;
import com.project.portal.teachplan.request.TeachPlanVerifyRequest;
import com.project.teachplan.domain.TeachPlanFile;
import com.project.teachplan.service.TeachPlanFileService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-25 16:49
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@Api(value = "在线教学计划资料管理", tags = {"在线教学计划资料管理"})
@RequestMapping(path = "/TeachPlanFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TeachPlanFileController {
    private final TeachPlanFileService teachPlanFileService;
    private final TokenService tokenService;

    public TeachPlanFileController(TeachPlanFileService teachPlanFileService, TokenService tokenService) {
        this.teachPlanFileService = teachPlanFileService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "计划资料保存修改")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "资料名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileType", value = "资料类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "资料类型，上传分类 A.教学大纲、B.教学计划、C.课程表 D, 教材库", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "资料URL", dataType = "string", required = true, paramType = "form")
    })
    public WebResult save(@RequestBody TeachPlanFileSaveRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getFileUrl(), DefineCode.ERR0010, "文件地址不能为空");
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不能为空");
        MyAssert.isNull(request.getType(), DefineCode.ERR0010, "文件类型不能为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        TeachPlanFile teachPlanFile = new TeachPlanFile();
        BeanUtil.copyProperties(request, teachPlanFile);
        teachPlanFile.setCenterAreaId(centerAreaId);
        teachPlanFile.setUpdateUser(userId);
        teachPlanFile.setCreateUser(userId);
        return WebResult.okResult(teachPlanFileService.save(teachPlanFile));
    }

    @UserLoginToken
    @ApiOperation(value = "根据计划id查询对应资料信息")
    @PostMapping(path = "/findAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "verifyStatus", value = "审核状态 0 已经审核, 1 没有审核 2 拒绝", dataType = "string", paramType = "form")
    })
    public WebResult findAllByPlanId(@RequestBody TeachPlanFileFinaAllRequest request) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        if (StrUtil.isBlank(request.getVerifyStatus())) {
            return WebResult.okResult(teachPlanFileService.findAllByPlanIdGroupByType(request.getPlanId()));
        }
        return WebResult.okResult(teachPlanFileService.findAllByPlanIdAndVerifyStatus(request.getPlanId(), request.getVerifyStatus()));
    }

    @UserLoginToken
    @ApiOperation(value = "通过文件id删除对应的文件信息")
    @DeleteMapping(path = "/{fileId}")
    @ApiImplicitParam(name = "fileId", value = "资料id", dataType = "string", paramType = "form", required = true)
    public WebResult deleteByFileId(@PathVariable String fileId) {
        MyAssert.isNull(fileId, DefineCode.ERR0010, "文件id不能为空");
        teachPlanFileService.deleteByFileId(fileId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除计划对应的资料信息")
    @DeleteMapping(path = "/planId/{planId}")
    @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form", required = true)
    public WebResult deleteAllByPlanId(@PathVariable String planId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        log.info("delete teachPlanFile planId : [{}], userId : [{}]", planId, userId);
        teachPlanFileService.deleteByPlanId(planId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "审核教学计划")
    @PostMapping(path = "/verifyTeachPlanFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注信息", dataType = "string", paramType = "form")
    })
    public WebResult verifyTeachPlan(TeachPlanVerifyRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isNull(request.getVerifyStatus(), DefineCode.ERR0010, "计划状态不能为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teachPlanFileService.verifyTeachPlan(request.getPlanId(), request.getVerifyStatus(), request.getRemark(), userId);
        return WebResult.okResult();
    }
}