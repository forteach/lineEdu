package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.project.schoolroll.domain.Family;
import com.project.schoolroll.domain.excel.StudentExport;
import com.project.schoolroll.repository.*;
import com.project.schoolroll.repository.dto.StudentExportDto;
import com.project.schoolroll.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

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
    public ExportServiceImpl(StudentExpandRepository studentExpandRepository, StudentRepository studentRepository, ViewStudentExportRepository viewStudentExportRepository,
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
    public List<List<String>> exportStudents() {
        List<List<String>> list = studentRepository.findByIsValidatedEqualsDto()
                .parallelStream().filter(Objects::nonNull)
                .map(this::setStudentExport)
                .collect(toList());
        list.add(0, setExportTemplate());
        return list;
    }

    private List<String> setStudentExport(StudentExportDto dto) {
        StudentExport studentExport = new StudentExport();
        //家庭成员
        setFamily(studentExport, dto.getStudentId());
        //扩展信息
        setStudentExpand(studentExport, dto.getStudentId());
        //学生信息
        setStudentPeopleData(dto, studentExport);
        //转换消息
        return setListData(studentExport);
    }

    private List<String> setListData(StudentExport studentExport){
        Map<String, Object> map = BeanUtil.beanToMap(studentExport);
        return map.values().stream().map(o -> {
            if (o == null){
                return "";
            }else {
                return String.valueOf(o);
            }
        }).collect(toList());
    }
    private void setStudentPeopleData(StudentExportDto dto, StudentExport studentExport) {
        studentExport.setStudentName(dto.getStudentName());
        studentExport.setGender(dto.getGender());
        studentExport.setStuBirthDate(dto.getStuBirthDate());
        studentExport.setStuCardType(dto.getStuCardType());
        studentExport.setStuIDCard(dto.getStuIDCard());
        studentExport.setNamePinYin(dto.getNamePinYin());
        studentExport.setClassName(dto.getClassName());
        studentExport.setStudentId(dto.getStudentId());
        studentExport.setStudentCategory(dto.getStudentCategory());
        studentExport.setLearningModality(dto.getLearningModality());
        studentExport.setWaysEnrollment(dto.getWaysEnrollment());
        studentExport.setWaysStudy(dto.getWaysStudy());
        studentExport.setNationality(dto.getNationality());
        studentExport.setNationalityType(dto.getNationalityType());
        studentExport.setMarriage(dto.getMarriage());
        studentExport.setIsImmigrantChildren(dto.getIsImmigrantChildren());
        studentExport.setHouseholdType(dto.getHouseholdType());
        studentExport.setSpecialtyName(dto.getSpecialtyName());
        studentExport.setEducationalSystem(dto.getEducationalSystem());
        studentExport.setNation(dto.getNation());
        studentExport.setPoliticalStatus(dto.getPoliticalStatus());
        studentExport.setStuPhone(dto.getStuPhone());
        studentExport.setEntranceCertificateNumber(dto.getEntranceCertificateNumber());
        studentExport.setTotalExaminationAchievement(dto.getTotalExaminationAchievement());
        studentExport.setEnrollmentDate(dto.getEnrollmentDate());
    }

    private void setStudentExpand(StudentExport studentExpand, String studentId) {
        Map<String, String> map = MapUtil.newHashMap();
        studentExpandRepository.findByIsValidatedEqualsAndStudentId(studentId)
                .parallelStream()
                .forEach(dto -> {
                    map.put(dto.getExpandName(), dto.getExpandValue());
                });
        BeanUtil.copyProperties(map, studentExpand);
    }

    private void setFamily(StudentExport studentExpand, String studentId) {
        List<Family> familyList = familyRepository.findAllByIsValidatedEqualsAndStudentId(TAKE_EFFECT_OPEN, studentId);
        if (!familyList.isEmpty()) {
            Family family = familyList.get(0);
            setStudentFamily1Data(studentExpand, family);
            if (familyList.size() > 1) {
                setStudentFamily2Data(studentExpand, familyList.get(1));
            }
        }
    }

    private void setStudentFamily2Data(StudentExport studentExpand, Family family) {
        if (StrUtil.isNotBlank(family.getFamilyName())) {
            studentExpand.setFamily2Name(family.getFamilyName());
        }
        if (StrUtil.isNotBlank(family.getFamilyRelationship())) {
            studentExpand.setFamily2Relationship(family.getFamilyRelationship());
        }
        if (StrUtil.isNotBlank(family.getIsGuardian())) {
            studentExpand.setFamily2IsGuardian(family.getIsGuardian());
        }
        if (StrUtil.isNotBlank(family.getFamilyPhone())) {
            studentExpand.setFamily2Phone(family.getFamilyPhone());
        }
        if (StrUtil.isNotBlank(family.getFamilyBirthDate())) {
            studentExpand.setFamily2BirthDate(family.getFamilyBirthDate());
        }
        if (StrUtil.isNotBlank(family.getFamilyCardType())) {
            studentExpand.setFamily2CardType(family.getFamilyCardType());
        }
        if (StrUtil.isNotBlank(family.getFamilyIDCard())) {
            studentExpand.setFamily2IDCard(family.getFamilyIDCard());
        }
        if (StrUtil.isNotBlank(family.getFamilyNation())) {
            studentExpand.setFamily2Nation(family.getFamilyNation());
        }
        if (StrUtil.isNotBlank(family.getFamilyPoliticalStatus())) {
            studentExpand.setFamily2HealthCondition(family.getFamilyHealthCondition());
        }
        if (StrUtil.isNotBlank(family.getFamilyCompanyOrganization())) {
            studentExpand.setFamily2CompanyOrganization(family.getFamilyCompanyOrganization());
        }
        if (StrUtil.isNotBlank(family.getFamilyPosition())) {
            studentExpand.setFamily2Position(family.getFamilyPosition());
        }
    }

    private void setStudentFamily1Data(StudentExport studentExpand, Family family) {
        if (StrUtil.isNotBlank(family.getFamilyName())) {
            studentExpand.setFamily1Name(family.getFamilyName());
        }
        if (StrUtil.isNotBlank(family.getFamilyRelationship())) {
            studentExpand.setFamily1Relationship(family.getFamilyRelationship());
        }
        if (StrUtil.isNotBlank(family.getIsGuardian())) {
            studentExpand.setFamily1IsGuardian(family.getIsGuardian());
        }
        if (StrUtil.isNotBlank(family.getFamilyPhone())) {
            studentExpand.setFamily1Phone(family.getFamilyPhone());
        }
        if (StrUtil.isNotBlank(family.getFamilyBirthDate())) {
            studentExpand.setFamily1BirthDate(family.getFamilyBirthDate());
        }
        if (StrUtil.isNotBlank(family.getFamilyCardType())) {
            studentExpand.setFamily1CardType(family.getFamilyCardType());
        }
        if (StrUtil.isNotBlank(family.getFamilyIDCard())) {
            studentExpand.setFamily1IDCard(family.getFamilyIDCard());
        }
        if (StrUtil.isNotBlank(family.getFamilyNation())) {
            studentExpand.setFamily1Nation(family.getFamilyNation());
        }
        if (StrUtil.isNotBlank(family.getFamilyPoliticalStatus())) {
            studentExpand.setFamily1HealthCondition(family.getFamilyHealthCondition());
        }
        if (StrUtil.isNotBlank(family.getFamilyCompanyOrganization())) {
            studentExpand.setFamily1CompanyOrganization(family.getFamilyCompanyOrganization());
        }
        if (StrUtil.isNotBlank(family.getFamilyPosition())) {
            studentExpand.setFamily1Position(family.getFamilyPosition());
        }
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