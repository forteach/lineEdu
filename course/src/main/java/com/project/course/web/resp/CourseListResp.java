package com.project.course.web.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResp implements Serializable {

    private String courseId;

    private String alias;

    private String courseName;

    private String courseNumber;

    private String topPicSrc;

    private String courseDescribe;

    private String chapterId;

    private String chapterName;

    private String joinChapterId;

    private String joinChapterName;

    private String teacherId;

    private String teacherName;

    private Integer courseType;
}
