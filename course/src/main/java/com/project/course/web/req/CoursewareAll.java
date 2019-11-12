package com.project.course.web.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoursewareAll implements Serializable {

    /**
     * 文件编号（也可能是图集编号）
     */
    public String fileId;

    /**
     * 文件名称
     */
    public String fileName;
    /**
     * 图集的话，可以没有URL
     */
    public String fileUrl;

    private Integer videoTime;

    private String remark;

    private String verifyStatus;

    /** 视频时长(单位秒)*/
    private Long videoDuration;
    /** 观看位置时间*/
    private String locationTime;
}