package com.project.portal.schoolroll.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.StudentOnLineFindAllPageRequest;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
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

import static com.project.portal.request.ValideSortVo.valideSort;

@Slf4j
@RestController
@RequestMapping(path = "/studentOnLine", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "管理在线学生", tags = {"管理在线学生信息"})
public class StudentOnLineController {

    private final StudentOnLineService studentOnLineService;
    private final TokenService tokenService;


    @Autowired
    public StudentOnLineController(StudentOnLineService studentOnLineService, TokenService tokenService) {
        this.studentOnLineService = studentOnLineService;
        this.tokenService = tokenService;
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
                studentOnLineService.importStudent(file.getInputStream(), centerAreaId);
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
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
    })
    public WebResult findPageAll(@RequestBody StudentOnLineFindAllPageRequest request){
        valideSort(request.getPage(), request.getSize());
        return WebResult.okResult(studentOnLineService.findAllPage(PageRequest.of(request.getPage(), request.getSize())));
    }

    @GetMapping("/import")
    public WebResult save() {
        studentOnLineService.checkoutKey();
        String centerAreaId = "1001";
        studentOnLineService.importStudent(FileUtil.getInputStream("/home/yy/nextcloud-forteach/zip/工作簿1.xlsx"), centerAreaId);
        return WebResult.okResult();
    }
}
