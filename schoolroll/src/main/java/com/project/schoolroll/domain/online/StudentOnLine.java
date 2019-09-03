package com.project.schoolroll.domain.online;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student_on_line", comment = "线上学生信息")
@Table(name = "student_on_line", indexes = {
        @Index(columnList = "student_id", name = "student_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentOnLine extends Entitys implements Serializable {

    @Id
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生Id(学号代码)'")
    private String studentId;
    /**
     * 姓名
     */
    @Column(name = "student_name", columnDefinition = "VARCHAR(32) COMMENT '学生姓名'")
    private String studentName;
    /**
     * 性别
     * 男/女
     */
    @Column(name = "gender", columnDefinition = "VARCHAR(32) COMMENT '性别'")
    private String gender;
    /**
     * 身份证号
     */
    @Column(name = "stu_id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号'")
    private String stuIDCard;
    /**
     * 联系电话
     */
    @Column(name = "stu_phone", columnDefinition = "VARCHAR(32) COMMENT '联系电话'")
    private String stuPhone;
    /**
     * 班级id
     */
    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '班级id'")
    private String classId;
    /**
     * 班级名称
     */
    @Column(name = "className", columnDefinition = "VARCHAR(32) COMMENT '班级名称'")
    private String className;
    /**
     * 入学时间(年/月)
     */
    @Column(name = "enrollment_date", columnDefinition = "VARCHAR(32) COMMENT '入学时间(年/月)'")
    private String enrollmentDate;
    /**
     * 民族
     * 汉族
     * 满族
     * 其他
     */
    @Column(name = "nation", columnDefinition = "VARCHAR(32) COMMENT '民族'")
    private String nation;

    /**
     * 学生信息 0 表格导入, 1 手动添加
     */
    @Column(name = "import_status", columnDefinition = "TINYINT(2) COMMENT '0 导入, 1 手动添加'")
    private int importStatus;
}