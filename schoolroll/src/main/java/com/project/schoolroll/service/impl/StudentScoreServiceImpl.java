package com.project.schoolroll.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.repository.StudentScoreRepository;
import com.project.schoolroll.repository.dto.StudentOnLineDto;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.service.vo.CourseScoreVo;
import com.project.schoolroll.web.vo.ScoreVo;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:38
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class StudentScoreServiceImpl extends BaseMySqlService implements StudentScoreService {
    private final StudentScoreRepository studentScoreRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, String> redisTemplate;
//    private final StudentOnLineService studentOnLineService;

    @PersistenceContext
    private EntityManager entityManager;

    public StudentScoreServiceImpl(StudentScoreRepository studentScoreRepository, RedisTemplate<String, String> redisTemplate,
//                                   StudentOnLineService studentOnLineService,
                                   StringRedisTemplate stringRedisTemplate) {
        this.studentScoreRepository = studentScoreRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
//        this.studentOnLineService = studentOnLineService;
    }

    @Override
    public StudentScore findByStudentIdAndCourseId(String studentId, String courseId) {
        Optional<StudentScore> optional = studentScoreRepository.findAllByIsValidatedEqualsAndStudentIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在对应的信息");
        return optional.get();
    }

    @Override
    public StudentScore findStudentIdAndCourseId(String studentId, String courseId) {
        return studentScoreRepository.findAllByStudentIdAndCourseId(studentId, courseId).orElseGet(StudentScore::new);
    }

    @Override
    public List<StudentScore> findByStudentId(String studentId) {
        return studentScoreRepository.findAllByIsValidatedEqualsAndStudentIdOrderByUpdateTime(TAKE_EFFECT_OPEN, studentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudentScoreById(String scoreId) {
        studentScoreRepository.deleteById(scoreId);
    }

    @Override
    public Page<StudentScore> findStudentScorePageAll(StudentScorePageAllVo pageAllVo, PageRequest of) {

        StringBuilder dateSql = new StringBuilder("select * from student_score ");
        StringBuilder whereSql = new StringBuilder("where is_validated = '0'");
        StringBuilder countSql = new StringBuilder("select count(1) from student_score ");
        if (StrUtil.isNotBlank(pageAllVo.getStudentId())) {
            whereSql.append(" and student_id = :studentId");
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseId())) {
            whereSql.append(" and course_id = :courseId");
        }
        if (StrUtil.isNotBlank(pageAllVo.getType())) {
            whereSql.append(" and type = :type");
        }
        if (StrUtil.isNotBlank(pageAllVo.getStartDate())) {
            whereSql.append(" and u_time >= ").append(pageAllVo.getStartDate());
        }
        if (StrUtil.isNotBlank(pageAllVo.getEndDate())) {
            whereSql.append(" and u_time <= ").append(pageAllVo.getEndDate());
        }

        dateSql.append(whereSql).append(" order by u_time desc");
        countSql.append(whereSql);

        Query dataQuery = entityManager.createNativeQuery(dateSql.toString(), StudentScore.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());

        if (StrUtil.isNotBlank(pageAllVo.getStudentId())) {
            dataQuery.setParameter("studentId", pageAllVo.getStudentId());
            countQuery.setParameter("studentId", pageAllVo.getStudentId());
        }
        if (StrUtil.isNotBlank(pageAllVo.getCourseId())) {
            dataQuery.setParameter("courseId", pageAllVo.getCourseId());
            countQuery.setParameter("courseId", pageAllVo.getCourseId());
        }
        if (StrUtil.isNotBlank(pageAllVo.getType())) {
            dataQuery.setParameter("type", pageAllVo.getType());
            countQuery.setParameter("type", pageAllVo.getType());
        }

        // 设置分页
        dataQuery.setFirstResult((int) of.getOffset());
        dataQuery.setMaxResults(of.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<StudentScore> content2 = total > of.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        return new PageImpl<>(content2, of, total);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateOffLineScore(OffLineScoreUpdateVo vo) {
//        StudentScore studentScore = findById(vo.getScoreId());
//        //是线上课程不能录入线下成绩
//        MyAssert.isTrue(COURSE_TYPE_1.equals(studentScore.getType()), DefineCode.ERR0010, "线上课程不能录入线下成绩");
//        studentScore.setUpdateUser(vo.getUpdateUser());
//        studentScore.setOnLineScore(vo.getOffLineScore());
//        //线下占比
//        Integer linePercentage = studentScore.getLinePercentage();
//        MyAssert.isNull(linePercentage, DefineCode.ERR0010, "线下占比成绩百分比是空");
//        MyAssert.isTrue(0 == linePercentage, DefineCode.ERR0010, "线下占比为0不能录入");
//        //计算加线下成绩后课程成绩
//        BigDecimal courseScore = NumberUtil.add(NumberUtil.mul(Double.valueOf(vo.getOffLineScore()), linePercentage), studentScore.getCourseScore());
//        studentScore.setCourseScore(courseScore.floatValue());
//        studentScoreRepository.save(studentScore);
//    }

    @Override
    public StudentScore findById(String scoreId) {
        Optional<StudentScore> optional = studentScoreRepository.findById(scoreId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在对应的学生成绩");
        return optional.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<StudentScore> list) {
        studentScoreRepository.saveAll(list);
    }

    //    @Override
//    public List<List<String>> exportScore(String centerId) {
//        List<String> head = setExportHead();
//        List<List<String>> list = findStudentScoreLists(centerId);
//        list.add(0, head);
//        return list;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taskCompleteCourseScore() {
        //查询没有计算过的成绩
        List<StudentScore> collect = studentScoreRepository.findAllByCompleteStatusAndType(1, COURSE_TYPE_3)
                .parallelStream()
                .filter(Objects::nonNull)
                .map(s -> {
                    int linePercentage = s.getLinePercentage();
                    int onLinePercentage = s.getOnLinePercentage();
                    String offLineScore = s.getOffLineScore();
                    String onLineScore = s.getOnLineScore();
                    if (StrUtil.isNotBlank(offLineScore) && StrUtil.isNotBlank(onLineScore)) {
                        //线上总成绩
                        BigDecimal onLineScoreSum = NumberUtil.mul(new BigDecimal(onLineScore), NumberUtil.div(onLinePercentage, 100, 2));
                        //线下总成绩
                        BigDecimal offLineScoreSum = NumberUtil.mul(new BigDecimal(offLineScore), NumberUtil.div(linePercentage, 100, 2));
                        //课程总成绩
                        BigDecimal courseScore = NumberUtil.add(onLineScoreSum, offLineScoreSum);
                        s.setCourseScore(courseScore.floatValue());
                        s.setCompleteStatus(1);
                        return s;
                    } else {
                        return null;
                    }
                }).collect(toList());
        if (!collect.isEmpty()) {
            studentScoreRepository.saveAll(collect);
        }
    }

    @Override
    public List<List<String>> exportScore(List<StudentOnLineDto> list) {
        //获取对应的学生成绩信息列表
        List<CourseScoreVo> scoreVos = list.parallelStream().map(this::setScoreValue).collect(toList());
        //获取集合元素最多值的,用户设置课程列表的表头
        int maxSize = scoreVos.stream().filter(Objects::nonNull)
                .map(CourseScoreVo::getCourseScore)
                .mapToInt(List::size)
                .max()
                .orElse(0);
        //设置获取课程成绩列表表头
        List<Map<String, String>> maxList = scoreVos.stream()
                .filter(Objects::nonNull)
                .map(CourseScoreVo::getCourseScore)
                .filter(c -> maxSize == c.size())
                .findFirst()
                .orElseGet(LinkedList::new);
        //构造转换为头部课程别表表头信息
        List<String> head = setHead(maxList);
        //取出对应的课程列表和成绩组合输出列表数据
        List<List<String>> listList = scoreVos.parallelStream().filter(Objects::nonNull).map(v -> setStudentScore(v, head, maxSize)).collect(toList());
        listList.add(0, head);
        return listList;
    }

    private List<String> setStudentScore(CourseScoreVo vo, List<String> head, int maxSize) {
        List<String> strings = CollUtil.newArrayList(vo.getStudentName(), vo.getStudentId(),
                vo.getStuId(), vo.getGrade(), vo.getSpecialtyName(), vo.getGender());
        setHeadValue(vo.getCourseScore(), strings, head, maxSize);
        return strings;
    }

    /**
     * 设置课程头部标题
     *
     * @param mapList
     * @return
     */
    private List<String> setHead(List<Map<String, String>> mapList) {
        List<String> list = CollUtil.newArrayList("姓名", "身份证号", "学号", "年级", "专业", "性别");
        mapList.forEach(m -> list.add(m.get("courseName")));
        return list;
    }

    /**
     * @param mapList 需要转换为行数据的课程成绩
     * @param list    要导出成绩的每一行数据
     * @param head    导出成绩的表格表头信息
     * @return List<String> 导出数据的每一行学生和对应的课程成绩信息
     */
    private List<String> setHeadValue(List<Map<String, String>> mapList, List<String> list, List<String> head, int maxSize) {
        if (maxSize == mapList.size()) {
            //有全部课程成绩直接导出
            mapList.forEach(m -> list.add(m.get("score")));
        } else {
            //有部分课程没有成绩只取对应的课程成绩
            for (int i = head.size() - maxSize; i < head.size(); i++) {
                String courseName = head.get(i);
                mapList.forEach(m -> {
                    if (courseName.equals(m.get("courseName"))) {
                        list.add(m.get("score"));
                    } else {
                        list.add("");
                    }
                });
            }
        }
        return list;
    }

    private CourseScoreVo setScoreValue(StudentOnLineDto s) {
        String studentId = s.getStudentId();
        //课程信息 key courseId value   score, courseName
        List<Map<String, String>> list = new ArrayList<>(16);
        studentScoreRepository.findAllByIsValidatedEqualsAndStudentId(TAKE_EFFECT_OPEN, studentId)
                .forEach(d -> {
                    Map<String, String> stringMap = new HashMap<>(3);
                    stringMap.put("courseName", d.getCourseName());
                    stringMap.put("score", String.valueOf(d.getCourseScore()));
                    stringMap.put("courseId", d.getCourseId());
                    list.add(stringMap);
                });
        return new CourseScoreVo(s.getStudentId(), s.getStudentName(), s.getStuId(), s.getGender(), s.getGrade(), s.getSpecialtyName(), list);
    }


//    private static List<List<String>> convert(List<StudentGrand> StudentGrandList)
//            throws IntrospectionException, IllegalAccessException, InvocationTargetException {//取得StudentGrand的属性，当然你也可以用list = {"id", "name", ...}
//        Field[] declaredFields = StudentGrand.class.getDeclaredFields();
//
//        List<List<String>> convertedTable = new ArrayList<List<String>>();
//
//        //多少个属性表示多少行，遍历行
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            ArrayList<String> rowLine = new ArrayList<String>();
//            //list<T>多少个StudentGrand实体类表示有多少列，遍历列
//            for (int i = 0, size = StudentGrandList.size(); i < size; i++) {
//                //每一行的第一列对应StudentGrand字段名
//                //所以新table的第一列要设置为字段名
//                if(i == 0){
//                    rowLine.add(field.getName());
//                }
//                //新table从第二列开始，某一列的某个值对应旧table第一列的某个字段
//                else{
//                    StudentGrand StudentGrand = StudentGrandList.get(i);
//                    String val = (String) field.get(StudentGrand);//grand为int会报错
//                    System.out.println(val);
//                    rowLine.add(val);
//                }
//            }
//            convertedTable.add(rowLine);
//        }
//        return convertedTable;
//    }

//    private List<List<String>> findStudentScoreLists(String centerId) {
//        return studentScoreRepository.findAllByIsValidatedEqualsAndCenterAreaId(centerId)
//                .parallelStream()
//                .filter(Objects::nonNull)
//                .map(o -> CollUtil.newArrayList(o.getStuId(), o.getStudentId(), o.getStudentName(), o.getGender(), o.getCourseName(), o.getSchoolYear(), o.getTerm(),
//                        String.valueOf(o.getCourseScore()), o.getOnLineScore(), o.getOffLineScore(),
//                        getType(o.getType()), o.getCourseType()))
//                .collect(toList());
//    }

//    private String getType(String type) {
//        switch (type) {
//            case COURSE_TYPE_1:
//                return "线上";
//            case COURSE_TYPE_2:
//                return "线下";
//            case COURSE_TYPE_3:
//                return "混合";
//            case COURSE_TYPE_4:
//                return "线上和混合";
//            default:
//                return "";
//        }
//    }

//    private List<String> setExportHead() {
//        return CollUtil.newLinkedList("学号",
//                "身份证号码",
//                "姓名",
//                "性别",
//                "课程",
//                "学年",
//                "学期",
//                "课程分数",
//                "线上成绩",
//                "线下成绩",
//                "课程类型",
//                "课程类别(必修(bx)、选修(xx)、实践(sj)");
//    }

    @Override
    @SuppressWarnings(value = "all")
    public void checkoutKey(String key) {
        MyAssert.isTrue(stringRedisTemplate.hasKey(key), DefineCode.ERR0013, "有人操作，请稍后再试!");
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @SuppressWarnings(value = "all")
    private void setKey(String key) {
        redisTemplate.opsForValue().set(key, DateUtil.now(), 10L, TimeUnit.MINUTES);
    }

    @Override
    public void importScore(InputStream inputStream, String key, String courseId, String courseName, String type, String userId, String centerId) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //设置导入
        setHeaderAlias(reader, courseName);

        List<ScoreVo> list = reader.readAll(ScoreVo.class);
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0010, "你导入的成绩信息是空白数据");
        // 校验数据不能为空
        checkScore(list);

        //设置key和过期时间
        setKey(key);

        // 构造数据
        List<StudentScore> collect = list.stream()
                .filter(Objects::nonNull)
                .map(v -> setOffLineScore(v.getStudentId(), courseId, type, v.getScore(), userId, centerId))
                .collect(toList());
        if (!collect.isEmpty()) {
            studentScoreRepository.saveAll(collect);
        }
        deleteKey(key);
    }

    private StudentScore setOffLineScore(String studentId, String courseId, String type, String score, String userId, String centerId) {
        StudentScore studentScore = findStudentIdAndCourseId(studentId, courseId);
        studentScore.setOffLineScore(score);
        studentScore.setUpdateUser(userId);
        studentScore.setCreateUser(userId);
        studentScore.setCourseId(courseId);
        studentScore.setStudentId(studentId);
        studentScore.setCenterAreaId(centerId);
        studentScore.setType(type);
        if (COURSE_TYPE_2.equals(type)){
            //是线下课程,直接计算完成计算课程,课程总成绩就是课程总成绩
            studentScore.setCompleteStatus(1);
            studentScore.setCourseScore(Float.valueOf(score));
        }
        return studentScore;
    }

    private void checkScore(List<ScoreVo> list) {
        list.forEach(v -> {
            MyAssert.isTrue(StrUtil.isBlank(v.getScore()), DefineCode.ERR0010, "成绩信息不能为空");
            MyAssert.isTrue(StrUtil.isBlank(v.getStudentId()), DefineCode.ERR0010, "身份证号码不能为空");
            MyAssert.isFalse(NumberUtil.isNumber(v.getScore()), DefineCode.ERR0010, v.getScore() + " 不是数字");
        });
    }

    private void setHeaderAlias(@NonNull ExcelReader reader, String courseName) {
        reader.addHeaderAlias("身份证号码", "studentId");
        reader.addHeaderAlias(courseName, "score");
    }
}