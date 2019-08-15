package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.ClassFileFindAllPage;
import com.project.portal.train.request.ClassFileSaveUpdateRequest;
import com.project.portal.train.request.FinanceDetailFindAllPage;
import com.project.portal.train.request.FinanceDetailSaveUpdateRequest;
import com.project.train.domain.ClassFile;
import com.project.train.domain.FinanceDetail;
import com.project.train.service.ClassFileService;
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
 * @date: 19-8-15 18:38
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训班级资料管理", tags = {"培训班级资料管理"})
@RequestMapping(path = "/classFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClassFileController {
    private final ClassFileService classFileService;

    public ClassFileController(ClassFileService classFileService) {
        this.classFileService = classFileService;
    }

    @ApiOperation(value = "培训财务明细保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "培训资料编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "培训资料名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "培训资料URL", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "培训班级编号", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody ClassFileSaveUpdateRequest request) {
        ClassFile classFile = new ClassFile();
        BeanUtil.copyProperties(request, classFile);
        if (StrUtil.isNotBlank(request.getFileId())) {
            return WebResult.okResult(classFileService.save(classFile));
        } else {
            return WebResult.okResult(classFileService.update(classFile));
        }
    }

    @ApiOperation(value = "班级资料明细列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody ClassFileFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getCenterAreaId(), DefineCode.ERR0010, "归属的学习中心编号不为空");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getClassId())) {
            return WebResult.okResult(classFileService
                    .findAllPage(request.getCenterAreaId(), request.getClassId(), pageRequest));
        }else {
            return WebResult.okResult(classFileService.findAllPage(request.getCenterAreaId(), pageRequest));
        }
    }

    @ApiOperation(value = "班级资料明细查询")
    @PostMapping(path = "/findById")
    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "培训项目计划编号id不为空");
        return WebResult.okResult(classFileService.findId(JSONObject.parseObject(planId).getString("planId")));
    }
}
