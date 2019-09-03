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
 * 培训项目课程字典管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_course", comment = "培训项目课程字典管理")
@Table(name = "train_course", indexes = {@Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainCourse extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "course_id", columnDefinition = "VARCHAR(40) COMMENT '培训课程编号'")
    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '培训课程名称'")
    private String courseName;

    @Column(name = "train_area_id", columnDefinition = "VARCHAR(60) COMMENT '培训项目领域'")
    private String trainAreaId;

    public TrainCourse(String courseId, String courseName, String trainAreaId,String centerId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.trainAreaId = trainAreaId;
        super.centerAreaId=centerId;
    }
}
