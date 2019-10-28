package com.project.portal.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/28 16:22
 * @Version: 1.0
 * @Description:
 */
public class MyExcleUtil {
    public static void getExcel(HttpServletResponse response, HttpServletRequest request, List list, String fileName) throws IOException {
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "没有要导出的数据");
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        if (request.getHeader("user-agent").toLowerCase().indexOf("firefox") > 1){
            response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
        }else {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        }
        writer.flush(response.getOutputStream());
        writer.close();
    }
}
