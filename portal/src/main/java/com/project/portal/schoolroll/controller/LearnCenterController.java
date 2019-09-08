package com.project.portal.schoolroll.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.LearnCenterSaveUpdateRequest;
import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.service.LearnCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "学习中心", tags = {"学习中心管理"})
@RequestMapping(path = "/learnCenter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LearnCenterController {
    private final LearnCenterService learnCenterService;
    private final LearnCenterRepository learnCenterRepository;

    public LearnCenterController(LearnCenterService learnCenterService, LearnCenterRepository learnCenterRepository) {
        this.learnCenterService = learnCenterService;
        this.learnCenterRepository = learnCenterRepository;
    }

    @ApiOperation(value = "添加修改学习中心信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerName", value = "学习中心名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "address", value = "学习中心地址", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "principal", value = "负责人", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "负责人联系电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankName", value = "银行名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankingAccount", value = "银行账户", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "accountHolder", value = "开户人", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "accountHolderPhone", value = "开户人电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankingAccountAddress", value = "开户行地址", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody LearnCenterSaveUpdateRequest request) {
        if (StrUtil.isNotBlank(request.getCenterId())) {
            learnCenterRepository.findById(request.getCenterId()).ifPresent(learnCenter -> {
                BeanUtils.copyProperties(request, learnCenter);
                learnCenterRepository.save(learnCenter);
            });
        } else {
            MyAssert.isNull(request.getCenterName(), DefineCode.ERR0010, "学习中心名称不为空");
            MyAssert.isNull(request.getPhone(), DefineCode.ERR0010, "学习中心联系人电话不为空");
            MyAssert.isNull(request.getPrincipal(), DefineCode.ERR0010, "学习中心负责人不为空");
            MyAssert.isNull(request.getAddress(), DefineCode.ERR0010, "学习中心地址不为空");
            List<LearnCenter> learnCenters = learnCenterRepository.findByCenterName(request.getCenterName());
            if (!learnCenters.isEmpty()) {
                MyAssert.isNull(null, DefineCode.ERR0011, "已经存在同名学习中心");
            }
            LearnCenter learnCenter = new LearnCenter();
            BeanUtils.copyProperties(request, learnCenter);
            learnCenter.setCenterId(IdUtil.fastSimpleUUID());
            learnCenterRepository.save(learnCenter);
        }
        return WebResult.okResult();
    }

    @ApiOperation(value = "查询中心信息", notes = "查询简介信息学习中心")
    @GetMapping(path = "/select")
    public WebResult findAllDto() {
        return WebResult.okResult(learnCenterService.findAllDto());
    }

    @ApiOperation(value = "分页查询学习中心")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody SortVo sortVo) {
        valideSort(sortVo.getPage(), sortVo.getSize());
        return WebResult.okResult(learnCenterRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN, PageRequest.of(sortVo.getPage(), sortVo.getSize())));
    }

    @ApiOperation(value = "移除学习中心信息")
    @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/removeById")
    public WebResult removeById(@RequestBody String centerId) {
        MyAssert.isNull(centerId, DefineCode.ERR0010, "学习中心id");
        learnCenterService.removeById(JSONObject.parseObject(centerId).getString("centerId"));
        return WebResult.okResult();
    }
}
