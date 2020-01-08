package com.project.questionlibrary.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/6 17:11
 * @version: 1.0
 * @description:
 */
@Data
public class Question implements Serializable {
    private String title;
    private Integer number;
    private String question;
    private String questionType;
    private String rightAnswer;
    private String getScore;
}
