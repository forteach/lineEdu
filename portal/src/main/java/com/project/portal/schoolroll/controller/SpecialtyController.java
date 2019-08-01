package com.project.portal.schoolroll.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.Specialty;
import com.project.course.service.SpecialtyService;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.SpecialtySaveUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.project.portal.request.ValideSortVo.valideSort;

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
    public WebResult saveUpdate(@RequestBody SpecialtySaveUpdateRequest request) {
        if (StrUtil.isBlank(request.getSpecialtyId())) {
            Specialty specialty = specialtyService.findBySpecialtyName(request.getSpecialtyName());
            MyAssert.notNull(specialty, DefineCode.ERR0014, "已经存在相同专业名称");
        }
        return WebResult.okResult(specialtyService.saveUpdate(request.getSpecialtyId(), request.getSpecialtyName()));
    }

    @ApiOperation(value = "查询全部专业信息")
    @GetMapping(path = "/findAll")
    public WebResult finaAll() {
        return WebResult.okResult(specialtyService.findAllSpecialty());
    }

    @ApiOperation(value = "分页查询专业信息")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody SortVo vo) {
        valideSort(vo.getPage(), vo.getSize());
        return WebResult.okResult(specialtyService.findAllPage(vo.getPage(), vo.getSize()));
    }

    @ApiOperation(value = "物理删除专业信息")
    @PostMapping(path = "/deleteById")
    @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "form")
    public WebResult deleteById(@RequestBody String specialtyId) {
        MyAssert.isNull(specialtyId, DefineCode.ERR0010, "专业id信息不能是空");
        specialtyService.deleteById(JSONObject.parseObject(specialtyId).getString("specialtyId"));
        return WebResult.okResult();
    }

    @ApiOperation(value = "移除专业信息(逻辑删除)")
    @PostMapping(path = "/removeById")
    @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "form")
    public WebResult removeId(@RequestBody String specialtyId) {
        MyAssert.isNull(specialtyId, DefineCode.ERR0010, "专业id信息不能是空");
        specialtyService.removeById(JSONObject.parseObject(specialtyId).getString("specialtyId"));
        return WebResult.okResult();
    }

    @ApiOperation(value = "根据id查询专业详情")
    @ApiImplicitParam(name = "specialtyId", value = "专业id", dataType = "string", paramType = "query")
    @PostMapping(path = "/findById")
    public WebResult findById(@RequestBody String specialtyId) {
        MyAssert.isNull(specialtyId, DefineCode.ERR0010, "专业id信息不能是空");
        return WebResult.okResult(specialtyService.getSpecialtyById(JSONObject.parseObject(specialtyId).getString("specialtyId")));
    }
}
