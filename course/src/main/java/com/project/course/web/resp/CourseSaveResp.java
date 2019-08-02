package com.project.course.web.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-28 11:49
 * @Version: 1.0
 * @Description: 返回前端目录树结构
 */
@Builder
@Data
public class CourseSaveResp implements Serializable {

    private String courseId;

//    private String shareId;
}
