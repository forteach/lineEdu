package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.FamilySaveUpdateRequest;
import com.project.portal.train.request.TrainProjectPlanSaveRequest;
import com.project.schoolroll.repository.FamilyRepository;
import com.project.schoolroll.service.FamilyService;
import com.project.train.domain.TrainProjectPlan;
import com.project.train.service.TrainPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(value = "培训项目列表管理", tags = {"培训项目计划列表管理"})
@RequestMapping(path = "/train/plan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainPlanController {

    private final TrainPlanService trainPlanService;
    private TrainPlanController(TrainPlanService trainPlanService){
        this.trainPlanService = trainPlanService;
    }

    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "项目计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainProjectName", value = "项目名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainAreaId", value = "项目领域", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerId", value = "培训中心", dataType = "string", paramType = "form"),
    })
    public WebResult saveOrUpdate(@RequestBody TrainProjectPlanSaveRequest request){
        TrainProjectPlan trainProjectPlan = new TrainProjectPlan();
        BeanUtil.copyProperties(request, trainProjectPlan);
        if (StrUtil.isNotBlank(request.getPjPlanId())) {
            return WebResult.okResult(trainPlanService.save(trainProjectPlan));
        }else {
            return WebResult.okResult(trainPlanService.update(trainProjectPlan));
        }
    }

}
