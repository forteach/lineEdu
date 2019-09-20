package com.project.schoolroll.service.online;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.StudentOnLine;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.online.StudentOnLineDto;
import com.project.schoolroll.repository.online.StudentOnLineRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.base.common.keyword.Dic.USER_PREFIX;
import static com.project.schoolroll.domain.excel.Dic.IMPORT_STUDENTS_ONLINE;
import static com.project.schoolroll.domain.excel.Dic.STUDENT_ON_LINE_IMPORT_STATUS_IMPORT;
import static com.project.schoolroll.domain.excel.StudentEnum.*;

@Service
public class StudentOnLineService {

    private final RedisTemplate redisTemplate;
    private final TbClassService tbClassService;
    private final StudentOnLineRepository studentOnLineRepository;

    @Autowired
    public StudentOnLineService(StudentOnLineRepository studentOnLineRepository, RedisTemplate redisTemplate, TbClassService tbClassService) {
        this.studentOnLineRepository = studentOnLineRepository;
        this.tbClassService = tbClassService;
        this.redisTemplate = redisTemplate;
    }

    public void deleteKey(){
        redisTemplate.delete(IMPORT_STUDENTS_ONLINE);
    }
    public void checkoutKey(){
        MyAssert.isTrue(redisTemplate.hasKey(IMPORT_STUDENTS_ONLINE), DefineCode.ERR0013, "有人操作，请稍后再试!");
    }
    private void setStudentKey(){
        redisTemplate.opsForValue().set(IMPORT_STUDENTS_ONLINE, DateUtil.now(), 30L, TimeUnit.MINUTES);
    }

    @Transactional(rollbackFor = Exception.class)
    public void importStudent(InputStream inputStream, String centerAreaId, String userId){
        //设置键操作
        setStudentKey();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        setHeaderAlias(reader);
        List<StudentOnLine> list = reader.readAll(StudentOnLine.class);

        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "导入数据不存在");

        Map<String, List<StudentOnLine>> stringListMap = list.stream()
                .filter(s -> StrUtil.isNotBlank(s.getClassName()))
                .collect(Collectors.groupingBy(StudentOnLine::getClassName));
        //判断班级信息存在则设值，不存在新建
        Map<String, String> classIds = getClass(stringListMap.keySet(), centerAreaId, userId);
        stringListMap.forEach((k, v) -> {
            List<StudentOnLine> lineList = setClassId(classIds, v, centerAreaId, userId);
            studentOnLineRepository.saveAll(lineList);
        });
        //删除键值操作
        deleteKey();
    }
    private Map<String, String> getClass(Set<String> set, String centerAreaId, String userId){
        return set.stream()
                .filter(Objects::nonNull)
                .map(className -> tbClassService.getClassIdByClassName(className, centerAreaId, userId))
                .collect(Collectors.toMap(TbClasses::getClassName, TbClasses::getClassId));
    }
    private List<StudentOnLine> setClassId(Map<String, String> classIds, List<StudentOnLine> list, String centerAreaId, String userId){
        return list.stream().map(s -> setStudentOnLine(s, classIds, centerAreaId, userId)).collect(Collectors.toList());
    }

    private StudentOnLine setStudentOnLine(StudentOnLine studentOnLine, Map<String, String> classIds, String centerAreaId, String userId){
        studentOnLine.setClassId(classIds.get(studentOnLine.getClassName()));
        studentOnLine.setCenterAreaId(centerAreaId);
        studentOnLine.setCreateUser(userId);
        studentOnLine.setUpdateUser(userId);
        //设置属性字段是导入数据
        studentOnLine.setImportStatus(STUDENT_ON_LINE_IMPORT_STATUS_IMPORT);
        return BeanUtil.trimStrFields(studentOnLine);
    }

    private void setHeaderAlias(@NonNull ExcelReader reader) {
        reader.addHeaderAlias(studentId.getName(), studentId.name());
        reader.addHeaderAlias(studentName.getName(), studentName.name());
        reader.addHeaderAlias(gender.getName(), gender.name());
        reader.addHeaderAlias(stuIDCard.getName(), stuIDCard.name());
        reader.addHeaderAlias(stuPhone.getName(), stuPhone.name());
        reader.addHeaderAlias(className.getName(), className.name());
        reader.addHeaderAlias(enrollmentDate.getName(), enrollmentDate.name());
        reader.addHeaderAlias(nation.getName(), nation.name());
        reader.addHeaderAlias(learningModality.getName(), learningModality.name());
    }

    public int countByClassId(String classId){
        return studentOnLineRepository.countAllByIsValidatedEqualsAndClassId(TAKE_EFFECT_OPEN, classId);
    }

    /* 查询*/
    public Page<StudentOnLine> findAllPage(PageRequest request){
        return studentOnLineRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, request);
    }

    public Page<StudentOnLineDto> findAllPageDtoByCenterAreaId(String centerAreaId, PageRequest request){
        return studentOnLineRepository.findAllByIsValidatedEqualsAndCenterAreaIdDto(centerAreaId, request);
    }

    public Page<StudentOnLineDto> findAllPageDto(PageRequest request){
        return studentOnLineRepository.findAllByIsValidatedEqualsDto(request);
    }

    public List<StudentOnLine> findByStuIDCardAndStudentName(String stuIDCard, String studentName){
        return studentOnLineRepository.findAllByIsValidatedEqualsAndStuIDCardAndStudentNameOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, stuIDCard, studentName);
    }
}