package com.project.base.util.excelImp;

import cn.hutool.poi.excel.ExcelReader;

import java.io.InputStream;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:18
 * @Version: 1.0
 * @Description: excel文件导入不要实现的接口
 */
public interface IExcelImpService<T> {

    public List<T> ExcelReader(InputStream inputStream, Class<T> obj );

//    public List<T> studentsExcel03Reader(InputStream inputStream,Class<T> obj );

    public void setHeaderAlias(ExcelReader reader);
}
