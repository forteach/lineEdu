package com.project.schoolroll.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import com.project.schoolroll.service.LeadingInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:20
 * @Version: 1.0
 * @Description:
 */
@Service
@Slf4j
public class LeadingInServiceImpl implements LeadingInService {
    private final StudentRepository studentRepository;
    private final StudentExpandRepository studentExpandRepository;
    private final StudentPeopleRepository studentPeopleRepository;

    @Autowired
    public LeadingInServiceImpl(StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository, StudentExpandRepository studentExpandRepository) {
        this.studentRepository = studentRepository;
        this.studentExpandRepository = studentExpandRepository;
        this.studentPeopleRepository = studentPeopleRepository;
    }

    @Override
    public void studentsSave(InputStream inputStream) {

        Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
        ExcelUtil.getWriter(true);
        reader.read("aaa.xlsx", 0);
    }

    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] [{}]", sheetIndex, rowIndex, rowlist);
            }
        };
    }
}
