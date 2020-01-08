package com.project.questionlibrary.domain.base;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/6 11:56
 * @version: 1.0
 * @description:
 */
@Data
public abstract class QuestionBase implements Serializable {
    /**
     * 习题id
     */
    @Id
    private String questionId;

    /**
     * 习题类型
     */
    private String questionType;

    /**
     * 习题标题
     */
    private String questionTitle;
    /**
     * 习题解析
     */
    private String questionAnalyze;
    /**
     * 习题分数
     */
    private Double fractionalNumber;
    /**
     * 习题分类
     */
    private String classify;
    /**
     * 习题标签
     */
    private String label;
}