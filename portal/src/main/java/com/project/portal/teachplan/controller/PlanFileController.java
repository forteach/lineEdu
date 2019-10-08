package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.*;
import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.service.PlanFileService;
import com.project.teachplan.vo.TeachFileVerifyVo;
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

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:38
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "在线计划班级资料管理", tags = {"在线计划班级资料管理"})
@RequestMapping(path = "/planFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PlanFileController {
    private final PlanFileService planFileService;
    private final TokenService tokenService;

    public PlanFileController(PlanFileService planFileService, TokenService tokenService) {
        this.planFileService = planFileService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "班级资料保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "资料名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileType", value = "资料类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "资料URL", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "createDate", value = "上课日期", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody PlanFileSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getFileUrl(), DefineCode.ERR0010, "文件地址不能为空");
        PlanFile planFile = new PlanFile();
        BeanUtil.copyProperties(request, planFile);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        planFile.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getFileId())) {
            MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不能为空");
            MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级id不为空");
            MyAssert.isNull(request.getCourseId(), DefineCode.ERR0010, "课程id不为空");
            MyAssert.isNull(request.getCreateDate(), DefineCode.ERR0010, "上课日期不为空");
            String centerAreaId = tokenService.getCenterAreaId(token);
            planFile.setCenterAreaId(centerAreaId);
            planFile.setCreateUser(userId);
            return WebResult.okResult(planFileService.save(planFile));
        } else {
            return WebResult.okResult(planFileService.update(planFile));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "班级资料根据学习中心查询明细列表")
    @PostMapping(path = "/findByCenterAreaIdAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findByCenterAreaIdAllPage(@RequestBody PlanFileFindAllPage request, HttpServletRequest httpServletRequest) {
        valideSort(request.getPage(), request.getPage());
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        String centerAreaId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
        if (StrUtil.isNotBlank(request.getClassId())) {
            return WebResult.okResult(planFileService.findAllPagePlanFileDtoByCenterAreaIdAndClassId(centerAreaId, request.getClassId(), pageRequest));
        } else {
            return WebResult.okResult(planFileService.findAllPagePlanFileDtoByCenterAreaId(centerAreaId, pageRequest));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "班级资料明细列表分页查询")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "planId", value = "计划Id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllPage(@RequestBody PlanFileFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级Id不能为空");
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划Id不能为空");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getCreateDate())) {
            return WebResult.okResult(planFileService.findAllPageFileListByCreateDate(request.getPlanId(), request.getClassId(), request.getCreateDate(), pageRequest));
        } else {
            return WebResult.okResult(planFileService.findAllPageFileList(request.getPlanId(), request.getClassId(), pageRequest));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "查询计划课程详细资料信息")
    @PostMapping(path = "/findAllByCourseIdAndCreateDate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "planId", value = "计划Id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程Id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建日期", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", paramType = "query")
    })
    public WebResult findAllByCourseIdAndCreateDate(@RequestBody PlanFileFindAllRequest request) {
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级Id不能为空");
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划Id不能为空");
        MyAssert.isNull(request.getCourseId(), DefineCode.ERR0010, "课程Id不能为空");
        MyAssert.isNull(request.getCreateDate(), DefineCode.ERR0010, "日期Id不能为空");
        if (StrUtil.isBlank(request.getVerifyStatus())) {
            return WebResult.okResult(planFileService.findAllByCourseIdAndCreateDate(request.getPlanId(),
                    request.getClassId(), request.getCourseId(), request.getCreateDate()));
        }
        //根据状态查询对应的课程章节资料
        return WebResult.okResult(planFileService.findAllByCourseIdAndCreateDateAndVerifyStatus(request.getPlanId(),
                request.getClassId(), request.getCourseId(), request.getCreateDate(), request.getVerifyStatus()));
    }

    @UserLoginToken
    @ApiOperation(value = "根据计划编号分页查询班级资料明细")
    @PostMapping(path = "/findByPlanId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findById(@RequestBody PlanFileFindByPlanIdRequest request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "项目计划编号id不为空");
        return WebResult.okResult(planFileService.findByPlanIdPageAll(request.getPlanId(), PageRequest.of(request.getPage(), request.getSize())));
    }

    @UserLoginToken
    @ApiOperation(value = "根据计划id和班级id查询对应的文件列表信息")
    @PostMapping(path = "/findAllByPlanIdAndClassId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "form", required = true)
    })
    public WebResult findAllByPlanIdAndClassId(@RequestBody FlanFileFindAllRequest request) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "项目计划编号id不为空");
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级id不为空");
        return WebResult.okResult(planFileService.findAllPlanIdAndClassId(request.getPlanId(), request.getClassId()));
    }


    @UserLoginToken
    @ApiOperation(value = "通过资料Id删除计划资料")
    @DeleteMapping("/file/{fileId}")
    @ApiImplicitParam(name = "fileId", value = "文件id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteFile(@PathVariable String fileId) {
        MyAssert.isNull(fileId, DefineCode.ERR0010, "文件id不能为空");
        planFileService.deleteByFileId(fileId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "审核班级资料信息")
    @PostMapping(path = "/verifyTeachPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注信息", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "班级编号", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "createDate", value = "上课日期", required = true, dataType = "string", paramType = "form")

    })
    public WebResult verifyPlanFile(TeachFileVerifyRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isNull(request.getVerifyStatus(), DefineCode.ERR0010, "计划状态不能为空");
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级不能为空");
        MyAssert.isNull(request.getCourseId(), DefineCode.ERR0010, "课程不能为空");
        MyAssert.isNull(request.getCreateDate(), DefineCode.ERR0010, "上课时间不能为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        TeachFileVerifyVo verifyVo = new TeachFileVerifyVo();
        BeanUtil.copyProperties(request, verifyVo);
        planFileService.verifyTeachFile(verifyVo, userId);
        return WebResult.okResult();
    }
}