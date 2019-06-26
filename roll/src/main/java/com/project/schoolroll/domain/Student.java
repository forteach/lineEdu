package com.project.schoolroll.domain;


import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student", comment = "学生信息")
@Table(name = "student")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Entitys {

    @Id
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生ID'")
    private String studentId;

    @Column(name = "student_code", columnDefinition = "VARCHAR(32) COMMENT '学生学校编号'")
    private String studentCode;

    public Student(String studentId, String studentCode, String centerId) {
        this.studentId = studentId;
        this.studentCode = studentCode;
        super.centerAreaId=centerId;
    }
}
