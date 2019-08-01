package com.project.course.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2018/12/5 22:18
 * @Version: 1.0
 * @Description: 查询知识点关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KNodeAll implements Serializable {

    private String courseId;

    private String chapterId;

    private String kNodeId;

    private String nodeName;

    private String createUser;

    public KNodeAll(String courseId, String chapterId, String kNodeId, String nodeName) {
        this.courseId = courseId;
        this.chapterId = chapterId;
        this.kNodeId = kNodeId;
        this.nodeName = nodeName;
    }
}