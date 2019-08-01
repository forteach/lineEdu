package com.project.course.domain;

import com.project.course.domain.pk.CourseShareUsersPk;
import com.project.mysql.domain.Entitys;
import lombok.*;

import javax.persistence.*;

/**
 * @Description: 课程内容协作访问成员
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 16:42
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course_share_users", indexes = {@Index(columnList = "share_id", name = "share_id_index"),
        @Index(columnList = "user_id", name = "user_id_index")})
@EqualsAndHashCode(callSuper = true)
@IdClass(CourseShareUsersPk.class)
public class CourseShareUsers extends Entitys {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CourseShareUsersPk shareUsersPk;

    private String shareId;

    private String userId;

    @Column(name = "user_name", columnDefinition = "VARCHAR(40) COMMENT '接受分享成员名称'")
    private String userName;

}
