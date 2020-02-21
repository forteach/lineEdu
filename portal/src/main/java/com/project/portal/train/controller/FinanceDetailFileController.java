package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FinanceDetailFileFindAllPage;
import com.project.portal.train.request.FinanceDetailFileSaveUpdateRequest;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.domain.FinanceDetailFile;
import com.project.train.service.FinanceDetailFileService;
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
 * @date: 19-8-15 18:40
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "财务凭证文件详细管理", tags = {"财务凭证文件详细管理"})
@RequestMapping(path = "/financeDetailFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FinanceDetailFileController {
    private final FinanceDetailFileService financeDetailFileService;
    private final TokenService tokenService;

    public FinanceDetailFileController(FinanceDetailFileService financeDetailFileService, TokenService tokenService) {
        this.financeDetailFileService = financeDetailFileService;
        this.tokenService = tokenService;
    }
    @UserLoginToken
    @ApiOperation(value = "培训项目班级保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "财务凭证资料编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "财务凭证资料名称", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "财务凭证资料URL", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "财务凭证计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody FinanceDetailFileSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        FinanceDetailFile financeDetailFile = new FinanceDetailFile();
        BeanUtil.copyProperties(request, financeDetailFile);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        financeDetailFile.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getFileId())) {
            String centerAreaId = tokenService.getCenterAreaId(token);
            financeDetailFile.setCenterAreaId(centerAreaId);
            financeDetailFile.setCreateUser(userId);
            return WebResult.okResult(financeDetailFileService.save(financeDetailFile));
        } else {
            return WebResult.okResult(financeDetailFileService.update(financeDetailFile));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "培训项目班级列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody FinanceDetailFileFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getPjPlanId(), DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(financeDetailFileService
                .findPlanPage(request.getCenterAreaId(), request.getPjPlanId(), PageRequest.of(request.getPage(), request.getSize())));
    }
}