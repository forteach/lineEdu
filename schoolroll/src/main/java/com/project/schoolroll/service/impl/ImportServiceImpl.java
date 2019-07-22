package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
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
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import com.project.schoolroll.service.ImportService;
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
public class ImportServiceImpl implements ImportService {
    private final StudentRepository studentRepository;
    private final StudentExpandRepository studentExpandRepository;
    private final StudentPeopleRepository studentPeopleRepository;

    @Autowired
    public ImportServiceImpl(StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository, StudentExpandRepository studentExpandRepository) {
        this.studentRepository = studentRepository;
        this.studentExpandRepository = studentExpandRepository;
        this.studentPeopleRepository = studentPeopleRepository;
    }

    @Override
    public void studentsExcel07Reader(InputStream inputStream) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentImport> list = reader.readAll(StudentImport.class);
        saveStudent(list);

//        Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
//        reader.read(inputStream, 0);
    }

    @Override
    public void studentsExcel03Reader(InputStream inputStream) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentImport> list = reader.readAll(StudentImport.class);

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
    void saveStudent(List<StudentImport> list) {
        list.parallelStream()
                .filter(Objects::nonNull)
                .forEach(studentImport -> {
                    StudentPeople studentPeople = new StudentPeople();
                    BeanUtil.copyProperties(studentImport, studentPeople);
                    studentPeople.setPeopleId(IdUtil.fastSimpleUUID());
                    String peopleId = studentPeopleRepository.save(studentPeople).getPeopleId();

                    Student student = new Student();
                    BeanUtil.copyProperties(studentImport, student);
                    student.setPeopleId(peopleId);
                    studentRepository.save(student);

                    StudentExpand studentExpand = new StudentExpand();
                    studentExpand.setExpandId(IdUtil.fastSimpleUUID());
                    studentExpand.setStuId(studentImport.getStuId());
                    studentExpand.setExpandName("学生邮箱");
                    studentExpand.setExpandValue(studentImport.getStuEmail());
                    studentExpandRepository.save(studentExpand);

                    StudentExpand studentExpand2 = new StudentExpand();
                    studentExpand2.setExpandId(IdUtil.fastSimpleUUID());
                    studentExpand2.setStuId(studentImport.getStuId());
                    studentExpand2.setExpandName("家庭地址");
                    studentExpand2.setExpandValue(studentImport.getFamilyAddress());
                    studentExpandRepository.save(studentExpand2);
                });
    }

    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                if (rowIndex > 0) {
                    rowlist.stream()
                            .filter(Objects::nonNull)
                            .filter(o -> StrUtil.isNotBlank(String.valueOf(o)))
                            .forEachOrdered(o -> {
                                Console.log("Object : [{}], [{}]", o.getClass(), o);
                            });
                }
            }
        };
    }

    private void setHeaderAlias(ExcelReader reader){
        //学号
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(0, 0)), "stuId");
        //学生姓名
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(1, 0)),"stuName");
        //学生性别
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(2, 0)),"gender");
        //身份证号
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(3, 0)),"stuIDCard");
        //入学年度
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(4, 0)),"enrollmentDate");
        //招生批次
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(5, 0)), "recruitBatch");
        //专业名称
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(6, 0)),"specialtyName");
        //政治面貌
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(7, 0)),"politicalStatus");
        //民族
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(8, 0)),"nation");
        //学制及计划类别
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(9, 0)),"educationalSystem");
        //中招考试成绩/考试成绩
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(10, 0)),"totalExaminationAchievement");
        //准考证号
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(11, 0)),"entranceCertificateNumber");
        //考试市县 地区
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(12, 0)),"examinationArea");
        //毕业学校
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(13, 0)),"school");
        //就读方式 走读,住校,借宿,其它
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(14, 0)),"waysStudy");
        //报道日期
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(15, 0)),"arrivalDate");
        //家长姓名
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(16, 0)),"familyName");
        //家长电话
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(17, 0)),"familyPhone");
        //工作单位
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(18, 0)),"companyOrganization");
        //学生电话
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(19, 0)),"stuPhone");
        //学生邮箱
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(20, 0)),"stuEmail");
        //家庭住址
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(21, 0)),"familyAddress");
        //备注
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(22, 0)),"remark");

//        reader.addHeaderAlias("学号", "stuId");
//        reader.addHeaderAlias("姓名","stuName");
//        reader.addHeaderAlias("性别","gender");
//        reader.addHeaderAlias("身份证号/生日","stuIDCard");
//        reader.addHeaderAlias("入学年度","enrollmentDate");
//        reader.addHeaderAlias("专业","specialtyName");
//        reader.addHeaderAlias("政治面貌","politicalStatus");
//        reader.addHeaderAlias("民族","nation");
//        reader.addHeaderAlias("学制及计划类别","educationalSystem");
//        reader.addHeaderAlias("中招成绩","totalExaminationAchievement");
//        reader.addHeaderAlias("准考证号","entranceCertificateNumber");
//        reader.addHeaderAlias("家长姓名","familyName");
//        reader.addHeaderAlias("家长联系电话","familyPhone");
//        reader.addHeaderAlias("工作单位","companyOrganization");
//        reader.addHeaderAlias("学生电话","stuPhone");
//        reader.addHeaderAlias("学生邮箱","stuEmail");
//        reader.addHeaderAlias("家庭住址","familyAddress");
//        reader.addHeaderAlias("备注","remark");
    }
}
