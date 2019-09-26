package com.project.user.domain;

import com.project.mysql.domain.Entitys;
import com.project.user.domain.base.BaseTeacher;
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
@org.hibernate.annotations.Table(appliesTo = "teacher_verify", comment = "教师审核信息")
@Table(name = "teacher_verify", indexes = {
        @Index(columnList = "teacher_id", name = "teacher_id_index"),
        @Index(columnList = "teacher_code", name = "teacher_code_index"),
        @Index(columnList = "phone", name = "phone_index")
})
public class TeacherVerify extends BaseTeacher implements Serializable {
    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;
}