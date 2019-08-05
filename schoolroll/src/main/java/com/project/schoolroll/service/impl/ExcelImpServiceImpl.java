package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.util.excelImp.AbsExcelImp;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.schoolroll.domain.excel.Dic.IMPORT_STUDENTS;
import static com.project.schoolroll.domain.excel.StudentEnum.*;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:20
 * @Version: 1.0
 * @Description:
 */
@Service
@Slf4j
public class ExcelImpServiceImpl extends AbsExcelImp<StudentImport> {
    private final StudentRepository studentRepository;
    private final StudentExpandRepository studentExpandRepository;
    private final StudentPeopleRepository studentPeopleRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public ExcelImpServiceImpl(StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository,
                               StudentExpandRepository studentExpandRepository, RedisTemplate redisTemplate) {
        this.studentRepository = studentRepository;
        this.studentExpandRepository = studentExpandRepository;
        this.studentPeopleRepository = studentPeopleRepository;
        this.redisTemplate = redisTemplate;
    }


    public void studentsExcel07Reader(InputStream inputStream,Class obj ) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentImport> list = reader.readAll(StudentImport.class);
        saveStudent(list);

        //导入成功删除对应键值
        redisTemplate.delete(IMPORT_STUDENTS);
    }


    public void studentsExcel03Reader(InputStream inputStream,Class obj) {
        List<StudentImport> list = ExcelReader(inputStream,obj);
        saveStudent(list);
    }

    @Transactional(rollbackFor = Exception.class)
    void saveStudent(List<StudentImport> list) {
        TimeInterval timer = DateUtil.timer();
        List<StudentPeople> studentPeopleList = CollUtil.newArrayList();
        List<Student> studentList = CollUtil.newArrayList();
        List<StudentExpand> studentExpandList = CollUtil.newArrayList();
        //查询全部学生信息

        list.parallelStream()
                .filter(Objects::nonNull)
                //去除导入数据的空格
                .map(BeanUtil::trimStrFields)
                .forEach(studentImport -> {

                    Optional<Student> studentOptional = studentRepository.findById(studentImport.getStuId());
                    if (studentOptional.isPresent()) {
                        //存在需要覆盖
                        Student student = studentOptional.get();
                        BeanUtil.copyProperties(studentImport, student);
                        studentList.add(student);
                        studentPeopleRepository.findById(student.getPeopleId())
                                .ifPresent(studentPeople -> {
                                    BeanUtil.copyProperties(studentImport, studentPeople);
                                    setStuBirthDate(studentImport.getStuIDCard(), studentPeople);
                                    studentPeopleList.add(studentPeople);
                                });
                    } else {
                        StudentPeople studentPeople = new StudentPeople();
                        BeanUtil.copyProperties(studentImport, studentPeople);
                        String peopleId = IdUtil.fastSimpleUUID();
                        studentPeople.setPeopleId(peopleId);
                        setStuBirthDate(studentImport.getStuIDCard(), studentPeople);
                        studentPeopleList.add(studentPeople);
                        Student student = new Student();
                        BeanUtil.copyProperties(studentImport, student);
                        student.setPeopleId(peopleId);
                        studentList.add(student);
                    }

                    if (StrUtil.isNotBlank(studentImport.getStuEmail())) {
                        setStudentExpandValue(studentImport.getStuId(), stuEmail.name(), studentImport.getFamilyAddress(), stuEmail.getName(), studentExpandList);
                    }

                    if (StrUtil.isNotBlank(studentImport.getFamilyAddress())) {
                        setStudentExpandValue(studentImport.getStuId(), familyAddress.name(), studentImport.getFamilyAddress(), familyAddress.getName(), studentExpandList);
                    }
                    log.info("thread id : [{}] , thread name : [{}]", Thread.currentThread().getId(), Thread.currentThread().getName());
                });

        studentRepository.saveAll(studentList);

        studentPeopleRepository.saveAll(studentPeopleList);

        studentExpandRepository.saveAll(studentExpandList);

        //花费毫秒数
        log.debug("花费毫秒数 : [{}]", timer.interval());
        //花费分钟数
        log.debug("花费分钟数 : [{}]", timer.intervalMinute());
        //返回花费时间，并重置开始时间
        log.debug("返回花费时间,并重置开始时间 : [{}]", timer.intervalRestart());
    }

    private void setStudentExpandValue(String stuId, String studentExpandName, String studentExpandValue, String expandExplain, List<StudentExpand> studentExpandList) {
        List<StudentExpand> expandList = studentExpandRepository.findAllByStuIdAndExpandName(stuId, studentExpandName);
        StudentExpand studentExpand;
        if (!expandList.isEmpty()) {
            studentExpand = expandList.get(0);
        } else {
            studentExpand = new StudentExpand();
            studentExpand.setExpandId(IdUtil.fastSimpleUUID());
            studentExpand.setStuId(stuId);
            studentExpand.setExpandName(studentExpandName);
            studentExpand.setExpandExplain(expandExplain);
        }
        studentExpand.setExpandValue(studentExpandValue);
        studentExpandList.add(studentExpand);
    }

//    private RowHandler createRowHandler() {
//        return new RowHandler() {
//            @Override
//            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
//                if (rowIndex > 0) {
//                    rowlist.stream()
//                            .filter(Objects::nonNull)
//                            .filter(o -> StrUtil.isNotBlank(String.valueOf(o)))
//                            .forEachOrdered(o -> {
//                                log.debug("Object : [{}], [{}]", o.getClass(), o);
//                            });
//                }
//            }
//        };
//    }

    private void setStuBirthDate(String stuIDCard, StudentPeople studentPeople) {
        if (IdcardUtil.isValidCard(StrUtil.trim(stuIDCard))) {
            //验证是合法身份证信息获取生日信息
            studentPeople.setStuBirthDate(IdcardUtil.getBirth(stuIDCard));
        }
    }

    @Override
    public void setHeaderAlias(@NonNull ExcelReader reader) {

        reader.addHeaderAlias(stuName.getName(), stuName.name());
        reader.addHeaderAlias(gender.getName(), gender.name());
        reader.addHeaderAlias(stuBirthDate.getName(), stuBirthDate.name());
        reader.addHeaderAlias(stuCardType.getName(), stuCardType.name());
        reader.addHeaderAlias(stuIDCard.getName(), stuIDCard.name());
        reader.addHeaderAlias(namePinYin.getName(), namePinYin.name());
        reader.addHeaderAlias(className.getName(), className.name());
        reader.addHeaderAlias(stuId.getName(), stuId.name());
        reader.addHeaderAlias(studentCategory.getName(), studentCategory.name());
        reader.addHeaderAlias(learningModality.getName(), learningModality.name());
        reader.addHeaderAlias(waysEnrollment.getName(), waysEnrollment.name());
        reader.addHeaderAlias(waysStudy.getName(), waysStudy.name());
        reader.addHeaderAlias(nationality.getName(), nationality.name());
        reader.addHeaderAlias(nationalityType.getName(), nationalityType.name());
        reader.addHeaderAlias(marriage.getName(), marriage.name());
        reader.addHeaderAlias(trainSpace.getName(), trainSpace.name());
        reader.addHeaderAlias(isImmigrantChildren.getName(), isImmigrantChildren.name());
        reader.addHeaderAlias(studentFormAdministrativeCode.getName(), studentFormAdministrativeCode.name());
        reader.addHeaderAlias(birthplaceAdministrativeCode.getName(), birthplaceAdministrativeCode.name());
        reader.addHeaderAlias(nativePlaceFormAdministrativeCode.getName(), nativePlaceFormAdministrativeCode.name());
        reader.addHeaderAlias(householdAddressDetails.getName(), householdAddressDetails.name());
        reader.addHeaderAlias(localPoliceStation.getName(), localPoliceStation.name());
        reader.addHeaderAlias(householdAdministrativeCode.getName(), householdAdministrativeCode.name());
        reader.addHeaderAlias(householdType.getName(), householdType.name());
        reader.addHeaderAlias(studentHabitationType.getName(), studentHabitationType.name());
        reader.addHeaderAlias(enrollmentDate.getName(), enrollmentDate.name());
        reader.addHeaderAlias(specialtyName.getName(), specialtyName.name());
        reader.addHeaderAlias(educationalSystem.getName(), educationalSystem.name());
        reader.addHeaderAlias(nation.getName(), nation.name());
        reader.addHeaderAlias(politicalStatus.getName(), politicalStatus.name());
        reader.addHeaderAlias(healthCondition.getName(), healthCondition.name());
        reader.addHeaderAlias(studentSource.getName(), studentSource.name());
        reader.addHeaderAlias(recruit.getName(), recruit.name());
        reader.addHeaderAlias(stuPhone.getName(), stuPhone.name());
        reader.addHeaderAlias(isDestituteFamily.getName(), isDestituteFamily.name());
        reader.addHeaderAlias(studentRecruitingWays.getName(), studentRecruitingWays.name());
        reader.addHeaderAlias(jointRecruitmentCooperationType.getName(), jointRecruitmentCooperationType.name());
        reader.addHeaderAlias(entranceCertificateNumber.getName(), entranceCertificateNumber.name());
        reader.addHeaderAlias(candidateNumber.getName(), candidateNumber.name());
        reader.addHeaderAlias(totalExaminationAchievement.getName(), totalExaminationAchievement.name());
        reader.addHeaderAlias(jointRecruitmentCooperationStyle.getName(), jointRecruitmentCooperationStyle.name());
        reader.addHeaderAlias(jointRecruitmentCooperationSchoolCode.getName(), jointRecruitmentCooperationSchoolCode.name());
        reader.addHeaderAlias(offCampusTeachingAddress.getName(), offCampusTeachingAddress.name());
        reader.addHeaderAlias(stageByStageEducationType.getName(), stageByStageEducationType.name());
        reader.addHeaderAlias(englishName.getName(), englishName.name());
        reader.addHeaderAlias(stuEmail.getName(), stuEmail.name());
        reader.addHeaderAlias(familyAddress.getName(), familyAddress.name());
        reader.addHeaderAlias(familyPostalCode.getName(), familyPostalCode.name());
        reader.addHeaderAlias(familyPhone.getName(), familyPhone.name());
        reader.addHeaderAlias(family1Name.getName(), family1Name.name());
        reader.addHeaderAlias(family1Relationship.getName(), family1Relationship.name());
        reader.addHeaderAlias(family1IsGuardian.getName(), family1IsGuardian.name());
        reader.addHeaderAlias(family1Phone.getName(), family1Phone.name());
        reader.addHeaderAlias(family1BirthDate.getName(), family1BirthDate.name());
        reader.addHeaderAlias(family1CardType.getName(), family1CardType.name());
        reader.addHeaderAlias(family1IDCard.getName(), family1IDCard.name());
        reader.addHeaderAlias(family1Nation.getName(), family1Nation.name());
        reader.addHeaderAlias(family1PoliticalStatus.getName(), family1PoliticalStatus.name());
        reader.addHeaderAlias(family1HealthCondition.getName(), family1HealthCondition.name());
        reader.addHeaderAlias(family1CompanyOrganization.getName(), family1CompanyOrganization.name());
        reader.addHeaderAlias(family1Position.getName(), family1Position.name());
        reader.addHeaderAlias(family2Name.getName(), family2Name.name());
        reader.addHeaderAlias(family2Relationship.getName(), family2Relationship.name());
        reader.addHeaderAlias(family2IsGuardian.getName(), family2IsGuardian.name());
        reader.addHeaderAlias(family2Phone.getName(), family2Phone.name());
        reader.addHeaderAlias(family2BirthDate.getName(), family2BirthDate.name());
        reader.addHeaderAlias(family2CardType.getName(), family2CardType.name());
        reader.addHeaderAlias(family2IDCard.getName(), family2IDCard.name());
        reader.addHeaderAlias(family2Nation.getName(), family2Nation.name());
        reader.addHeaderAlias(family2PoliticalStatus.getName(), family2PoliticalStatus.name());
        reader.addHeaderAlias(family2HealthCondition.getName(), family2HealthCondition.name());
        reader.addHeaderAlias(family2CompanyOrganization.getName(), family2CompanyOrganization.name());
        reader.addHeaderAlias(family2Position.getName(), family2Position.name());
        reader.addHeaderAlias(remark.getName(), remark.name());

//        //学号
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(0, 0)), "stuId");
//        //学生姓名
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(1, 0)), "stuName");
//        //学生性别
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(2, 0)), "gender");
//        //身份证号
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(3, 0)), "stuIDCard");
//        //入学年度
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(4, 0)), "enrollmentDate");
//        //招生批次
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(5, 0)), "recruitBatch");
//        //专业名称
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(6, 0)), "specialtyName");
//        //政治面貌
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(7, 0)), "politicalStatus");
//        //民族
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(8, 0)), "nation");
//        //学制及计划类别
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(9, 0)), "educationalSystem");
//        //中招考试成绩/考试成绩
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(10, 0)), "totalExaminationAchievement");
//        //准考证号
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(11, 0)), "entranceCertificateNumber");
//        //考试市县 地区
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(12, 0)), "examinationArea");
//        //毕业学校
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(13, 0)), "school");
//        //就读方式 走读,住校,借宿,其它
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(14, 0)), "waysStudy");
//        //报道日期
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(15, 0)), "arrivalDate");
//        //家长姓名
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(16, 0)), "familyName");
//        //家长电话
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(17, 0)), "familyPhone");
//        //工作单位
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(18, 0)), "companyOrganization");
//        //学生电话
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(19, 0)), "stuPhone");
//        //学生邮箱
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(20, 0)), "stuEmail");
//        //家庭住址
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(21, 0)), "familyAddress");
//        //备注
//        reader.addHeaderAlias(String.valueOf(reader.readCellValue(22, 0)), "remark");

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
