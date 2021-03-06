package com.project.teachplan.domain;

import cn.hutool.core.util.StrUtil;
import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;

/**
 * 培训班级资料管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan_file", comment = "教学计划资料管理")
@Table(name = "teach_plan_file", indexes = {
        @Index(columnList = "file_id", name = "file_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeachPlanFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(255) COMMENT '资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '资料URL'")
    private String fileUrl;

    @Column(name = "file_type", columnDefinition = "VARCHAR(32) COMMENT '文件类型'")
    private String fileType;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(40) COMMENT '在线项目计划编号'")
    private String planId;

    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;

    /**资料分类　教学大纲、教学计划、课程表、教材库*/
    @Column(name = "type", columnDefinition = "VARCHAR(32) COMMENT '资料类型，上传分类 A.教学大纲、B.教学计划、C.课程表 D, 教材库'")
    private String type;

    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus  = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;
}