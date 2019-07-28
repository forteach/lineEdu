package com.project.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
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
import java.util.List;
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
    public ExportServiceImpl(StudentExpandRepository studentExpandRepository, StudentRepository studentRepository,
                             StudentPeopleRepository studentPeopleRepository) {
        this.studentExpandRepository = studentExpandRepository;
        this.studentRepository = studentRepository;
        this.studentPeopleRepository = studentPeopleRepository;
    }

    @Override
    public List<List<String>> exportStudentTemplate() {
        List<List<String>> list = new ArrayList<>();
        list.add(CollUtil.newArrayList(
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
        ));
        return list;
    }

    @Override
    public List<List<?>>  exportStudents() {

        return null;
    }

}
