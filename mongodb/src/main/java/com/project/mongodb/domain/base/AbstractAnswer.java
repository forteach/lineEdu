package com.project.mongodb.domain.base;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-9 16:43
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractAnswer extends BaseEntity {

    /**
     * 课程id
     */
    @Indexed
    private String courseId;

    /**
     * 章节id
     */
    @Indexed
    protected String chapterId;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * 班级id
     */
    private String classId;
    /**
     * 学生id
     */
    @Indexed
    private String studentId;
}