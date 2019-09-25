package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.TeachPlanFileSaveRequest;
import com.project.teachplan.domain.TeachPlanFile;
import com.project.teachplan.service.TeachPlanFileService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
            @ApiImplicitParam(name = "fileUrl", value = "资料URL", dataType = "string", required = true, paramType = "form")
    })
    public WebResult save(@RequestBody TeachPlanFileSaveRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getFileUrl(), DefineCode.ERR0010, "文件地址不能为空");
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不能为空");
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
    @GetMapping(path = "/findAll/{planId}")
    @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form", required = true)
    public WebResult findAllByPlanId(@PathVariable String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        return WebResult.okResult(teachPlanFileService.findAllByPlan(planId));
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
    public WebResult deleteAllByPlanId(@PathVariable String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachPlanFileService.deleteByPlanId(planId);
        return WebResult.okResult();
    }
}