package com.project.course.web.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 科目
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 16:42
 */
@Data
public class RCourseData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dataId;

    private String datumName;

    private String datumUrl;

    private String datumArea;

    private String datumType;

    private String courseId;

    private String chapterId;

    private String kNodeId;

//    private String teachShare = "0";

//    private String stuShare = "0";

}
