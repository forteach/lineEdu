package com.project.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import com.project.schoolroll.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:49
 * @Version: 1.0
 * @Description: 导出数据或者模版
 */
@Slf4j
@Service
public class ExportServiceImpl implements ExportService {

    private final StudentExpandRepository studentExpandRepository;
    private final StudentRepository studentRepository;
    private final StudentPeopleRepository studentPeopleRepository;

    @Autowired
    public ExportServiceImpl(StudentExpandRepository studentExpandRepository, StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository) {
        this.studentExpandRepository = studentExpandRepository;
        this.studentRepository = studentRepository;
        this.studentPeopleRepository = studentPeopleRepository;
    }

    @Override
    public void exportStudentTemplate() {
        studentRepository.findAll();
    }

    @Override
    public OutputStream exportStudents() {
        return null;
    }

    //    @Override
//    public void exportStudentTemplate() {
//        Map<String, Object> row1 = new LinkedHashMap<>();
//        row1.put("姓名", "张三");
//        row1.put("年龄", 23);
//        row1.put("成绩", 88.32);
//        row1.put("是否合格", true);
//        row1.put("考试日期", DateUtil.date());
//
//        Map<String, Object> row2 = new LinkedHashMap<>();
//        row2.put("姓名", "李四");
//        row2.put("年龄", 33);
//        row2.put("成绩", 59.50);
//        row2.put("是否合格", false);
//        row2.put("考试日期", DateUtil.date());
//
//        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);
//        // 通过工具类创建writer
//        ExcelWriter writer = ExcelUtil.getWriter("/home/yy/Downloads/writeMapTest.xlsx");
//        //设置自动宽度
//        writer.autoSizeColumn(0, true);
//        writer.addHeaderAlias("test1", "test2");
//        writer.renameSheet("一般成绩但");
//// 合并单元格后的标题行，使用默认标题样式
//        writer.merge(row1.size() - 1, "一班成绩单");
//// 一次性写出内容，使用默认样式
//        writer.write(rows);
//// 关闭writer，释放内存
//        writer.close();
//    }

    //    @Override
//    public void leadingOutStudentTemplate() {
//        Map<String, Object> row1 = new LinkedHashMap<>();
//        row1.put("姓名", "张三");
//        row1.put("年龄", 23);
//        row1.put("成绩", 88.32);
//        row1.put("是否合格", true);
//        row1.put("考试日期", DateUtil.date());
//
//        Map<String, Object> row2 = new LinkedHashMap<>();
//        row2.put("姓名", "李四");
//        row2.put("年龄", 33);
//        row2.put("成绩", 59.50);
//        row2.put("是否合格", false);
//        row2.put("考试日期", DateUtil.date());
//
//        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);
//        // 通过工具类创建writer
//        ExcelWriter writer = ExcelUtil.getWriter("d:/writeMapTest.xlsx");
//// 合并单元格后的标题行，使用默认标题样式
//        writer.merge(row1.size() - 1, "一班成绩单");
//// 一次性写出内容，使用默认样式
//        writer.write(rows);
//// 关闭writer，释放内存
//        writer.close();
//    }

//    @Override
//    public void leadingOutStudentTemplate() {
//        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
//        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
//        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
//        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
//        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
//
//        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
//        //通过工具类创建writerDocuments
//        ExcelWriter writer = ExcelUtil.getWriter("/home/yy/Documents/writeTest.xlsx");
////通过构造方法创建writer
////ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");
//
////跳过当前行，既第一行，非必须，在此演示用
//        writer.passCurrentRow();
//
////合并单元格后的标题行，使用默认标题样式
//        writer.merge(rows.size()-2, "测试标题");
////一次性写出内容
//        writer.write(rows);
////关闭writer，释放内存
//        writer.close();
//    }
}
