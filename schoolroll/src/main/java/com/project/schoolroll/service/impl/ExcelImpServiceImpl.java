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
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.excelImp.AbsExcelImp;
import com.project.schoolroll.domain.Family;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.domain.excel.StudentImport;
import com.project.schoolroll.repository.FamilyRepository;
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
import java.util.concurrent.TimeUnit;

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
    private final FamilyRepository familyRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public ExcelImpServiceImpl(StudentRepository studentRepository, StudentPeopleRepository studentPeopleRepository,
                               FamilyRepository familyRepository,
                               StudentExpandRepository studentExpandRepository, RedisTemplate redisTemplate) {
        this.studentRepository = studentRepository;
        this.studentExpandRepository = studentExpandRepository;
        this.studentPeopleRepository = studentPeopleRepository;
        this.redisTemplate = redisTemplate;
        this.familyRepository = familyRepository;
    }


    public void studentsExcel07Reader(InputStream inputStream, Class obj, String centerAreaId, String createUser) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentImport> list = reader.readAll(StudentImport.class);
        saveStudent(list, centerAreaId, createUser);

        //导入成功删除对应键值
        deleteKey();
    }

    public void deleteKey(){
        redisTemplate.delete(IMPORT_STUDENTS);
    }
    public void checkoutKey(){
        MyAssert.isTrue(redisTemplate.hasKey(IMPORT_STUDENTS), DefineCode.ERR0013, "有人操作，请稍后再试!");
    }
    public void setStudentKey(){
        redisTemplate.opsForValue().set(IMPORT_STUDENTS, DateUtil.now(), 30L, TimeUnit.MINUTES);
    }


    public void studentsExcel03Reader(InputStream inputStream,Class obj, String centerAreaId, String createUser) {
//        List<StudentImport> list = ExcelReader(inputStream,obj);
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentImport> list = reader.readAll(StudentImport.class);
        saveStudent(list, centerAreaId, createUser);
        deleteKey();
    }

    @Transactional(rollbackFor = Exception.class)
    void saveStudent(List<StudentImport> list, String centerAreaId, String createUser) {
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "导入的数据为空");
        TimeInterval timer = DateUtil.timer();
        List<StudentPeople> studentPeopleList = CollUtil.newArrayList();
        List<Student> studentList = CollUtil.newArrayList();
        List<StudentExpand> studentExpandList = CollUtil.newArrayList();
        List<Family> familyList = CollUtil.newArrayList();
        //查询全部学生信息

        list.parallelStream()
                .filter(Objects::nonNull)
                //去除导入数据的空格
                .map(BeanUtil::trimStrFields)
                .forEach(studentImport -> {

                    Optional<Student> studentOptional = studentRepository.findById(studentImport.getStudentId());
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
                        studentPeople.setCenterAreaId(centerAreaId);
                        studentPeople.setCreateUser(createUser);
                        studentPeople.setUpdateUser(createUser);
                        studentPeopleList.add(studentPeople);
                        Student student = new Student();
                        BeanUtil.copyProperties(studentImport, student);
                        student.setPeopleId(peopleId);
                        student.setCenterAreaId(centerAreaId);
                        student.setCreateUser(createUser);
                        student.setUpdateUser(createUser);
                        studentList.add(student);
                    }
                    //将学生信息数据,列数据转换为行数据，设置为List集合列
                    setStudentExpandData(studentImport, studentExpandList, centerAreaId, createUser);
                    //设置学生信息家庭成员信息
                    setFamilyData(studentImport, familyList, centerAreaId);
                    log.info("thread id : [{}] , thread name : [{}]", Thread.currentThread().getId(), Thread.currentThread().getName());
                });

        studentRepository.saveAll(studentList);

        studentPeopleRepository.saveAll(studentPeopleList);

        studentExpandRepository.saveAll(studentExpandList);

        familyRepository.saveAll(familyList);

        //花费毫秒数
        log.debug("花费毫秒数 : [{}]", timer.interval());
        //花费分钟数
        log.debug("花费分钟数 : [{}]", timer.intervalMinute());
        //返回花费时间，并重置开始时间
        log.debug("返回花费时间,并重置开始时间 : [{}]", timer.intervalRestart());
    }

    private void setFamilyData(StudentImport studentImport, List<Family> familyList, String centerAreaId){
        if (StrUtil.isNotBlank(studentImport.getStudentId()) && StrUtil.isNotBlank(studentImport.getFamily1Name())){
            familyData1(studentImport, familyList, centerAreaId);
        }
        if (StrUtil.isNotBlank(studentImport.getStudentId()) && StrUtil.isNotBlank(studentImport.getFamily1Name())){
            familyData2(studentImport, familyList, centerAreaId);
        }
    }

    private void familyData1(StudentImport studentImport, List<Family> familyList, String centerAreaId){
        List<Family> list = familyRepository.findByStudentIdAndFamilyName(studentImport.getStudentId(), studentImport.getFamily1Name());
        Family family;
        if (!list.isEmpty()){
            family = list.get(0);
            setFamilyData1(studentImport, family);
        }else {
            family = new Family();
            setFamilyData1(studentImport, family);
            family.setStudentId(studentImport.getStudentId());
            family.setFamilyId(IdUtil.fastSimpleUUID());
            family.setCenterAreaId(centerAreaId);
        }
        familyList.add(family);
    }
    private void familyData2(StudentImport studentImport, List<Family> familyList, String centerAreaId){
        List<Family> list = familyRepository.findByStudentIdAndFamilyName(studentImport.getStudentId(), studentImport.getFamily2Name());
        Family family;
        if (!list.isEmpty()){
            family = list.get(0);
            setFamilyData2(studentImport, family);
        }else {
            family = new Family();
            setFamilyData2(studentImport, family);
            family.setStudentId(studentImport.getStudentId());
            family.setFamilyId(IdUtil.fastSimpleUUID());
            family.setCenterAreaId(centerAreaId);
        }
        familyList.add(family);
    }
    private void setFamilyData1(StudentImport studentImport, Family family){
        if (StrUtil.isNotBlank(studentImport.getFamily1Name())) {
            family.setFamilyName(studentImport.getFamily1Name());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1Relationship())) {
            family.setFamilyRelationship(studentImport.getFamily1Relationship());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1Phone())) {
            family.setFamilyPhone(studentImport.getFamily1Phone());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1IsGuardian())) {
            family.setIsGuardian(studentImport.getFamily1IsGuardian());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1CardType())) {
            family.setFamilyCardType(studentImport.getFamily1CardType());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1IDCard())) {
            family.setFamilyIDCard(studentImport.getFamily1IDCard());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1BirthDate())) {
            family.setFamilyBirthDate(studentImport.getFamily1BirthDate());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1HealthCondition())) {
            family.setFamilyHealthCondition(studentImport.getFamily1HealthCondition());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1CompanyOrganization())) {
            family.setFamilyCompanyOrganization(studentImport.getFamily1CompanyOrganization());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1PoliticalStatus())) {
            family.setFamilyPoliticalStatus(studentImport.getFamily1PoliticalStatus());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1Nation())) {
            family.setFamilyNation(studentImport.getFamily1Nation());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily1Position())) {
            family.setFamilyPosition(studentImport.getFamily1Position());
        }
    }

    private void setFamilyData2(StudentImport studentImport, Family family){
        if (StrUtil.isNotBlank(studentImport.getFamily2Name())) {
            family.setFamilyName(studentImport.getFamily2Name());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2Relationship())) {
            family.setFamilyRelationship(studentImport.getFamily2Relationship());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2Phone())) {
            family.setFamilyPhone(studentImport.getFamily2Phone());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2IsGuardian())) {
            family.setIsGuardian(studentImport.getFamily2IsGuardian());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2CardType())) {
            family.setFamilyCardType(studentImport.getFamily2CardType());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2IDCard())) {
            family.setFamilyIDCard(studentImport.getFamily2IDCard());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2BirthDate())) {
            family.setFamilyBirthDate(studentImport.getFamily2BirthDate());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2HealthCondition())) {
            family.setFamilyHealthCondition(studentImport.getFamily2HealthCondition());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2CompanyOrganization())) {
            family.setFamilyCompanyOrganization(studentImport.getFamily2CompanyOrganization());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2PoliticalStatus())) {
            family.setFamilyPoliticalStatus(studentImport.getFamily2PoliticalStatus());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2Nation())) {
            family.setFamilyNation(studentImport.getFamily2Nation());
        }
        if (StrUtil.isNotBlank(studentImport.getFamily2Position())) {
            family.setFamilyPosition(studentImport.getFamily2Position());
        }
    }

    private void setStudentExpandData(StudentImport studentImport, List<StudentExpand> studentExpandList, String centerAreaId, String createUser){
        //学生邮箱
        if (StrUtil.isNotBlank(studentImport.getStuEmail())) {
            setStudentExpandValue(studentImport.getStudentId(), stuEmail.name(), studentImport.getStuEmail(), stuEmail.getName(), studentExpandList, centerAreaId, createUser);
        }
        //家庭地址
        if (StrUtil.isNotBlank(studentImport.getFamilyAddress())) {
            setStudentExpandValue(studentImport.getStudentId(), familyAddress.name(), studentImport.getFamilyAddress(), familyAddress.getName(), studentExpandList, centerAreaId, createUser);
        }
        //乘火车区间
        if(StrUtil.isNotBlank(studentImport.getTrainSpace())){
            setStudentExpandValue(studentImport.getStudentId(), trainSpace.name(), studentImport.getTrainSpace(), trainSpace.getName(), studentExpandList, centerAreaId, createUser);
        }
        //考生特长
        if (StrUtil.isNotBlank(studentImport.getStudentSpeciality())) {
            setStudentExpandValue(studentImport.getStudentId(), studentSpeciality.name(), studentImport.getStudentSpeciality(), studentSpeciality.getName(), studentExpandList, centerAreaId, createUser);
        }
        //学生来源
        if (StrUtil.isNotBlank(studentImport.getStudentSource())) {
            setStudentExpandValue(studentImport.getStudentId(), studentSource.name(), studentImport.getStudentSource(), studentSource.getName(), studentExpandList, centerAreaId, createUser);
        }
        //招生方式
        if(StrUtil.isNotBlank(studentImport.getStudentRecruitingWays())){
            setStudentExpandValue(studentImport.getStudentId(), studentRecruitingWays.name(), studentImport.getStudentRecruitingWays(), studentRecruitingWays.getName(), studentExpandList, centerAreaId, createUser);
        }
        //考生既往病史
        if (StrUtil.isNotBlank(studentImport.getStudentMedicalHistory())) {
            setStudentExpandValue(studentImport.getStudentId(), studentMedicalHistory.name(), studentImport.getStudentMedicalHistory(), studentMedicalHistory.getName(), studentExpandList, centerAreaId, createUser);
        }
        //学生居住地类型
        if (StrUtil.isNotBlank(studentImport.getStudentHabitationType())) {
            setStudentExpandValue(studentImport.getStudentId(), studentHabitationType.name(), studentImport.getStudentHabitationType(), studentHabitationType.getName(), studentExpandList, centerAreaId, createUser);
        }
        //生源地行政区划码
        if(StrUtil.isNotBlank(studentImport.getStudentFormAdministrativeCode())){
            setStudentExpandValue(studentImport.getStudentId(), studentFormAdministrativeCode.name(), studentImport.getStudentFormAdministrativeCode(), studentFormAdministrativeCode.getName(), studentExpandList, centerAreaId, createUser);
        }
        //毕业学校
        if (StrUtil.isNotBlank(studentImport.getSchool())) {
            setStudentExpandValue(studentImport.getStudentId(), school.name(), studentImport.getSchool(), school.getName(), studentExpandList, centerAreaId, createUser);
        }
        //招生对象
        if (StrUtil.isNotBlank(studentImport.getRecruit())) {
            setStudentExpandValue(studentImport.getStudentId(), recruit.name(), studentImport.getRecruit(), recruit.getName(), studentExpandList, centerAreaId, createUser);
        }
        //校外教学点
        if(StrUtil.isNotBlank(studentImport.getOffCampusTeachingAddress())){
            setStudentExpandValue(studentImport.getStudentId(), offCampusTeachingAddress.name(), studentImport.getOffCampusTeachingAddress(), offCampusTeachingAddress.getName(), studentExpandList, centerAreaId, createUser);
        }
        //籍贯行政区划码
        if (StrUtil.isNotBlank(studentImport.getNativePlaceFormAdministrativeCode())) {
            setStudentExpandValue(studentImport.getStudentId(), nativePlaceFormAdministrativeCode.name(), studentImport.getNativePlaceFormAdministrativeCode(), nativePlaceFormAdministrativeCode.getName(), studentExpandList, centerAreaId, createUser);
        }
        //所属派出所
        if (StrUtil.isNotBlank(studentImport.getLocalPoliceStation())) {
            setStudentExpandValue(studentImport.getStudentId(), localPoliceStation.name(), studentImport.getLocalPoliceStation(), localPoliceStation.getName(), studentExpandList, centerAreaId, createUser);
        }
        //联招合作类型
        if(StrUtil.isNotBlank(studentImport.getJointRecruitmentCooperationType())){
            setStudentExpandValue(studentImport.getStudentId(), jointRecruitmentCooperationType.name(), studentImport.getJointRecruitmentCooperationType(), jointRecruitmentCooperationType.getName(), studentExpandList, centerAreaId, createUser);
        }
        //联招合作办学形式
        if(StrUtil.isNotBlank(studentImport.getJointRecruitmentCooperationStyle())){
            setStudentExpandValue(studentImport.getStudentId(), jointRecruitmentCooperationStyle.name(), studentImport.getJointRecruitmentCooperationStyle(), jointRecruitmentCooperationStyle.getName(), studentExpandList, centerAreaId, createUser);
        }
        //联招合作学校代码
        if(StrUtil.isNotBlank(studentImport.getJointRecruitmentCooperationSchoolCode())){
            setStudentExpandValue(studentImport.getStudentId(), jointRecruitmentCooperationSchoolCode.name(), studentImport.getJointRecruitmentCooperationSchoolCode(), jointRecruitmentCooperationSchoolCode.getName(), studentExpandList, centerAreaId, createUser);
        }
        //是否建档立卡贫困户
        if(StrUtil.isNotBlank(studentImport.getIsDestituteFamily())){
            setStudentExpandValue(studentImport.getStudentId(), isDestituteFamily.name(), studentImport.getIsDestituteFamily(), isDestituteFamily.getName(), studentExpandList, centerAreaId, createUser);
        }
        //户口所在地行政区划码
        if(StrUtil.isNotBlank(studentImport.getHouseholdAdministrativeCode())){
            setStudentExpandValue(studentImport.getStudentId(), householdAdministrativeCode.name(), studentImport.getHouseholdAdministrativeCode(), householdAdministrativeCode.getName(), studentExpandList, centerAreaId, createUser);
        }
        //户口所在地区县以下详细地址
        if(StrUtil.isNotBlank(studentImport.getHouseholdAddressDetails())){
            setStudentExpandValue(studentImport.getStudentId(), householdAddressDetails.name(), studentImport.getHouseholdAddressDetails(), householdAddressDetails.getName(), studentExpandList, centerAreaId, createUser);
        }
        //体检结论
        if(StrUtil.isNotBlank(studentImport.getHealthReport())){
            setStudentExpandValue(studentImport.getStudentId(), healthReport.name(), studentImport.getHealthReport(), healthReport.getName(), studentExpandList, centerAreaId, createUser);
        }
        //健康状态
        if(StrUtil.isNotBlank(studentImport.getHealthCondition())){
            setStudentExpandValue(studentImport.getStudentId(), healthCondition.name(), studentImport.getHealthCondition(), healthCondition.getName(), studentExpandList, centerAreaId, createUser);
        }
        //家庭邮政编码
        if(StrUtil.isNotBlank(studentImport.getFamilyPostalCode())){
            setStudentExpandValue(studentImport.getStudentId(), familyPostalCode.name(), studentImport.getFamilyPostalCode(), familyPostalCode.getName(), studentExpandList, centerAreaId, createUser);
        }
        //家庭电话
        if(StrUtil.isNotBlank(studentImport.getFamilyPhone())){
            setStudentExpandValue(studentImport.getStudentId(), familyPhone.name(), studentImport.getFamilyPhone(), familyPhone.getName(), studentExpandList, centerAreaId, createUser);
        }
        //英文姓名
        if(StrUtil.isNotBlank(studentImport.getEnglishName())){
            setStudentExpandValue(studentImport.getStudentId(), englishName.name(), studentImport.getEnglishName(), englishName.getName(), studentExpandList, centerAreaId, createUser);
        }
        //出生地行政区划码
        if(StrUtil.isNotBlank(studentImport.getBirthplaceAdministrativeCode())){
            setStudentExpandValue(studentImport.getStudentId(), birthplaceAdministrativeCode.name(), studentImport.getBirthplaceAdministrativeCode(), birthplaceAdministrativeCode.getName(), studentExpandList, centerAreaId, createUser);
        }
    }

    private void setStudentExpandValue(String studentId, String studentExpandName, String studentExpandValue, String expandExplain,
                                       List<StudentExpand> studentExpandList, String centerAreaId, String createUser) {
        List<StudentExpand> expandList = studentExpandRepository.findAllByStudentIdAndExpandName(studentId, studentExpandName);
        StudentExpand studentExpand;
        if (!expandList.isEmpty()) {
            studentExpand = expandList.get(0);
        } else {
            studentExpand = new StudentExpand();
            studentExpand.setExpandId(IdUtil.fastSimpleUUID());
            studentExpand.setStudentId(studentId);
            studentExpand.setExpandName(studentExpandName);
            studentExpand.setExpandExplain(expandExplain);
            studentExpand.setCenterAreaId(centerAreaId);
            studentExpand.setCreateUser(createUser);
            studentExpand.setUpdateUser(createUser);
        }
        studentExpand.setExpandValue(studentExpandValue);
        studentExpandList.add(studentExpand);
    }

    private void setStuBirthDate(String stuIDCard, StudentPeople studentPeople) {
        if (IdcardUtil.isValidCard(StrUtil.trim(stuIDCard))) {
            //验证是合法身份证信息获取生日信息
            studentPeople.setStuBirthDate(IdcardUtil.getBirth(stuIDCard));
        }
    }

    @Override
    public void setHeaderAlias(@NonNull ExcelReader reader) {

        reader.addHeaderAlias(studentName.getName(), studentName.name());
        reader.addHeaderAlias(gender.getName(), gender.name());
        reader.addHeaderAlias(stuBirthDate.getName(), stuBirthDate.name());
        reader.addHeaderAlias(stuCardType.getName(), stuCardType.name());
        reader.addHeaderAlias(stuIDCard.getName(), stuIDCard.name());
        reader.addHeaderAlias(namePinYin.getName(), namePinYin.name());
        reader.addHeaderAlias(className.getName(), className.name());
        reader.addHeaderAlias(studentId.getName(), studentId.name());
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
    }
}