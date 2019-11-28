package com.project.user.domain;

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

/**
 * 培训班级资料管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teacher_file", comment = "教师信息资料管理")
@Table(name = "teacher_file", indexes = {
        @Index(columnList = "file_id", name = "file_id_index"),
        @Index(columnList = "teacher_id", name = "teacher_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '教师资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(255) COMMENT '资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '资料URL'")
    private String fileUrl;

    @Column(name = "teacher_id", columnDefinition = "VARCHAR(40) COMMENT '教师信息id'")
    private String teacherId;
}