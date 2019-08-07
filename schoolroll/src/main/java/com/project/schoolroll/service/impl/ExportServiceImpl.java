package com.project.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.domain.excel.ViewStudentExport;
import com.project.schoolroll.repository.*;
import com.project.schoolroll.repository.dto.FamilyExportDto;
import com.project.schoolroll.repository.dto.StudentExpandExportDto;
import com.project.schoolroll.repository.dto.StudentExportDto;
import com.project.schoolroll.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

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
    private final FamilyRepository familyRepository;
    private final ViewStudentExportRepository viewStudentExportRepository;

    @Autowired
    public ExportServiceImpl(StudentExpandRepository studentExpandRepository, StudentRepository studentRepository,ViewStudentExportRepository viewStudentExportRepository,
                             StudentPeopleRepository studentPeopleRepository, FamilyRepository familyRepository) {
        this.studentExpandRepository = studentExpandRepository;
        this.studentRepository = studentRepository;
        this.studentPeopleRepository = studentPeopleRepository;
        this.familyRepository = familyRepository;
        this.viewStudentExportRepository = viewStudentExportRepository;
    }

    /**
     * 导出数据模版
     *
     * @return
     */
    @Override
    public List<List<String>> exportStudentTemplate() {
        List<List<String>> list = new ArrayList<>();
        list.add(setExportTemplate());
        return list;
    }

    @Override
    public List<List<?>> exportStudents() {
//        List<Student> studentList = studentRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
//        List<StudentExpandExportDto> studentExpands = studentExpandRepository.findByIsValidatedEquals(TAKE_EFFECT_OPEN);
//        List<StudentPeople> studentPeopleList = studentPeopleRepository.findByIsValidatedEquals(TAKE_EFFECT_OPEN);
//        List<FamilyExportDto> familyExportDtos = familyRepository.findByIsValidatedEqualsDto(TAKE_EFFECT_OPEN);
        List<StudentExportDto> list = studentRepository.findByIsValidatedEqualsDto();
        System.out.println("list.size "+list.size());
//        list.forEach(System.out::println);
        list.forEach(d -> {
            if ("17030421 ".equals(d.getStudentId())) {
                System.out.println("studentName");
                System.out.println(d.getStudentName());
                System.out.println("studentId");
                System.out.println(d.getStudentId());
            }
        });
        return null;
    }

    @Override
    public List<List<?>> exportStudents(List list) {
        //获取需要查询的字段
        List studentList = studentRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
        return null;
    }

    private List<String> setExportTemplate() {
        return CollUtil.newArrayList(
                "姓名",
                "性别",
                "出生日期",
                "身份证件类型",
                "身份证件号",
                "姓名拼音",
                "班级名称",
                "学号",
                "学生类别",
                "学习形式",
                "入学方式",
                "就读方式",
                "国籍/地区",
                "港澳台侨外",
                "婚姻状况",
                "乘火车区间",
                "是否随迁子女",
                "生源地行政区划码",
                "出生地行政区划码",
                "籍贯地行政区划码",
                "户口所在地区县以下详细地址",
                "所属派出所",
                "户口所在地行政区划码",
                "户口性质",
                "学生居住地类型",
                "入学年月",
                "专业简称",
                "学制",
                "民族",
                "政治面貌",
                "健康状况",
                "学生来源",
                "招生对象",
                "联系电话",
                "是否建档立卡贫困户",
                "招生方式",
                "联招合作类型",
                "准考证号",
                "考生号",
                "考试总分",
                "联招合作办学形式",
                "联招合作学校代码",
                "校外教学点",
                "分段培养方式",
                "英文姓名",
                "电子信箱/其他联系方式",
                "家庭现地址",
                "家庭邮政编码",
                "家庭电话",
                "成员1姓名",
                "成员1关系",
                "成员1是否监护人",
                "成员1联系电话",
                "成员1出生年月",
                "成员1身份证件类型",
                "成员1身份证件号",
                "成员1民族",
                "成员1政治面貌",
                "成员1健康状况",
                "成员1工作或学习单位",
                "成员1职务",
                "成员2姓名",
                "成员2关系",
                "成员2是否监护人",
                "成员2联系电话",
                "成员2出生年月",
                "成员2身份证件类型",
                "成员2身份证件号",
                "成员2民族",
                "成员2政治面貌",
                "成员2健康状况",
                "成员2工作或学习单位",
                "成员2职务"
        );
    }
}