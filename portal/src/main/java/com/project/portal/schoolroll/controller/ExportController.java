package com.project.portal.schoolroll.controller;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.ExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

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
    @GetMapping(path = "/exportStudentTemplate")
    public WebResult leadingOutStudentTemplate(HttpServletResponse res, HttpServletRequest req) throws IOException {
        ServletOutputStream out = res.getOutputStream();
        res.setContentType("multipart/form-data");
        res.setCharacterEncoding("utf-8");
        res.setHeader("Content-disposition", "attachment;filename=学生数据模版.xlsx");
        List<List<String>> list = exportService.exportStudentTemplate();
        ExcelWriter writer = new ExcelWriter(true);
        writer.write(list);
        writer.autoSizeColumnAll();
        writer.flush();
//        out.write(writer.);
        out.flush();
//        BigExcelWriter writer = ExcelUtil.getBigWriter("/home/yy/Downloads/xxx.xlsx");
//        ExcelUtil.
// 一次性写出内容，使用默认样式
//        writer.write(list);
// 关闭writer，释放内存
        writer.close();


//        ServletOutputStream out = res.getOutputStream();
//        res.setContentType("multipart/form-data");
//        res.setCharacterEncoding("utf-8");
//        res.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
//        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
//        String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
//                .getBytes(), "UTF-8");
//        Sheet sheet1 = new Sheet(1, 0);
//        sheet1.setSheetName("第一个sheet");
//        writer.write0(getListString(), sheet1);
//        writer.finish();
//        out.flush();
//    }

        return WebResult.okResult();
    }

    @ApiOperation(value = "导出学生信息")
    @GetMapping(path = "/exportStudents")
    public WebResult exportStudents(HttpServletResponse res, HttpServletRequest req) throws IOException {
        String fileName = "students" + ".xlsx";
        ServletOutputStream out;
        res.setContentType("multipart/form-data");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        String filePath = getClass().getResource("/template/" + fileName).getPath();
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
// 非IE浏览器的处理：
            fileName = new String((fileName).getBytes("UTF-8"), "ISO-8859-1");
        }
        filePath = URLDecoder.decode(filePath, "UTF-8");
        res.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        FileInputStream inputStream = new FileInputStream(filePath);
        out = res.getOutputStream();
        int b = 0;
        byte[] buffer = new byte[1024];
        while ((b = inputStream.read(buffer)) != -1) {
// 4.写到输出流(out)中
            out.write(buffer, 0, b);
        }
        inputStream.close();

        if (out != null) {
            out.flush();
            out.close();
        }
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
