package com.project.portal.user.request;

import com.project.portal.request.SortVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 16:38
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherFindAllPageRequest extends SortVo implements Serializable {
    private String isValidated;
}
