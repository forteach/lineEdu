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
import java.io.Serializable;

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
public class SchoolRollChange extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 移动
     */
    @Id
    @Column(name = "change_id", columnDefinition = "VARCHAR(32) COMMENT '异动ID'")
    private String changeId;

    /**
     * 学生id
     */
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String studentId;

    /**
     * 学生编号
     */
    @Column(name = "student_code", columnDefinition = "VARCHAR(32) COMMENT '学生编号'")
    private String studentCode;

    /**
     * 移动类型编号
     */
    @Column(name = "type_id", columnDefinition = "VARCHAR(32) COMMENT '异动类型编号'")
    private String typeId;

    /**
     * 移动类型名称
     */
    @Column(name = "type_name", columnDefinition = "VARCHAR(32) COMMENT '异动类型名称'")
    private String typeName;
//
//    /**
//     * 异动状态
//     * 待处理,处理中,审核中,审核未通过
//     */
//    @Column(name = "status", columnDefinition = "VARCHAR(32) COMMENT '异动状态'")
//    private String status;
//    /**
//     * 审批人
//     */
//    @Column(name = "approver", columnDefinition = "VARCHAR(32) COMMENT '审批人'")
//    private String approver;
//    /**
//     * 审批意见
//     */
//    @Column(name = "comments", columnDefinition = "VARCHAR(32) COMMENT '审批意见'")
//    private String comments;
//    /**
//     * 审批说明
//     */
//    @Column(name = "approval_note", columnDefinition = "VARCHAR(255) COMMENT '审批说明'")
//    private String approvalNote;
//    /**
//     * 审批时间
//     */
//    @Column(name = "approval_date", columnDefinition = "VARCHAR(32) COMMENT '审批时间'")
//    private String approvalDate;

    public SchoolRollChange(String changeId, String studentId, String studentCode, String typeId, String typeName, String centerId) {
        this.changeId = changeId;
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.typeId = typeId;
        this.typeName = typeName;
        super.centerAreaId=centerId;
    }
}
