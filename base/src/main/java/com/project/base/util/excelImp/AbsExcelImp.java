package com.project.base.util.excelImp;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import lombok.NonNull;

import java.io.InputStream;
import java.util.List;

public abstract class AbsExcelImp<T>  implements  IExcelImpService<T>{

    @Override
    public List<T> ExcelReader(InputStream inputStream, Class<T> obj) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<T> list = reader.readAll(obj);
        return list;
    }

    @Override
    public void setHeaderAlias(@NonNull ExcelReader reader) {
        MyAssert.isNull(null, DefineCode.ERR0002,"Excel 数据结构未初始化");
    }
}
