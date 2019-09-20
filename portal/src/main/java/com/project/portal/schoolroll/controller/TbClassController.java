package com.project.portal.schoolroll.controller;

import cn.hutool.core.util.StrUtil;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.TbClassFindAllPageRequest;
import com.project.schoolroll.service.online.TbClassService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

@RestController
@RequestMapping(path = "/class", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "在线班级管理", tags = {"在线班级信息"})
public class TbClassController {
    private final TbClassService tbClassService;
    private final TokenService tokenService;

    public TbClassController(TbClassService tbClassService, TokenService tokenService) {
        this.tbClassService = tbClassService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "查询所有有效班级信息(根据学习中心)")
    @GetMapping(path = "/list")
    public WebResult findAll(HttpServletRequest httpServletRequest) {
        String centerAreaId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
        return WebResult.okResult(tbClassService.findAllByCenterAreaId(centerAreaId));
    }

    @UserLoginToken
    @ApiOperation(value = "管理员查询分中心班级信息")
    @GetMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody TbClassFindAllPageRequest request){
        valideSort(request.getPage(), request.getSize());
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getCenterAreaId())){
            return WebResult.okResult(tbClassService.findAllPageByCenterAreaId(request.getCenterAreaId(), pageRequest));
        }
        return WebResult.okResult(tbClassService.findAllPage(pageRequest));
    }
}