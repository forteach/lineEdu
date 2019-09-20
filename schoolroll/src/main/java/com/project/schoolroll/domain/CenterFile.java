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
 * @date: 19-9-20 18:34
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "center_file", comment = "学习中心资料管理")
@Table(name = "center_file", indexes = {
        @Index(columnList = "file_id", name = "file_id_index"),
        @Index(columnList = "center_id", name = "center_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CenterFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(60) COMMENT '资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '资料URL'")
    private String fileUrl;

    @Column(name = "file_type", columnDefinition = "VARCHAR(32) COMMENT '文件类型'")
    private String fileType;

    @Column(name = "center_id", columnDefinition = "VARCHAR(32) COMMENT '学习中心编号'")
    private String centerId;
}