package com.project.schoolroll.domain.online;

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
 * @date: 19-7-4 20:04
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "tb_class", comment = "班级信息")
@Table(name = "tb_class", indexes = {@Index(columnList = "class_id", name = "class_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TbClasses extends Entitys implements Serializable {
    @Id
    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '班级id'")
    private String classId;
    @Column(name = "class_name", columnDefinition = "VARCHAR(32) COMMENT '班级名称'")
    private String className;

    public TbClasses(String centerAreaId, String classId, String className, String userId) {
        super(userId, userId, centerAreaId);
        this.classId = classId;
        this.className = className;
    }
}
