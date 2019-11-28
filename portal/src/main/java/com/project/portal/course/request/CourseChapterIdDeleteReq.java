package com.project.portal.course.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-27 11:57
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterIdDeleteReq implements Serializable {
    private String chapterId;
}
