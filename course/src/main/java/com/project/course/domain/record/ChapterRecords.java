package com.project.course.domain.record;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 10:25
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "chapter_records", comment = "学生上课章节记录")
@Table(name = "chapter_records", indexes = {
        @Index(columnList = "record_id", name = "record_id_index"),
        @Index(columnList = "student_id", name = "student_id_index"),
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "chapter_id", name = "chapter_id_index")
})
@EqualsAndHashCode(callSuper = true)
public class ChapterRecords extends AbstractRecord implements Serializable {

    @Column(name = "video_duration", columnDefinition = "INT(11) DEFAULT 0 COMMENT '视频时长(单位秒)'")
    private Long videoDuration = this.videoDuration == null ? 0 : this.videoDuration;

    @Column(name = "location_time", columnDefinition = "VARCHAR(32) COMMENT '观看位置时间'")
    private String locationTime;
}