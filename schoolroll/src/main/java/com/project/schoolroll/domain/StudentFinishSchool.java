package com.project.schoolroll.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/2 10:29
 * @version: 1.0
 * @description:
 */
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "student_finish_school", indexes = {
        @Index(columnList = "student_id", name = "student_id_index")
})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(comment = "学生是毕业信息", appliesTo = "student_finish_school")
public class StudentFinishSchool extends Entitys implements Serializable {

    @Id
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生Id'")
    public String studentId;

    @Column(name = "student_name", columnDefinition = "VARCHAR(32) COMMENT '学生姓名'")
    public String studentName;
    /**
     * 是否毕业 Y/N
     */
    @Column(name = "is_finish_school", columnDefinition = "CHAR(2) COMMENT '是否毕业 Y/N'")
    public String isFinishSchool;
    /**
     * 是否需要补考 Y/N
     */
    @Column(name = "is_makeup_examination", columnDefinition = "CHAR(3) COMMENT '是否需要补考 Y/N'")
    public String isMakeupExamination;

    public StudentFinishSchool(String isFinishSchool) {
        this.isFinishSchool = isFinishSchool;
    }
}