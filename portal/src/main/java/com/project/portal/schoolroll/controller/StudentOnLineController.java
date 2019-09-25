package com.project.portal.schoolroll.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.StudentOnLineFindAllPageRequest;
import com.project.schoolroll.domain.online.StudentOnLine;
import com.project.schoolroll.repository.online.StudentOnLineRepository;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.wechat.mini.app.service.WeChatUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.portal.request.ValideSortVo.valideSort;

@Slf4j
@RestController
@RequestMapping(path = "/studentOnLine", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "管理在线学生", tags = {"管理在线学生信息"})
public class StudentOnLineController {

    private final StudentOnLineService studentOnLineService;
    private final TokenService tokenService;
    private final WeChatUserService weChatUserService;
    private final StudentOnLineRepository studentOnLineRepository;


    @Autowired
    public StudentOnLineController(StudentOnLineService studentOnLineService, TokenService tokenService,
                                   WeChatUserService weChatUserService, StudentOnLineRepository studentOnLineRepository) {
        this.studentOnLineService = studentOnLineService;
        this.tokenService = tokenService;
        this.studentOnLineRepository = studentOnLineRepository;
        this.weChatUserService = weChatUserService;
    }

    @UserLoginToken
    @ApiOperation(value = "导入学生信息数据")
    @PostMapping(path = "/saveImport/{token}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "需要导入的Excel文件", required = true, paramType = "body", dataTypeClass = File.class),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心Id", required = true, paramType = "form", dataType = "string")
    })
    public WebResult saveImport(@RequestParam("file") MultipartFile file, @PathVariable String token) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "导入的文件不存在,请重新选择");
        try {
            studentOnLineService.checkoutKey();
            //设置导入修改时间 防止失败没有过期时间
            String type = FileUtil.extName(file.getOriginalFilename());
            if (StrUtil.isNotBlank(type) && "xlsx".equals(type) || "xls".equals(type)) {
                String centerAreaId = tokenService.getCenterAreaId(token);
                String userId = tokenService.getUserId(token);
                studentOnLineService.importStudent(file.getInputStream(), centerAreaId, userId);
                return WebResult.okResult();
            }
        } catch (IOException e) {
            studentOnLineService.deleteKey();
            log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
            e.printStackTrace();
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询在线学生信息(暂无查询条件)")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", required = true, paramType = "query")
    })
    public WebResult findPageAll(@RequestBody StudentOnLineFindAllPageRequest request, HttpServletRequest httpServletRequest) {
        valideSort(request.getPage(), request.getSize());
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isAdmin(token)) {
            return WebResult.okResult(studentOnLineService.findAllPageDto(PageRequest.of(request.getPage(), request.getSize())));
        }
        String centerAreaId = tokenService.getCenterAreaId(token);
        return WebResult.okResult(studentOnLineService.findAllPageDtoByCenterAreaId(centerAreaId, PageRequest.of(request.getPage(), request.getSize())));
    }

    @ApiOperation(value = "更改学生状态")
    @PutMapping("/status/{studentId}")
    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String studentId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生id不能为空");
        Optional<StudentOnLine> onLine = studentOnLineRepository.findById(studentId);
        MyAssert.isFalse(onLine.isPresent(), DefineCode.ERR0010, "不存在要更改的学生");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        onLine.ifPresent(s -> {
            String status = s.getIsValidated();
            if (TAKE_EFFECT_CLOSE.equals(status)) {
                s.setIsValidated(TAKE_EFFECT_OPEN);
                weChatUserService.updateStatus(studentId, TAKE_EFFECT_OPEN, userId);
            } else {
                s.setIsValidated(TAKE_EFFECT_CLOSE);
                weChatUserService.updateStatus(studentId, TAKE_EFFECT_CLOSE, userId);
            }
            s.setUpdateUser(userId);
            studentOnLineRepository.save(s);
        });
        return WebResult.okResult();
    }

//    @GetMapping("/import")
//    public WebResult save() {
//        studentOnLineService.checkoutKey();
//        String centerAreaId = "1001";
//        studentOnLineService.importStudent(FileUtil.getInputStream("/home/yy/nextcloud-forteach/zip/工作簿1.xlsx"), centerAreaId);
//        return WebResult.okResult();
//    }
}