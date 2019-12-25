package com.project.user.web.resp;

import com.project.schoolroll.domain.LearnCenter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-29 10:01
 * @version: 1.0
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse implements Serializable {

    private String userId;

    private String teacherId;

    private String userName;

    private String token;

    private String roleId;

    private String roleCode;

    private String roleName;

    private String roleActivity;

    private String centerAreaId;

    private String centerName;

    private LearnCenter learnCenter;
}