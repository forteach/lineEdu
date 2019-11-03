package com.project.course.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-27 15:40
 * @version: 1.0
 * @description:
 */
public interface ICourseStudyDto {
    /**
     * 课程id
     */
    public String getCourseId();

    /**
     * 课程名称
     *
     * @return
     */
    public String getCourseName();

    /**
     * 别名
     *
     * @return
     */
    public String getAlias();

    /**
     * 章节id
     *
     * @return
     */
    public String getChapterId();

    /**
     * 章节名称
     *
     * @return
     */
    public String getChapterName();

    /**
     * 学习状态
     *
     * @return
     */
    public Integer getStudyStatus();

    /**
     * 平时成绩/学期成绩
     *
     * @return
     */
    public String getSemesterGrade();

    /**
     * 考试成绩期末成绩
     *
     * @return
     */
    public String getExamGrade();

    /**
     * 考试结果状态 0 已经通过 1 未通过
     *
     * @return
     */
    public Integer getExamResults();

    /**
     * 是否需要补考
     *
     * @return
     */
    public Integer getMakeUpExamination();

    /**
     * 学生Id
     */
    public String getStudentId();

    /**
     * 学生名称
     */
    public String getStudentName();

    /**
     * 在线学习时长(秒)
     */
    public Integer getOnLineTime();

    /**
     * 课程视频总长度(秒)
     */
    public Integer getOnLineTimeSum();

    /**
     * 回答题目数量
     */
    public Integer getAnswerSum();

    /**
     * 正确题目总数量
     */
    public Integer getCorrectSum();
}