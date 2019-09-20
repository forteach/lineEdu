package com.project.portal.classfee.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassStandard;
import com.project.classfee.service.ClassStandardService;
import com.project.portal.classfee.request.ClassStandardListReq;
import com.project.portal.classfee.request.ClassStandardSaveReq;
import com.project.portal.request.ByIdReq;
import com.project.portal.response.PageListRes;
import com.project.portal.controller.BaseController;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;


/**
 * 课时费标准
 *  * @param <T>   Page 分页对象返回的Domain
 *  * @param <T1>  response 相应输出的List对象类型
 */
@Slf4j
@Api(value = "课时费管理", tags = {"课时费的管理/添加修改"})
@RestController
@RequestMapping(path = "/classStandard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClassStandardController extends BaseController<ClassStandard,ClassStandardSaveReq> {

    private final ClassStandardService classStandardService;
    private final TokenService tokenService;

    @Autowired
    public ClassStandardController(TokenService tokenService, ClassStandardService classStandardService) {
        this.classStandardService = classStandardService;
        this.tokenService = tokenService;
    }


    @UserLoginToken
    @ApiOperation(value = "添加修改课时费信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课时费编号", name = "standardId", paramType = "form", example = "值为添加,否则修改"),
            @ApiImplicitParam(value = "课时费年份", name = "createYear", paramType = "form"),
            @ApiImplicitParam(value = "学习中心专业", name = "specialtyIds", paramType = "form"),
            @ApiImplicitParam(value = "所属的教学中心编号", name = "centerAreaId", dataType = "string", paramType = "form"),
            @ApiImplicitParam(value = "教学中心学生数量", name = "studentSum", dataType = "int", paramType = "form"),
            @ApiImplicitParam(value = "每位学生补贴金额", name = "studentSubsidies", dataType = "int", paramType = "form"),
            @ApiImplicitParam(value = "中心补贴总金额", name = "subsidiesSum", dataType = "int", paramType = "form"),
            @ApiImplicitParam(value = "每节课课时费", name = "classFee", dataType = "int", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody ClassStandardSaveReq request, HttpServletRequest httpServletRequest) {
        log.info("课时费添加");

        String staId=request.getStandardId();
        ClassStandard cls=null;
        String token = httpServletRequest.getHeader("token");
        String createUser = tokenService.getUserId(token);
        if(StrUtil.isBlank(staId)){
            //保存课时费标准
            String centerAreaId = tokenService.getCenterAreaId(token);
             cls=classStandardService.save(
                    request.getCreateYear(),
                    request.getSpecialtyIds(),
                    request.getStudentSum(),
                    request.getStudentSubsidies(),
                    request.getSubsidiesSum(),
                    centerAreaId, createUser);
        }else{
            //保存课时费标准
             cls=classStandardService.update(
                    staId,
                    request.getCreateYear(),
                     request.getSpecialtyIds(),
                    request.getStudentSum(),
                    request.getStudentSubsidies(),
                    request.getSubsidiesSum(), createUser);
        }

        MyAssert.isNull(cls, DefineCode.ERR0010,"操作课时费标准失败！");

        //创建输出对象
        ClassStandardSaveReq res= new ClassStandardSaveReq();
        BeanUtil.copyProperties(cls, res);

        return WebResult.okResult(res);
    }

    @UserLoginToken
    @PostMapping("/findId")
    @ApiOperation(value = "根据课时信息id查询课时详情信息")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "string", paramType = "id")
    public WebResult findAll(@RequestBody ByIdReq request){
        ClassStandard cls=classStandardService.findId(request.getId());
        MyAssert.isNull(cls, DefineCode.ERR0010,"操作课时费标准失败！");
        //创建输出对象
        ClassStandardSaveReq res= new ClassStandardSaveReq();
        BeanUtil.copyProperties(cls, res);

        return WebResult.okResult(res);
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询课程标准")
    @PostMapping("/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "所属年份", name = "createYear", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody ClassStandardListReq request, HttpServletRequest httpServletRequest){
        valideSort(request.getPage(), request.getSize());
        PageRequest pageReq = PageRequest.of(request.getPage(), request.getSize());
        String centerAreaId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
        Page<ClassStandard> result= classStandardService.findAllPage(centerAreaId,request.getCreateYear(),pageReq);
        //设置分页返回对象
        return WebResult.okResult(getPageResult(new PageListRes<ClassStandardSaveReq>(),result,new ClassStandardSaveReq()));
    }

}