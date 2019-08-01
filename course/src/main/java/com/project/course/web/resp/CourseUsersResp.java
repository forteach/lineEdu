package com.project.course.web.resp;

import com.project.course.domain.pk.CourseShareUsersPk;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */
@Data
public class CourseUsersResp implements Serializable {

    private CourseShareUsersPk shareUsersPk;

    private String shareId;

    private String userId;

    @Column(name = "user_name", columnDefinition = "VARCHAR(40) COMMENT '接受分享成员名称'")
    private String userName;

}
