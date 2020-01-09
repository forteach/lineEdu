package com.project.course.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.QuestionService;
import com.project.course.web.vo.BigQuestionVo;
import com.project.course.web.vo.QuestionVo;
import com.project.mongodb.domain.BigQuestion;
import com.project.mongodb.domain.question.ChoiceQstOption;
import com.project.mongodb.repository.BigQuestionRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/9 14:10
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    public static final String READ_QUESTION_KEY = "import_question_";
    private final BigQuestionRepository bigQuestionRepository;
    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public QuestionServiceImpl(BigQuestionRepository bigQuestionRepository, RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.bigQuestionRepository = bigQuestionRepository;
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public List<BigQuestion> readImportQuestion(InputStream inputStream, QuestionVo vo){
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //设置别名头部取数
        setHeaderAlias(reader);
        //取值
        List<BigQuestionVo> list = reader.readAll(BigQuestionVo.class);
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0010, "你导入的信息是空白数据");
        // 校验数据不能为空
        checkData(list);
        List<BigQuestion> bigQuestions = builderBigQuestion(list, vo);
        stringRedisTemplate.opsForValue().set(READ_QUESTION_KEY.concat(vo.getTeacherId()), JSONUtil.toJsonStr(list),  Duration.ofDays(1L));
        log.info("import bigQuestion userId: [{}], size : [{}]", vo.getTeacherId(), bigQuestions.size());
        return bigQuestions;
    }

    private List<BigQuestion> builderBigQuestion(List<BigQuestionVo> list, QuestionVo vo){
        return list.stream().map(v ->{
            BigQuestion bigQuestion = new BigQuestion(vo.getChapterId(), vo.getCourseId(), vo.getChapterName(),
                    Double.valueOf(v.getScore()), vo.getTeacherId(), v.getExamType(), v.getChoiceQstTxt(),
                    v.getAnswer(), v.getAnalysis(), v.getLevelId(), vo.getCourseName(),
                    vo.getTeacherName(), vo.getCenterAreaId(), vo.getCenterName(), VERIFY_STATUS_APPLY);
            List<ChoiceQstOption> choiceQstOptions = new ArrayList<>(6);
            if (StrUtil.isNotBlank(v.getOptTxtA())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "A"));
            }
            if (StrUtil.isNotBlank(v.getOptTxtB())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "B"));
            }
            if (StrUtil.isNotBlank(v.getOptTxtC())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "C"));
            }
            if (StrUtil.isNotBlank(v.getOptTxtD())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "D"));
            }
            if (StrUtil.isNotBlank(v.getOptTxtE())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "E"));
            }
            if (StrUtil.isNotBlank(v.getOptTxtF())){
                choiceQstOptions.add(new ChoiceQstOption(v.getOptTxtA(), "F"));
            }
            bigQuestion.setOptChildren(choiceQstOptions);
            return bigQuestion;
        }).collect(toList());
    }
    @Override
    public List<BigQuestion> getRedisBigQuestion(String userId){
        String listStr = stringRedisTemplate.opsForValue().get(READ_QUESTION_KEY.concat(userId));
        if (JSONUtil.isJsonArray(listStr)) {
            return JSONUtil.toList(JSONUtil.parseArray(listStr), BigQuestion.class);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void saveBigQuestion(List<BigQuestion> list){
        bigQuestionRepository.saveAll(list);
    }

    private void checkData(List<BigQuestionVo> list){
        list.forEach(v -> {
            MyAssert.isTrue(StrUtil.isBlank(v.getChoiceQstTxt()), DefineCode.ERR0010, "题目信息为空");
            MyAssert.isTrue(StrUtil.isBlank(v.getExamType()), DefineCode.ERR0010, "题目类型为空");
            MyAssert.isTrue(StrUtil.isBlank(v.getOptTxtA()), DefineCode.ERR0010, "选项A为空");
            MyAssert.isTrue(StrUtil.isBlank(v.getAnswer()), DefineCode.ERR0010, "正确答案为空");
            MyAssert.isTrue(StrUtil.isBlank(v.getScore()), DefineCode.ERR0010, "分数为空");
            MyAssert.isFalse(NumberUtil.isNumber(v.getScore()), DefineCode.ERR0010, "分数不是数字");
        });
    }

    private void setHeaderAlias(@NonNull ExcelReader reader) {
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(0, 0)), "choiceQstTxt");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(1, 0)), "examType");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(2, 0)), "optTxtA");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(3, 0)), "optTxtB");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(4, 0)), "optTxtC");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(5, 0)), "optTxtD");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(6, 0)), "optTxtE");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(7, 0)), "optTxtF");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(8, 0)), "answer");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(9, 0)), "analysis");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(10, 0)), "score");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(11, 0)), "type");
        reader.addHeaderAlias(String.valueOf(reader.readCellValue(12, 0)), "tag");
    }
}
