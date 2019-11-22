package com.project.portal.user.controller;

import com.project.mongodb.service.UserRecordService;
import com.project.portal.request.SortVo;
import com.project.portal.request.ValideSortVo;
import com.project.portal.response.WebResult;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-22 11:33
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@Api(value = "用户操作记录", tags = {"查询用户操作记录,登录、删除信息"})
@RequestMapping(path = "/userRecord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserRecordController {
    private final TokenService tokenService;
    private final UserRecordService userRecordService;

    public UserRecordController(UserRecordService userRecordService, TokenService tokenService) {
        this.userRecordService = userRecordService;
        this.tokenService = tokenService;
    }
    @PostMapping(path = "/findUserRecordAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findUserRecordAllPage(@RequestBody SortVo sortVo, HttpServletRequest httpServletRequest){
        ValideSortVo.valideSort(sortVo.getPage(), sortVo.getSize());
        String token = httpServletRequest.getHeader("token");
        PageRequest of = PageRequest.of(sortVo.getPage(), sortVo.getSize());
        if (tokenService.isAdmin(token)){
            return WebResult.okResult(userRecordService.findAllPage(of));
        }
        return WebResult.okResult(userRecordService.findAllPageByCenterId(tokenService.getCenterAreaId(token), of));
    }
}