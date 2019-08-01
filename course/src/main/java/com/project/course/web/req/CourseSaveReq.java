package com.project.course.web.req;

import com.project.course.web.vo.RCourse;
import com.project.course.web.vo.RTeacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-29 16:21
 * @Version: 1.0
 * @Description:课程添加Request对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSaveReq implements Serializable {

    private RCourse course;

    private String oldShareId;

    private List<RTeacher> teachers;

    private String createUser;
}