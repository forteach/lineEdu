package com.project.course.web.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-19 15:29
 * @version: 1.0
 * @description:
 */
@Data
public class CourseDataDeleteReq implements Serializable {

    private String chapterId;

    private List<String> fileIds;

    private String updateUser;
}