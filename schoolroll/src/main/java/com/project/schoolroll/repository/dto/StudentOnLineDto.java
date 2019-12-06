package com.project.schoolroll.repository.dto;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-19 16:37
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "v_student_on_line_dto")
public class StudentOnLineDto implements Serializable {
    @Id
    @Column(length = 32, name = "student_id")
    public String studentId;

    /**
     * 姓名
     */
    @Column(length = 32, name = "student_name")
    public String studentName;

    /**
     * 性别
     * 男/女
     */
    @Column(length = 32, name = "gender")
    public String gender;

    /**
     * 身份证号
     */
    @Column(length = 32, name = "stu_id_card")
    public String stuIDCard;

    /**
     * 联系电话
     */
    @Column(length = 32, name = "stu_phone")
    public String stuPhone;

    /**
     * 班级id
     */
    @Column(length = 32, name = "class_id")
    public String classId;

    /**
     * 班级名称
     */
    @Column(length = 32, name = "class_name")
    public String className;

    /**
     * 入学时间(年/月)
     */
    @Column(length = 32, name = "enrollment_date")
    public String enrollmentDate;

    /**
     * 民族
     * 汉族
     * 满族
     * 其他
     */
    @Column(length = 32, name = "nation")
    public String nation;

    /**
     * 学习形式全日制非全日制
     */
    @Column(length = 32, name = "learning_modality")
    public String learningModality;

    /**
     * 学生信息 0 表格导入, 1 手动添加
     */
    @Column(length = 4, name = "import_status")
    public Integer importStatus;

    @Column(length = 32, name = "center_name")
    public String centerName;
    @Column(name = "is_validated", length = 1)
    public String isValidated;
    @Column(name = "center_area_id", length = 40)
    public String centerAreaId;
    @Column(name = "c_time", length = 32)
    public String createTime;

    /** 专业简称*/
    @Column(name = "specialty_name", columnDefinition = "VARCHAR(32) COMMENT '专业简称'")
    private String specialtyName;

    /** 年级 入学年的 也是学生级别 如： 2019级*/
    @Column(name = "grade", columnDefinition = "VARCHAR(32) COMMENT '年级'")
    private String grade;

    /**
     * 学制
     * 三年制，四年制，五年制，一年制
     */
    @Column(name = "educational_system", columnDefinition = "VARCHAR(32) COMMENT '学制'")
    private String educationalSystem;
}