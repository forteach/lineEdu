package com.project.course.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/9 15:19
 * @Version: 1.0
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigQuestionVo implements Serializable {
    /**
     * 题目题干
     */
    private String choiceQstTxt;
    /**
     * 考题类型   single  multiple trueOrFalse
     */
    protected String examType;

    /**
     * 选择项 A,B,C,D,E,F等Value
     */
    private String optTxtA;
    private String optTxtB;
    private String optTxtC;
    private String optTxtD;
    private String optTxtE;
    private String optTxtF;

    /**
     * 答案
     */
    private String answer;
    /**
     * 题目解析
     */
    private String analysis;

    /**
     * 题目分数
     */
    protected String score;
    /**
     * 难易度id
     */
    private String levelId;
    /** 分类 */
    private String type;
    /** 标签*/
    private String tag;
}