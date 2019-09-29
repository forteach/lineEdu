package com.project.portal.databank.controller;

import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.databank.service.CourseVerifyVoService;
import com.project.databank.web.vo.CourseVerifyRequest;
import com.project.portal.databank.request.FindDatumVerifyRequest;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
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

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:49
 * @version: 1.0
 * @description:
 */
@RestController
@RequestMapping(path = "/courseVerify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "课程章节资料审核", tags = {"章节资料操作信息"})
public class CourseVerifyController {
    private final TokenService tokenService;
    private final CourseVerifyVoService courseVerifyVoService;

    public CourseVerifyController(TokenService tokenService, CourseVerifyVoService courseVerifyVoService) {
        this.tokenService = tokenService;
        this.courseVerifyVoService = courseVerifyVoService;
    }

    @ApiOperation(value = "查询需要审核的课程信息")
    @UserLoginToken
    @PostMapping("/findAllPageVerify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllPageVerify(@RequestBody FindDatumVerifyRequest request) {
        valideSort(request.getPage(), request.getSize());
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getCourseId())) {
            return WebResult.okResult(courseVerifyVoService.findAllPage(request.getCourseId(), pageRequest));
        }
        return WebResult.okResult(courseVerifyVoService.findAllPage(pageRequest));
    }

    @ApiOperation(value = "课程计划审核")
    @UserLoginToken
    @PostMapping(path = "/verify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要审核的id", dataType = "string", required = true),
            @ApiImplicitParam(name = "verifyStatus", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "form")
    })
    public WebResult verifyStatus(@RequestBody CourseVerifyRequest request, HttpServletRequest httpServletRequest){
        MyAssert.isNull(request.getId(), DefineCode.ERR0010, "审核id不能为空");
        MyAssert.isNull(request.getVerifyStatus(), DefineCode.ERR0010, "审核状态不能为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        request.setUserId(userId);
        courseVerifyVoService.saveUpdateVerify(request);
        return WebResult.okResult();
    }
}