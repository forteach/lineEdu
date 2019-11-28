package com.project.course.domain.ziliao;

import cn.hutool.core.util.StrUtil;
import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;

/**
 * 重要课件信息父类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsFile extends Entitys {
    @Id
    @Column(name = "file_id", columnDefinition = "VARCHAR(32) COMMENT '文件编号'")
    private String fileId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "chapter_id", columnDefinition = "VARCHAR(32) COMMENT '章节编号'")
    public String chapterId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(255) COMMENT '文件名称'")
    public String fileName;

    @Column(name = "file_type", columnDefinition = "VARCHAR(10) COMMENT '文件扩展名'")
    public String fileType;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '文件URL'")
    public String fileUrl;

    @Column(name = "datum_type", columnDefinition = "VARCHAR(32) COMMENT '资料类型 1文档　3视频　4音频　5链接'")
    private String datumType;

    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;

    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;
}