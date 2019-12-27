package com.project.course.web.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-3 17:09
 * @Version: 1.0
 * @Description: 课程教案、课件
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpCoursewareAll implements Serializable {

    /**
     * 章节编号
     */
    private String chapterId;

    /**
     * 资料领域：1教案 2课件
     */
//    private String importantType;


    /**
     * 文件名称
     */
    public String fileName;
    /**
     * 图集的话，可以没有URL
     */
    public String fileUrl;
    /**
     * 视频时长
     */
    private Integer videoTime;

    private String courseId;

    private String courseName;

    @JsonIgnore
    private String centerName;
    @JsonIgnore
    private String createUser;
//    @JsonIgnore
//    private String teacherName;
//    @JsonIgnore
//    private String teacherId;
}