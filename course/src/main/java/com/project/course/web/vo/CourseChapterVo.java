package com.project.course.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 11:27
 * @Version: 1.0
 * @Description: 查询科目章节VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseChapterVo implements Serializable {

    private String isValidated;

    private String courseId;
}
