package com.project.schoolroll.domain;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:30
 * @version: 1.0
 * @description: 在线教师信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@org.hibernate.annotations.Table(appliesTo = "teacher", comment = "教师信息")
@Table(name = "teacher", indexes = {
        @Index(columnList = "teacher_id", name = "teacher_id_index"),
        @Index(columnList = "teacher_code", name = "teacher_code_index")
})
public class Teacher extends Entitys implements Serializable {
    @Id
    @Column(name = "teacher_id", columnDefinition = "VARCHAR(32) COMMENT '教师id'")
    private String teacherId;

    @Column(name = "teacher_name", columnDefinition = "VARCHAR(32) COMMENT '教师名称'")
    private String teacherName;

    @Column(name = "teacher_code", columnDefinition = "VARCHAR(32) COMMENT '教师代码'")
    private String teacherCode;
}