package com.project.schoolroll.service.online;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.StudentOnLine;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.dto.StudentOnLineDto;
import com.project.schoolroll.repository.online.IStudentOnLineDto;
import com.project.schoolroll.repository.online.StudentOnLineRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.schoolroll.domain.excel.Dic.IMPORT_STUDENTS_ONLINE;
import static com.project.schoolroll.domain.excel.Dic.STUDENT_ON_LINE_IMPORT_STATUS_IMPORT;
import static com.project.schoolroll.domain.excel.StudentEnum.*;
import static java.util.stream.Collectors.toList;

@Service
public class StudentOnLineService {

    private final RedisTemplate redisTemplate;
    private final TbClassService tbClassService;
    private final StudentOnLineRepository studentOnLineRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public StudentOnLineService(StudentOnLineRepository studentOnLineRepository, RedisTemplate redisTemplate, TbClassService tbClassService) {
        this.studentOnLineRepository = studentOnLineRepository;
        this.tbClassService = tbClassService;
        this.redisTemplate = redisTemplate;
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String studentId){
        Optional<StudentOnLine> optional = studentOnLineRepository.findById(studentId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在对应的学生信息");
        studentOnLineRepository.deleteById(studentId);
    }

    @SuppressWarnings(value = "all")
    public void deleteKey() {
        redisTemplate.delete(IMPORT_STUDENTS_ONLINE);
    }

    @SuppressWarnings(value = "all")
    public void checkoutKey() {
        MyAssert.isTrue(redisTemplate.hasKey(IMPORT_STUDENTS_ONLINE), DefineCode.ERR0013, "有人操作，请稍后再试!");
    }

    @SuppressWarnings(value = "all")
    private void setStudentKey() {
        redisTemplate.opsForValue().set(IMPORT_STUDENTS_ONLINE, DateUtil.now(), 30L, TimeUnit.MINUTES);
    }

    /**
     * TODO 统一学习中心的学生导入后不能在另一学习中心导入了
     * */
    public int importStudent(InputStream inputStream, String centerAreaId, String userId) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentOnLine> list = reader.readAll(StudentOnLine.class);

        //校验数据完整性
        checkImportStudentData(list);
        Map<String, List<StudentOnLine>> stringListMap = list.stream()
                .filter(s -> StrUtil.isNotBlank(s.getClassName()))
                .collect(Collectors.groupingBy(StudentOnLine::getClassName));
        //同一班的学生入学时间必须相同。
        stringListMap.forEach((k, v) ->{
            long count = v.stream().filter(Objects::nonNull).map(StudentOnLine::getEnrollmentDate).distinct().count();
            MyAssert.isTrue(count > 1, DefineCode.ERR0010, "同一班的学生入学时间必须相同");
        });
        //判断是否是同一个学习中心，是同一个学习中心则覆盖，否则不予导入并提示
        list.forEach(s -> studentOnLineRepository.findById(s.getStudentId())
                .ifPresent(o -> MyAssert.isFalse(centerAreaId.equals(o.getCenterAreaId()), DefineCode.ERR0010, "您添加的学生: " + s.getStudentId() + " 已经在别的学习中心注册")));
        //设置键操作
        setStudentKey();
        //保存对应班级和学生信息
        saveClassAndStudent(stringListMap, centerAreaId, userId);
        //删除键值操作
        deleteKey();
        return list.size();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveClassAndStudent(Map<String, List<StudentOnLine>> stringListMap, String centerAreaId, String userId){
        //判断班级信息存在则设值，不存在新建
        Map<String, String> classIds = getClass(stringListMap.keySet(), centerAreaId, userId);
        stringListMap.forEach((k, v) -> studentOnLineRepository.saveAll(setClassId(classIds, v, centerAreaId, userId)));
    }

    private void checkImportStudentData(List<StudentOnLine> list){
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "导入数据不存在");
        list.parallelStream().filter(Objects::nonNull).forEach(s -> {
            MyAssert.isTrue(StrUtil.isBlank(s.getStudentName()), DefineCode.ERR0010, "姓名不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getGender()), DefineCode.ERR0010, "性别不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getClassName()), DefineCode.ERR0010, "班级名称不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getStuIDCard()), DefineCode.ERR0010, "身份证件号不能为空");
            MyAssert.isFalse(IdcardUtil.isValidCard(s.getStuIDCard()), DefineCode.ERR0010, "身份证号: ".concat(s.getStuIDCard()).concat("无效"));
            MyAssert.isTrue(StrUtil.isBlank(s.getStuPhone()), DefineCode.ERR0010, "联系电话不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getEnrollmentDate()), DefineCode.ERR0010, "入学年月不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getLearningModality()), DefineCode.ERR0010, "学习形式不能为空");
            MyAssert.isTrue(StrUtil.isBlank(s.getNation()), DefineCode.ERR0010, "民族不能为空");
            MyAssert.isTrue(StrUtil.length(s.getEnrollmentDate()) < 4, DefineCode.ERR0010, "入学时间不正确");
            s.setStudentId(s.getStuIDCard());
            //设置年级/届 取（入学时间前4位） 例如 2019/9 取 2019
            s.setGrade(StrUtil.subWithLength(s.getEnrollmentDate(), 0, 4));
        });
    }

    private Map<String, String> getClass(Set<String> set, String centerAreaId, String userId) {
        return set.stream()
                .filter(Objects::nonNull)
                .map(className -> tbClassService.getClassIdByClassName(className, centerAreaId, userId))
                .collect(Collectors.toMap(TbClasses::getClassName, TbClasses::getClassId));
    }

    private List<StudentOnLine> setClassId(Map<String, String> classIds, List<StudentOnLine> list, String centerAreaId, String userId) {
        return list.stream().map(s -> setStudentOnLine(s, classIds, centerAreaId, userId)).collect(toList());
    }

    private StudentOnLine setStudentOnLine(StudentOnLine studentOnLine, Map<String, String> classIds, String centerAreaId, String userId) {
        studentOnLine.setClassId(classIds.get(studentOnLine.getClassName()));
        studentOnLine.setCenterAreaId(centerAreaId);
        studentOnLine.setCreateUser(userId);
        studentOnLine.setUpdateUser(userId);
        //设置属性字段是导入数据
        studentOnLine.setImportStatus(STUDENT_ON_LINE_IMPORT_STATUS_IMPORT);
        return BeanUtil.trimStrFields(studentOnLine);
    }

    @SuppressWarnings(value = "all")
    private void setHeaderAlias(@NonNull ExcelReader reader) {
        // 新添加字段，对应学号信息
        reader.addHeaderAlias(studentId.getName(), stuId.name());
        //原学号字段应身份证证号
        reader.addHeaderAlias(stuIDCard.getName(), studentId.name());
        reader.addHeaderAlias(studentName.getName(), studentName.name());
        reader.addHeaderAlias(gender.getName(), gender.name());
        reader.addHeaderAlias(stuIDCard.getName(), stuIDCard.name());
        reader.addHeaderAlias(stuPhone.getName(), stuPhone.name());
        reader.addHeaderAlias(className.getName(), className.name());
        reader.addHeaderAlias(enrollmentDate.getName(), enrollmentDate.name());
        reader.addHeaderAlias(nation.getName(), nation.name());
        reader.addHeaderAlias(learningModality.getName(), learningModality.name());
        //专业简称
        reader.addHeaderAlias(specialtyName.getName(), specialtyName.name());
        //学制
        reader.addHeaderAlias(educationalSystem.getName(), educationalSystem.name());
    }

    /** 统计计算班级有效的学生人数*/
    public int countByClassId(String classId) {
        return studentOnLineRepository.countAllByIsValidatedEqualsAndClassId(TAKE_EFFECT_OPEN, classId);
    }

    /** 分页查询有效的全部学生信息*/
    public Page<StudentOnLine> findAllPage(PageRequest request) {
        return studentOnLineRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, request);
    }

    public Page<StudentOnLineDto> findStudentOnLineDto(PageRequest request, String studentName, String isValidated, String centerAreaId, String grade, String specialtyName, String className){
        StringBuilder dataSql = new StringBuilder("select " +
                " s.student_id as student_id, " +
                " s.student_name as student_name, " +
                " s.gender as gender, " +
                " s.stu_id_card as stu_iD_card, " +
                " s.stu_phone as stu_phone, " +
                " s.class_id as class_id, " +
                " s.class_name as class_name, " +
                " s.enrollment_date as enrollment_date, " +
                " s.nation as nation, " +
                " s.learning_modality as learning_modality, " +
                " s.import_status as import_status, " +
                " s.center_area_id as center_area_id, " +
                " lc.center_name as center_name, " +
                " s.c_time as c_time, " +
                " s.specialty_name as specialty_name, " +
                " s.grade as grade, " +
                " s.educational_system as educational_system, " +
                " s.is_validated as is_validated " +
                " from student_on_line as s " +
                " left join learn_center as lc on lc.center_id = s.center_area_id ");
        StringBuilder whereSql = new StringBuilder(" where lc.is_validated = '0' ");
        StringBuilder countSql = new StringBuilder(" select count(1) from student_on_line as s left join learn_center as lc on lc.center_id = s.center_area_id ");
        if (StrUtil.isNotBlank(centerAreaId)){
            whereSql.append(" and lc.center_id = :centerAreaId");
        }
        if (StrUtil.isNotBlank(studentName)){
            whereSql.append(" and s.student_name = :studentName");
        }
        if (StrUtil.isNotBlank(grade)){
            whereSql.append(" and s.grade = :grade");
        }
        if (StrUtil.isNotBlank(specialtyName)){
            whereSql.append(" and s.specialty_name = :specialtyName");
        }
        if (StrUtil.isNotBlank(className)){
            whereSql.append(" and s.class_name = :className");
        }
        if (StrUtil.isNotBlank(isValidated)){
            whereSql.append(" and s.is_validated = :isValidated");
        }
        dataSql.append(whereSql).append(" order by s.class_Id, s.c_time desc ");
        countSql.append(whereSql);
        Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), StudentOnLineDto.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        if (StrUtil.isNotBlank(centerAreaId)){
            dataQuery.setParameter("centerAreaId", centerAreaId);
            countQuery.setParameter("centerAreaId", centerAreaId);
        }
        if (StrUtil.isNotBlank(studentName)){
            dataQuery.setParameter("studentName", studentName);
            countQuery.setParameter("studentName", studentName);
        }
        if (StrUtil.isNotBlank(grade)){
            dataQuery.setParameter("grade", grade);
            countQuery.setParameter("grade", grade);
        }
        if (StrUtil.isNotBlank(specialtyName)){
            dataQuery.setParameter("specialtyName", specialtyName);
            countQuery.setParameter("specialtyName", specialtyName);
        }
        if (StrUtil.isNotBlank(className)){
            dataQuery.setParameter("className", className);
            countQuery.setParameter("className", className);
        }
        if (StrUtil.isNotBlank(isValidated)){
            dataQuery.setParameter("isValidated", isValidated);
            countQuery.setParameter("isValidated", isValidated);
        }
        //设置分页
        dataQuery.setFirstResult((int)request.getOffset());
        dataQuery.setMaxResults(request.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<StudentOnLineDto> content = total > request.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        return new PageImpl<>(content, request, total);
    }


    public List<StudentOnLine> findByStuIDCardAndStudentName(String stuIDCard, String studentName) {
        return studentOnLineRepository.findAllByIsValidatedEqualsAndStuIDCardAndStudentNameOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, stuIDCard, studentName);
    }

    public List<List<String>> findAllStudent(String isValidated){
        List<List<String>> list = exportChange(studentOnLineRepository.findAllIsValidatedDto(isValidated));
        list.add(0, setTitle());
        return list;
    }

    public List<List<String>> findAllStudentByCenterId(String isValidated, String centerId) {
        List<List<String>> list = exportChange(studentOnLineRepository.findAllByIsValidatedAndCenterAreaIdDto(isValidated, centerId));
        list.add(0, setTitle());
        return list;
    }

    private List<List<String>> exportChange(List<IStudentOnLineDto> list){
        return list.stream().filter(Objects::nonNull).map(this::setExport).collect(toList());
    }
    private List<String> setExport(IStudentOnLineDto dto){
        List<String> list = new ArrayList<>();
        list.add(dto.getStuId());
        list.add(dto.getStudentName());
        list.add(dto.getGender());
        list.add(dto.getStuIDCard());
        list.add(dto.getStuPhone());
        list.add(dto.getClassName());
        list.add(dto.getEnrollmentDate());
        list.add(dto.getNation());
        list.add(dto.getLearningModality());
        list.add(dto.getCenterName());
        list.add(TAKE_EFFECT_OPEN.equals(dto.getIsValidated()) ? "是" : "否");
        return list;
    }
    private List<String> setTitle() {
        return CollUtil.newArrayList(studentId.getName(),
                studentName.getName(),
                gender.getName(),
                stuIDCard.getName(),
                stuPhone.getName(),
                className.getName(),
                enrollmentDate.getName(),
                nation.getName(),
                learningModality.getName(),
                "学习中心名称",
                "是否有效"
        );
    }

    public Optional<StudentOnLine> findById(String id){
        return studentOnLineRepository.findById(id);
    }
    /** 根据班级Id查询对应的年级*/
    public String getGradeByClassId(String classId){
        List<StudentOnLine> list = studentOnLineRepository.findAllByClassId(classId);
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0010, "班级对应的级别不存在");
        return list.stream().filter(Objects::nonNull).map(StudentOnLine::getGrade).findFirst().orElseGet(String::new);
    }
}