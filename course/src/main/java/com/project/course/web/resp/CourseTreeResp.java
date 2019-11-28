package com.project.course.web.resp;

import com.project.course.web.req.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class CourseTreeResp implements Serializable {

    private String id;

    private String parent;

    private String text;

    private String icon;

    private Integer level;

    private State state;

    private Integer randomQuestionsNumber;

    private Integer videoTime;

    /** 观看位置时间*/
    private String locationTime;
    /** 观看视频时间长度*/
    private Integer duration;
}