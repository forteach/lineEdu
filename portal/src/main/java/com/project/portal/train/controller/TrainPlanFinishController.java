package com.project.portal.train.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.service.TrainPlanFinishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:36
 * @version: 1.0
 * @description: 培训项目计划列表管理
 */
@RestController
@Api(value = "培训项目计划管理", tags = {"培训项目计划管理"})
@RequestMapping(path = "/train/plan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainPlanFinishController {

    private final TrainPlanFinishService trainPlanFinishService;
    private final TokenService tokenService;

    private TrainPlanFinishController(TrainPlanFinishService trainPlanFinishService, TokenService tokenService) {
        this.trainPlanFinishService = trainPlanFinishService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "根据学习中心编号id查询，未填写完成列表")
    @PostMapping(path = "/noFinishList")
    @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query")
    public WebResult findAll(@RequestBody String centerAreaId) {
        MyAssert.isNull(centerAreaId, DefineCode.ERR0010, "学习中心编号id不为空");
        return WebResult.okResult(trainPlanFinishService.findTOP4(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
    }
}