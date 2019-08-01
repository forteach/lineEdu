package com.project.course.web.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:　教师
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 14:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String teacherId;

    private String teacherName;

}