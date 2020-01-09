package com.project.course.service;

import com.project.course.web.vo.QuestionVo;
import com.project.mongodb.domain.BigQuestion;

import java.io.InputStream;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/9 14:09
 * @Version: 1.0
 * @Description:
 */
public interface QuestionService {
    /**
     * 用户上传的习题库读取保存到redis 保存时间 1天
     */
    List<BigQuestion> readImportQuestion(InputStream inputStream, QuestionVo vo);

    List<BigQuestion> getRedisBigQuestion(String userId);

    void saveBigQuestion(List<BigQuestion> list);
}