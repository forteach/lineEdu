package com.project.portal.databank.controller;

import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseService;
import com.project.databank.service.ChapteDataService;
import com.project.portal.databank.request.ChapteDataListReq;
import com.project.portal.databank.request.ChapteDataReq;
import com.project.portal.databank.request.ChapterDataRemoveReq;
import com.project.portal.databank.verify.ChapterDataVerify;
import com.project.portal.databank.vo.DataDatumVo;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.LearnCenterService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-26 15:43
 * @Version: 1.0
 * @Description: 章节资料库资料操作
 */
@RestController
@RequestMapping(path = "/chapteData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "章节资料", tags = {"章节资料操作信息"})
public class ChapteDataController {

    @Resource
    private ChapteDataService chapteDataService;
    @Resource
    private ChapterDataVerify chapterDataVerify;
    @Resource
    private TokenService tokenService;
//    @Resource
//    private TeacherService teacherService;
    @Resource
    private LearnCenterService learnCenterService;
    @Resource
    private CourseService courseService;


    @UserLoginToken
    @ApiOperation(value = "保存资料信息", notes = "{\"chapterId\":\"2c9180c067ee2be40167eeb29a7f0004\",\"courseId\":\"40288d5c67ed87b80167ed9569ed0000\",\"datumArea\":\"1\",\"datumType\":\"1\",\"files\":[{\"fileName\":\"工作汇报.docx\",\"fileUrl\":\"http://118.24.120.43:8080/group1/M00/00/02/rBsADFwF5TuAKbfUAAKjQx3o88406.docx\"}]}")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目编号", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "datumName", value = "资料名称", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "datumType", value = "资料类型", dataType = "string", required = true, paramType = "form", example = "资料类型 1文档　2图册　3视频　4音频　5链接"),
            @ApiImplicitParam(name = "files", value = "文件对象", dataTypeClass = DataDatumVo.class, paramType = "form", required = true)
    })
    public WebResult save(@ApiParam(value = "保存资料信息", name = "chapteData") @RequestBody ChapteDataReq chapteDataReq, HttpServletRequest httpServletRequest) {
        chapterDataVerify.saveVerify(chapteDataReq);
        //1、初始化参数
        String courseId = chapteDataReq.getCourseId();
        String chapterId = chapteDataReq.getChapterId();
        String datumType = chapteDataReq.getDatumType();
        List<com.project.databank.web.vo.DataDatumVo> files = new ArrayList<>();
        chapteDataReq.getFiles().forEach(c -> files.add(c));
        // 2、设置返回结果
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String userName = tokenService.getUserName(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        String centerName = learnCenterService.findByCenterId(centerAreaId).getCenterName();
//        String teacherName = teacherService.findById(userId).getTeacherName();
        String courseName = courseService.getById(courseId).getCourseName();
        return WebResult.okResult(chapteDataService.save(courseId, chapterId, datumType, files, userId, centerAreaId, centerName, userName, courseName));
    }

    @UserLoginToken
    @ApiOperation(value = "资料信息列表", notes = "{\"chapterId\":\"2c9180c067ee2be40167eeb29a7f0004\",\"courseId\":\"2c91808d678e620701679bfccf570000\",\"datumArea\":\"1\",\"sortVo\":{\"isValidated\":\"0\",\"page\":0,\"size\":15,\"sort\":1}}")
    @PostMapping("/findDatumList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "datumType", value = "资料类型", dataType = "string", required = true, paramType = "form", example = "资料类型 1文档　2图册　3视频　4音频　5链接"),
    })
    public WebResult findDatumList(@ApiParam(value = "资料信息列表", name = "chapteData") @RequestBody ChapteDataListReq req, HttpServletRequest httpServletRequest) {
        valideSort(req.getPage(), req.getSize());
        PageRequest pageReq = PageRequest.of(req.getPage(), req.getSize());
        //判断是否按资源领域查询列表，并设置返回结果
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isStudent(token)) {
            if (StrUtil.isNotBlank(req.getDatumType())) {
                return WebResult.okResult(chapteDataService.findDatumList(req.getChapterId(), req.getDatumType(), pageReq, TAKE_EFFECT_OPEN));
            } else {
                return WebResult.okResult(chapteDataService.findDatumList(req.getChapterId(), pageReq, TAKE_EFFECT_OPEN));
            }
        }else {
            //不是学生查询对应的资料信息
            if (StrUtil.isNotBlank(req.getDatumType())) {
                return WebResult.okResult(chapteDataService.findDatumList(req.getChapterId(), req.getDatumType(), pageReq, ""));
            }else {
                return WebResult.okResult(chapteDataService.findDatumList(req.getChapterId(), pageReq, ""));
            }
        }
    }

    @UserLoginToken
    @ApiOperation(value = "删除单个文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "datumType", dataType = "string", value = "资料类型", example = "example = \"资料类型 1文档　2图册　3视频　4音频　5链接"),
            @ApiImplicitParam(name = "fileId", value = "资料id", dataType = "string", paramType = "form")
    })
    @ApiResponse(code = 0, message = "OK")
    @PostMapping(path = "/deleteById")
    public WebResult deleteById(@RequestBody ChapterDataRemoveReq chapterDataRemoveReq) {
        MyAssert.isNull(chapterDataRemoveReq.getFileId(), DefineCode.ERR0010, "资料id不为空");
        MyAssert.isNull(chapterDataRemoveReq.getDatumType(), DefineCode.ERR0010, "文件类型不能为空");
        chapteDataService.deleteById(chapterDataRemoveReq.getFileId(), chapterDataRemoveReq.getDatumType());
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除一类课程章节的资源信息", notes = "删除一类文件信息")
    @PostMapping("/removeChapteDataList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目编号", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "datumType", value = "资料类型", dataType = "string", paramType = "form", example = "资料类型 1文档　2图册　3视频　4音频　5链接, (多个用‘,’分割), 不传值是全部需要删除挂载")
    })
    @ApiResponse(code = 0, message = "OK")
    public WebResult removeChapteDataList(@RequestBody ChapterDataRemoveReq chapterDataRemoveReq) {
        MyAssert.isNull(chapterDataRemoveReq.getCourseId(), DefineCode.ERR0010, "科目编号不为空");
        MyAssert.isNull(chapterDataRemoveReq.getChapterId(), DefineCode.ERR0010, "科目编号不为空");
        chapteDataService.removeChapteDataList(chapterDataRemoveReq.getCourseId(), chapterDataRemoveReq.getChapterId(), chapterDataRemoveReq.getDatumType());
        return WebResult.okResult();
    }
}