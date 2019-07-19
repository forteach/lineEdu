package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel03SaxReader;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.domain.excel.StudentInfo;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import com.project.schoolroll.service.LeadingInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public void studentsExcel07Reader(InputStream inputStream) {
        Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
        reader.read(inputStream, 0);
    }

    @Override
    public void studentsExcel03Reader(InputStream inputStream){
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<StudentInfo> list = reader.readAll(StudentInfo.class);
        saveStudent(list);
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        List<Map<String,Object>> readAll = reader.readAll();

//        readAll.forEach(stringObjectMap -> {
//            stringObjectMap.forEach((k, v) -> Console.log("key : [{}] value : [{}]", k, v));
//        });
//        readAll.forEach((k, v) -> System.out.println("key : [" + k + "], value : [" + v + "]"));
//        Excel03SaxReader reader = new Excel03SaxReader(createRowHandler());
//        reader.read(inputStream, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    void saveStudent(List<StudentInfo> list){
        list.parallelStream()
                .filter(Objects::nonNull)
                .forEach(studentInfo -> {
                    StudentPeople studentPeople = new StudentPeople();
                    BeanUtil.copyProperties(studentInfo, studentPeople);
                    studentPeople.setPeopleId(IdUtil.fastSimpleUUID());
                    String peopleId = studentPeopleRepository.save(studentPeople).getPeopleId();

                    Student student = new Student();
                    BeanUtil.copyProperties(studentInfo, student);
                    student.setPeopleId(peopleId);
                    studentRepository.save(student);

                    StudentExpand studentExpand = new StudentExpand();
                    studentExpand.setExpandId(IdUtil.fastSimpleUUID());
                    studentExpand.setExpandName("stuEmail");
                    studentExpand.setExpandValue(studentInfo.getStuEmail());
                    studentExpandRepository.save(studentExpand);

                    StudentExpand studentExpand2 = new StudentExpand();
                    studentExpand.setExpandId(IdUtil.fastSimpleUUID());
                    studentExpand.setExpandName("familyAddress");
                    studentExpand.setExpandValue(studentInfo.getFamilyAddress());
                    studentExpandRepository.save(studentExpand2);
                });
    }

    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] [{}]", sheetIndex, rowIndex, rowlist);
                if (rowIndex > 0) {
                    rowlist.stream().filter(Objects::nonNull).filter(o -> StrUtil.isNotBlank(String.valueOf(o))).forEachOrdered(o -> {
                        Console.log("Object : [{}], [{}]", o.getClass(), o);
                    });
                }
            }
        };
    }
}
