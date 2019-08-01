package com.project.course.web.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class FindImpCoursewareReq implements Serializable {

    private String chapterId;

    private String importantType;

    private String datumType;

}