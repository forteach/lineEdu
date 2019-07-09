package com.project.portal.schoolroll.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.SpecialtySaveUpdateRequest;
import com.project.schoolroll.service.SpecialtyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/8 22:17
 * @Version: 1.0
 * @Description: 专业信息
 */
@RestController
@Api(value = "专业信息管理", tags = {"专业信息管理"})
@RequestMapping(path = "/specialty", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @ApiOperation(value = "保存修改专业信息")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "specialtyName", value = "专业名称", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody SpecialtySaveUpdateRequest request){
        specialtyService.saveUpdate(request.getSpecialtyId(), request.getSpecialtyName());
        return WebResult.okResult();
    }

    @ApiOperation(value = "查询全部专业信息")
    @GetMapping(path = "/findAll")
    public WebResult finaAll(){
        return WebResult.okResult(specialtyService.findAllSpecialty());
    }

    @ApiOperation(value = "删除专业信息")
    @PostMapping(path = "/deleteBySpecialtyId")
    @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "form")
    public WebResult deleteBySpecialtyId(@RequestBody String specialtyId){
        MyAssert.isNull(specialtyId, DefineCode.ERR0010, "专业id信息不能是空");
        specialtyService.deleteById(JSONObject.parseObject(specialtyId).getString("specialtyId"));
        return WebResult.okResult();
    }
}
