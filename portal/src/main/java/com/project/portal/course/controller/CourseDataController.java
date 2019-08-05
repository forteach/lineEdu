package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseDataService;
import com.project.course.web.req.CoursewareAll;
import com.project.databank.web.res.DatumResp;
import com.project.portal.course.controller.verify.CourseDataVer;
import com.project.portal.course.request.CourseDataDatumReq;
import com.project.portal.course.request.CourseDataDeleteReq;
import com.project.portal.databank.request.ChapteDataListReq;
import com.project.portal.databank.request.ChapteDataReq;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 16:52
 * @Version: 1.0
 * @Description:　科目课程资料挂接信息
 */
@Slf4j
@RestController
@RequestMapping(path = "/courseData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "课程资料挂接操作", tags = {"课程资料挂接信息"})
public class CourseDataController {

    @Resource
    private CourseDataVer courseDataVer;
    @Resource
    private CourseDataService courseDataService;
    @Resource
    private TokenService tokenService;

    @UserLoginToken
    @ApiOperation(value = "保存挂接课程资料信息", notes = "保存挂接课程资料信息")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "files", value = "多个文件列表", dataTypeClass = List.class)
    })
    public WebResult save(@ApiParam(name = "courseReq", value = "科目课程对象") @RequestBody CourseDataDatumReq req, HttpServletRequest request) {
        MyAssert.blank(req.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        MyAssert.elt(0, req.getFiles().size(), DefineCode.ERR0010, "章节编号不为空");
        String userId = tokenService.getUserId(request.getHeader("token"));
        req.setCreateUser(userId);
        int resp = courseDataService.save(req.getChapterId(), req.getFiles());
        return WebResult.okResult(String.valueOf(resp));
    }

    @UserLoginToken
    @ApiOperation(value = "修改课程资料挂接共享属性信息", notes = "{\"chapterId\":\"2c9180c067ee2be40167eeb29a7f0004\",\"courseId\":\"40288d5c67ed87b80167ed9569ed0000\",\"datumArea\":\"1\",\"datumType\":\"1\",\"files\":[{\"fileName\":\"工作汇报.docx\",\"fileUrl\":\"http://118.24.120.43:8080/group1/M00/00/02/rBsADFwF5TuAKbfUAAKjQx3o88406.docx\"}]}")
    @PostMapping("/updateAreaAndShare")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "资料编号", dataType = "string", required = true, paramType = "from"),
            @ApiImplicitParam(name = "courseId", value = "科目编号", dataType = "string", required = true, paramType = "from"),
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataType = "string", paramType = "from"),
            @ApiImplicitParam(name = "datumArea", value = "资料领域", dataType = "string", required = true, paramType = "from", example = "资料领域：1教案 2课件 3预习参考 4教学参考 5授课案例"),
            @ApiImplicitParam(name = "datumName", value = "资料名称", dataType = "string", paramType = "from", required = true),
            @ApiImplicitParam(name = "datumType", value = "资料类型", dataType = "string", required = true, paramType = "from", example = "资料类型 1文档　2图册　3视频　4音频　5链接")
    })
    public WebResult updateAreaAndShare(@ApiParam(value = "保存资料信息", name = "chapteData") @RequestBody ChapteDataReq chapteDataReq) {
        courseDataVer.updateAreaAndShare(chapteDataReq);
        //1、初始化参数
        String courseId = chapteDataReq.getCourseId();
        String chapterId = chapteDataReq.getChapterId();
        String fileId = chapteDataReq.getFileId();
        String datumArea = chapteDataReq.getDatumArea();
        String datumType = chapteDataReq.getDatumType();
        // 2、设置返回结果
        return WebResult.okResult(courseDataService.updateAreaAndShare(courseId, chapterId, fileId, datumType, datumArea));
    }

    @UserLoginToken
    @ApiOperation(value = "获得挂接课件资料列表", notes = "获得挂接课件资料列表")
    @PostMapping("/findDatumList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "photoDatumName", value = "图集名称", dataTypeClass = String.class),
            @ApiImplicitParam(name = "files", value = "图集文件url", dataTypeClass = CoursewareAll.class)
    })
    public WebResult findDatumList(@ApiParam(name = "courseReq", value = "科目课程对象") @RequestBody ChapteDataListReq req) {
        MyAssert.blank(req.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        valideSort(req.getPage(), req.getSize());
        PageRequest pageReq = PageRequest.of(req.getPage(), req.getSize());
        //课程挂载列表
        List<DatumResp> list = null;
        //课程资料文件按类型划分挂载的课件数据
        if (StrUtil.isBlank(req.getDatumType())) {
            //查看所有的挂载课件信息
            list = courseDataService.findDatumList(req.getChapterId(), pageReq);
        } else {
            //按文件（音频、视频，文件）类型查看课程挂载的课件数据
            list = courseDataService.findDatumList(req.getChapterId(), req.getDatumType(), pageReq);
        }
        return WebResult.okResult(list);
    }


    @UserLoginToken
    @ApiOperation(value = "删除挂接的课程资料信息", notes = "逻辑删除挂接课程资料信息")
    @PostMapping("/removeCourseData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "files", value = "多个文件列表", dataTypeClass = List.class, example = "传入需要删除的文件id,不传全部删除")
    })
    public WebResult removeCourseData(@RequestBody CourseDataDeleteReq courseDataDeleteReq, HttpServletRequest request) {
        courseDataDeleteReq.setUpdateUser(tokenService.getUserId(request.getHeader("token")));
        com.project.course.web.req.CourseDataDeleteReq dataDeleteReq = new com.project.course.web.req.CourseDataDeleteReq();
        courseDataService.removeCourseData(dataDeleteReq);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除挂接的课程资料信息", notes = "物理删除挂接课程资料信息")
    @PostMapping("/deleteCourseData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "files", value = "多个文件列表", dataTypeClass = List.class, example = "传入需要删除的文件id,不传全部删除")
    })
    public WebResult deleteCourseData(@RequestBody CourseDataDeleteReq courseDataDeleteReq, HttpServletRequest request) {
        courseDataDeleteReq.setUpdateUser(tokenService.getUserId(request.getHeader("token")));
        com.project.course.web.req.CourseDataDeleteReq dataDeleteReq = new com.project.course.web.req.CourseDataDeleteReq();
        BeanUtil.copyProperties(courseDataDeleteReq, dataDeleteReq);
        courseDataService.deleteCourseData(dataDeleteReq);
        return WebResult.okResult();
    }
}