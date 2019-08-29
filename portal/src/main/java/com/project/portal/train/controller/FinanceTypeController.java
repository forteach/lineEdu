package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FinanceTypeSaveUpdateRequest;
import com.project.train.domain.FinanceType;
import com.project.train.service.FinanceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:43
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = " 培训财务类型字典管理", tags = {" 培训财务类型字典管理"})
@RequestMapping(path = "/financeType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FinanceTypeController {
    private final FinanceTypeService financeTypeService;

    public FinanceTypeController(FinanceTypeService financeTypeService) {
        this.financeTypeService = financeTypeService;
    }

    @ApiOperation(value = " 培训财务类型字典保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeTypeId", value = "培训财务类型编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "financeTypeName", value = "培训财务类型名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody FinanceTypeSaveUpdateRequest request) {
        FinanceType financeType = new FinanceType();
        BeanUtil.copyProperties(request, financeType);
        if (StrUtil.isBlank(request.getFinanceTypeId())) {
            return WebResult.okResult(financeTypeService.save(financeType));
        } else {
            return WebResult.okResult(financeTypeService.update(financeType));
        }
    }

    @ApiOperation(value = "财务类型字典列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query")
    })
    public WebResult findAllPage(@RequestBody String centerAreaId) {
        MyAssert.isNull(centerAreaId, DefineCode.ERR0010, "归属的学习中心编号不为空");
        return WebResult.okResult(financeTypeService.findAll(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
    }

//    @ApiOperation(value = "财务类型字典管理")
//    @PostMapping(path = "/findById")
//    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
//    public WebResult findById(@RequestBody String planId) {
//        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
//        return WebResult.okResult(financeTypeService.findId(JSONObject.parseObject(planId).getString("planId")));
//    }
}
