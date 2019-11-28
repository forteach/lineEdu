package com.project.mongodb.domain;

import com.project.mongodb.domain.base.AbstractAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-19 10:26
 * @version: 1.0
 * @description: 学生练习回答详情信息 学生练习作业回答详情信息
 */
@Data
@Builder
@AllArgsConstructor
@Document(collection = "bigQuestionAnswer")
@EqualsAndHashCode(callSuper = true)
public class BigQuestionAnswer extends AbstractAnswer {
    /**
     * 练习题id
     */
    @Indexed
    private String questionId;

    /**
     * 回答结果正确与否
     */
    private String right;

    /**
     * 学生回答内容
     */
    private String stuAnswer;

    /**
     * 题目信息详情
     */
    private BigQuestion bigQuestion;
}