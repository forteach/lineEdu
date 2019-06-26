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
 * 学籍异动信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "school_roll_change", comment = "学籍异动信息")
@Table(name = "school_roll_change")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRoll_change extends Entitys {

    @Id
    @Column(name = "change_id", columnDefinition = "VARCHAR(32) COMMENT '异动ID'")
    private String changeId;

    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生学校编号'")
    private String studentId;

    @Column(name = "student_code", columnDefinition = "VARCHAR(32) COMMENT '学生学校编号'")
    private String studentCode;

    @Column(name = "type_id", columnDefinition = "VARCHAR(32) COMMENT '异动类型编号'")
    private String typeId;

    @Column(name = "type_name", columnDefinition = "VARCHAR(32) COMMENT '异动类型名称'")
    private String typeName;

    public SchoolRoll_change(String changeId,String studentId, String studentCode,String typeId,String typeName, String centerId) {
        this.changeId = changeId;
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.typeId = typeId;
        this.typeName = typeName;
        super.centerAreaId=centerId;
    }
}
