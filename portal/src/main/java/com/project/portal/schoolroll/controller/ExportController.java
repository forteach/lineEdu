package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.ExportService;
import com.project.token.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 08:25
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@RestController
@Api(value = "导出接口", tags = {"导出相关数据接口"})
@RequestMapping(path = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExportController {

    private final ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

//    @UserLoginToken
    @ApiOperation(value = "导入学生需要信息模版")
    @PostMapping(path = "/exportStudentTemplate")
    public WebResult leadingOutStudentTemplate(){
        exportService.exportStudentTemplate();
        return WebResult.okResult();
    }

    @ApiOperation(value = "导出学生信息")
    @GetMapping(path = "/exportStudents")
    public WebResult exportStudents(HttpServletResponse response) throws IOException {
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        //响应头信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel; charset=UTF-8");
        List<List<?>> rows = exportService.exportStudents();
        BigExcelWriter writer= ExcelUtil.getBigWriter("/home/yy/Downloads/stu.xlsx", "学生信息");
        writer.write(rows);
//        response.getOutputStream()
        writer.close();
        return WebResult.okResult();
    }

//    public static Map<String, Object> main(Object args) {
//        return Arrays.stream(BeanUtils.getPropertyDescriptors(args.getClass()))
//                .filter(pd -> !"class".equals(pd.getName()))
//                .collect(HashMap::new,
//                        (map, pd) -> map.put(pd.getName(), ReflectionUtils.invokeMethod(pd.getReadMethod(), args)),
//                        HashMap::putAll);
//    }

}
