package com.project.portal.classfee.controller;

import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.classfee.domain.ClassStandard;
import com.project.classfee.service.ClassStandardService;
import com.project.portal.classfee.request.ClassStandardListReq;
import com.project.portal.classfee.request.ClassStandardSaveReq;
import com.project.portal.request.ByIdReq;
import com.project.portal.response.PageListRes;
import com.project.portal.controller.BaseController;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


/**
 * 课时费标准
 *  * @param <T>   Page 分页对象返回的Domain
 *  * @param <T1>  response 相应输出的List对象类型
 */
@Slf4j
@RestController
@RequestMapping(path = "/classStandard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClassStandardController extends BaseController<ClassStandard,ClassStandardSaveReq> {

    @Resource
    private ClassStandardService classStandardService;

    @PostMapping("/saveOrUpdate")
    public WebResult saveOrUpdate(@RequestBody ClassStandardSaveReq request) {
        log.info("课时费添加");

        String staId=request.getStandardId();
        ClassStandard cls=null;
        if(StrUtil.isBlank(staId)){
            //保存课时费标准
             cls=classStandardService.save(
                    request.getCreateYear(),
                    request.getStudentSum(),
                    request.getStudentSubsidies(),
                    request.getSubsidiesSum(),
                    request.getClass_fee(),
                    request.getCenterAreaId());
        }else{
            //保存课时费标准
             cls=classStandardService.update(
                    staId,
                    request.getCreateYear(),
                    request.getStudentSum(),
                    request.getStudentSubsidies(),
                    request.getSubsidiesSum(),
                    request.getClass_fee());
        }

        MyAssert.isNull(cls, DefineCode.ERR0010,"操作课时费标准失败！");
        //创建输出对象
        ClassStandardSaveReq res= new ClassStandardSaveReq();
        UpdateUtil.copyNullProperties(cls, res);

        return WebResult.okResult(res);
    }

    @PostMapping("/findId")
    public WebResult findAll(@RequestBody ByIdReq request){
        ClassStandard cls=classStandardService.findId(request.getId());
        MyAssert.isNull(cls, DefineCode.ERR0010,"操作课时费标准失败！");
        //创建输出对象
        ClassStandardSaveReq res= new ClassStandardSaveReq();
        UpdateUtil.copyNullProperties(cls, res);

        return WebResult.okResult(res);
    }

    @PostMapping("/findAll")
    public WebResult findAll(@RequestBody ClassStandardListReq request){
        SortVo sortVo = request.getSortVo();
        PageRequest pageReq = PageRequest.of(sortVo.getPage(), sortVo.getSize());
        Page<ClassStandard> result= classStandardService.findAllPage(request.getCenterAreaId(),request.getCreateYear(),pageReq);
        //设置分页返回对象
        return WebResult.okResult(getPageResult(new PageListRes<ClassStandardSaveReq>(),result,new ClassStandardSaveReq()));
    }

}
