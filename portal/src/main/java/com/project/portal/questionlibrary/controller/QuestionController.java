package com.project.portal.questionlibrary.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.QuestionService;
import com.project.course.web.vo.QuestionVo;
import com.project.mongodb.domain.BigQuestion;
import com.project.portal.response.WebResult;
import com.project.portal.util.MyExcleUtil;
import com.project.token.annotation.PassToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/9 11:18
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@RestController
@Api(value = "习题相关接口", tags = {"习题相关接口，下载模版，导入习题"})
@RequestMapping(path = "/question", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QuestionController {
    private final TokenService tokenService;
    private final QuestionService questionService;

    public QuestionController(TokenService tokenService, QuestionService questionService) {
        this.tokenService = tokenService;
        this.questionService = questionService;
    }

    @PassToken
    @ApiOperation(value = "导出学生需要信息模版")
    @GetMapping(path = "/exportQuestionTemplate/{token}")
    public WebResult leadingOutStudentTemplate(@PathVariable String token, HttpServletResponse response, HttpServletRequest request) throws IOException {
        MyAssert.isFalse(StrUtil.isNotBlank(token), DefineCode.ERR0010, "token is null");
        MyAssert.isFalse(tokenService.checkToken(token), DefineCode.ERR0010, "401");
        String fileName = "试题导入模板.xlsx";
        InputStream inputStream = new ClassPathResource("template/试题导入模板.xlsx").getStream();
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型为下载类型
        MyExcleUtil.setResponse(response, request, fileName);
        //用 common-io 工具 将输入流拷贝到输出流
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        return WebResult.okResult();
    }

    @PassToken
    @ApiOperation(value = "导入课程章节对应的习题")
    @PostMapping(path = "/importQuestion")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程Id", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "chapterId", value = "章节Id", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "chapterName", value = "章节名称", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "form", dataType = "string")
    })
    public WebResult importCourseScore(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "文件信息不为空");
        String token = httpServletRequest.getHeader("token");
        MyAssert.isFalse(StrUtil.isNotBlank(token), DefineCode.ERR0010, "token is null");
        MyAssert.isFalse(tokenService.checkToken(token), DefineCode.ERR0010, "401");
        String courseId = httpServletRequest.getParameter("courseId");
        String courseName = httpServletRequest.getParameter("courseName");
        String chapterId = httpServletRequest.getParameter("chapterId");
        String chapterName = httpServletRequest.getParameter("chapterName");
        MyAssert.isTrue(StrUtil.isBlank(courseId), DefineCode.ERR0010, "课程Id不能为空");
        MyAssert.isTrue(StrUtil.isBlank(courseName), DefineCode.ERR0010, "课程名称不能为空");
        MyAssert.isTrue(StrUtil.isBlank(chapterId), DefineCode.ERR0010, "章节Id不能为空");
        MyAssert.isTrue(StrUtil.isBlank(chapterName), DefineCode.ERR0010, "章节名称不能为空");
        String fileType = FileUtil.extName(file.getOriginalFilename());
        if (StrUtil.isNotBlank(fileType) && "xlsx".equals(fileType) || "xls".equals(fileType)) {
            String centerId = tokenService.getCenterAreaId(token);
            String userId = tokenService.getUserId(token);
            String userName = tokenService.getUserName(token);
            String centerName = tokenService.getCenterName(token);
            try {
                return WebResult.okResult(questionService.readImportQuestion(file.getInputStream(),
                        new QuestionVo(courseId, courseName, chapterId, chapterName, userId, userName, centerId, centerName)));
            } catch (IOException e) {
                log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
                e.printStackTrace();
            }
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }

    @ApiOperation(value = "获取导入的习题集")
    @GetMapping(path = "/getImportQuestion")
    public WebResult getImportQuestions(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        return WebResult.okResult(questionService.getRedisBigQuestion(userId));
    }

    @ApiOperation(value = "保存导入的习题集")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigQuestion", dataTypeClass = List.class, required = true, paramType = "form")
    })
    @PostMapping(path = "/saveQuestion")
    public WebResult saveQuestion(@RequestBody List<BigQuestion> bigQuestions){
        MyAssert.isTrue(bigQuestions.isEmpty(), DefineCode.ERR0010, "习题集不能为空");
        questionService.saveBigQuestion(bigQuestions);
        return WebResult.okResult();
    }
}