package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FinanceDetailFileFindAllPage;
import com.project.portal.train.request.FinanceDetailFileSaveUpdateRequest;
import com.project.portal.train.request.FinanceDetailFindAllPage;
import com.project.portal.train.request.FinanceDetailSaveUpdateRequest;
import com.project.train.domain.FinanceDetail;
import com.project.train.domain.FinanceDetailFile;
import com.project.train.service.FinanceDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:42
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训财务明细管理", tags = {"培训财务明细管理"})
@RequestMapping(path = "/financeDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FinanceDetailController {

    private final FinanceDetailService financeDetailService;

    public FinanceDetailController(FinanceDetailService financeDetailService) {
        this.financeDetailService = financeDetailService;
    }

    @ApiOperation(value = "培训财务明细保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detailId", value = "培训财务流水编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "financeTypeId", value = "培训财务类型Id", paramType = "form"),
            @ApiImplicitParam(name = "financeTypeName", value = "培训财务类型名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanName", value = "培训项目计划名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "inOut", value = "收入支出", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "money", value = "金额", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "happenTime", value = "账目发生时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody FinanceDetailSaveUpdateRequest request) {
        FinanceDetail financeDetail = new FinanceDetail();
        BeanUtil.copyProperties(request, financeDetail);
        if (StrUtil.isBlank(request.getDetailId())) {
            return WebResult.okResult(financeDetailService.save(financeDetail));
        } else {
            return WebResult.okResult(financeDetailService.update(financeDetail));
        }
    }

    @ApiOperation(value = "培训财务明细列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string"),
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody FinanceDetailFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getCenterAreaId(), DefineCode.ERR0010, "归属的学习中心编号不为空");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(String.valueOf(request.getAgoDay()))) {
            return WebResult.okResult(financeDetailService.findAllPage(request.getCenterAreaId(), Integer.valueOf(request.getAgoDay()), pageRequest));
        }else {
            return WebResult.okResult(financeDetailService.findAllPage(request.getCenterAreaId(), pageRequest));
        }
    }

    @ApiOperation(value = "培训财务明细查询")
    @PostMapping(path = "/findById")
    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "培训项目计划编号id不为空");
        return WebResult.okResult(financeDetailService.findId(JSONObject.parseObject(planId).getString("planId")));
    }
}