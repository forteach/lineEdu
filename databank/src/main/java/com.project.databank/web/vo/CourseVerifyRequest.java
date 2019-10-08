package com.project.databank.web.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 15:56
 * @version: 1.0
 * @description:
 */
@Data
@Builder
public class CourseVerifyRequest implements Serializable {
    private String id;
    private String verifyStatus;
    private String remark;
    private String userId;

    public CourseVerifyRequest() {
    }

    public CourseVerifyRequest(String id, String verifyStatus, String remark, String userId) {
        this.id = id;
        this.verifyStatus = verifyStatus;
        this.remark = remark;
        this.userId = userId;
    }
}
