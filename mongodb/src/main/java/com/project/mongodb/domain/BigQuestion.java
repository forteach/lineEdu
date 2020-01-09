package com.project.mongodb.domain;

import com.project.mongodb.domain.base.AbstractExam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description: 所有的题目类型 全部由大题外部封装   由examChildren展示具体的题目信息
 * 所有的题目类型 全部由大题外部封装 由examChildren展示具体的题目信息
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/15  11:04
 */
@Data
@Document(collection = "bigQuestion")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BigQuestion extends AbstractExam {

    public BigQuestion(String chapterId, String courseId, String chapterName,
                       Double score, String teacherId, String examType, String choiceQstTxt,
                       String answer, String analysis, String levelId, String courseName,
                       String teacherName, String centerAreaId, String centerName, String verifyStatus) {
        super(chapterId, courseId, chapterName, score, teacherId, examType, choiceQstTxt,
                answer, analysis, levelId, courseName, teacherName, centerAreaId, centerName, verifyStatus);
    }
}
