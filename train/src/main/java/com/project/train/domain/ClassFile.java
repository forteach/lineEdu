package com.project.train.domain;

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
 * 培训班级资料管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_file", comment = "培训班级资料管理")
@Table(name = "class_file", indexes = {@Index(columnList = "file_id", name = "file_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '培训资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(255) COMMENT '培训资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '培训资料URL'")
    private String fileUrl;

    @Column(name = "class_id", columnDefinition = "VARCHAR(60) COMMENT '培训班级编号'")
    private String classId;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;

}
