package com.project.course.domain;


import com.project.course.domain.pk.CourseSpecialtyPk;
import com.project.mysql.domain.Entitys;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @Description: (课程专业对应)
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 11:01
 */
@Data
@Entity
@Builder
@Table(name = "courseSpecialty")
@IdClass(CourseSpecialtyPk.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseSpecialty extends Entitys {

    @EmbeddedId
    private CourseSpecialtyPk courseSpecialtyPk;

    private String courseId;

    private String specialtyId;

}
