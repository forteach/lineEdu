package com.project.user.domain;

import cn.hutool.core.util.StrUtil;
import com.project.mysql.domain.Entitys;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;

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
@Builder
@org.hibernate.annotations.Table(appliesTo = "teacher_verify", comment = "教师审核信息")
@Table(name = "teacher_verify", indexes = {
        @Index(columnList = "teacher_id", name = "teacher_id_index"),
        @Index(columnList = "teacher_code", name = "teacher_code_index"),
        @Index(columnList = "phone", name = "phone_index")
})
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVerify extends Entitys implements Serializable {
    @Id
    @Column(name = "teacher_id", columnDefinition = "VARCHAR(32) COMMENT '教师id'")
    private String teacherId;
    @Column(name = "teacher_name", columnDefinition = "VARCHAR(32) COMMENT '教师名称'")
    private String teacherName;
    @Column(name = "teacher_code", columnDefinition = "VARCHAR(32) COMMENT '教师代码'")
    private String teacherCode;
    @Column(name = "gender", columnDefinition = "VARCHAR(32) COMMENT '性别'")
    private String gender;
    @Column(name = "birth_date", columnDefinition = "VARCHAR(32) COMMENT '出生年月'")
    private String birthDate;
    @Column(name = "id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号'")
    private String idCard;
    @Column(name = "professional_title", columnDefinition = "VARCHAR(60) COMMENT '现任专业技术职务'")
    private String professionalTitle;
    @Column(name = "professional_title_date", columnDefinition = "VARCHAR(32) COMMENT '现任专业技术职务取得时间'")
    private String professionalTitleDate;
    @Column(name = "position", columnDefinition = "VARCHAR(32) COMMENT '工作单位及职务'")
    private String position;
    @Column(name = "industry", columnDefinition = "VARCHAR(32) COMMENT '所在行业'")
    private String industry;
    @Column(name = "email", columnDefinition = "VARCHAR(32) COMMENT '邮箱地址'")
    private String email;
    @Column(name = "phone", columnDefinition = "VARCHAR(32) COMMENT '联系电话'")
    private String phone;
    @Column(name = "specialty", columnDefinition = "VARCHAR(32) COMMENT '专业'")
    private String specialty;
    @Column(name = "is_full_time", columnDefinition = "VARCHAR(32) COMMENT '是否全日制(Y/N)'")
    private String isFullTime;
    @Column(name = "academic_degree", columnDefinition = "VARCHAR(32) COMMENT '学位'")
    private String academicDegree;
    @Column(name = "bank_card_account", columnDefinition = "VARCHAR(32) COMMENT '银行卡账户'")
    private String bankCardAccount;
    @Column(name = "bank_card_bank", columnDefinition = "VARCHAR(32) COMMENT '银行卡开户行'")
    private String bankCardBank;
    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;
    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;

    /**
     * 学习中心名称
     */
    @Column(name = "center_name", columnDefinition = "VARCHAR(64) COMMENT '学习中心名称'")
    private String centerName;
}