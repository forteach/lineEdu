package com.project.mongodb.domain.base;


import com.project.mongodb.domain.question.ChoiceQstOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/15  0:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractExam extends BaseEntity {

    /**
     * 章节id
     */
    @Indexed
    protected String chapterId;

    /**
     * 课程id
     */
    @Indexed
    private String courseId;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * 题目分数
     */
    protected Double score;

    /**
     * 创作老师
     */
    protected String teacherId;

    /**
     * 考题类型   single  multiple trueOrFalse
     */
    protected String examType;

    /**
     * 题目题干
     */
    private String choiceQstTxt;

    /**
     * 回答答案
     */
    private String answer;

    /**
     * 题目解析
     */
    private String analysis;

    /**
     * 难易度id
     */
    private String levelId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 添加修改的教师名称
     */
    private String teacherName;

    /**
     * 学习中心Id
     */
    public String centerAreaId;

    /**
     * 学习中心名称
     */
    public String centerName;


    /**
     * 选项集
     */
    private List<ChoiceQstOption> optChildren;

    private String remark;

    private String verifyStatus;
}