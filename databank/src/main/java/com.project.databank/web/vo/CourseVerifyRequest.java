package com.project.databank.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class CourseVerifyRequest implements Serializable {
    private String id;
    private String verifyStatus;
    private String remark;
    private String userId;
}
