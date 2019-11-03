package com.project.mongodb.domain;


import com.project.mongodb.domain.base.AbstractAnswer;
import com.project.mongodb.domain.base.QuestionAnswer;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-9 16:52
 * @version: 1.0
 * @description:　学生答题的题目信息快照表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "questionsLists")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsLists extends AbstractAnswer {

    /**
     * 是否回答完毕 Y/N
     */
    private String isAnswerCompleted;

    /**
     * 学生生成题目信息
     */
    private List<QuestionAnswer> bigQuestions;

    /**
     * 回答过的题目id集合
     */
    private List<String> questionIds;
}