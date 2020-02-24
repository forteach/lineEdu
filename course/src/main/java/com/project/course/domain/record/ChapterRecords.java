package com.project.course.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.course.domain.pk.ChapterRecordsPk;
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
 * @date: 19-8-5 10:25
 * @version: 1.0
 * @description: 学习章节信息记录
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "chapter_records", comment = "学生上课章节记录")
@Table(name = "chapter_records", indexes = {
        @Index(columnList = "student_id", name = "student_id_index"),
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "chapter_id", name = "chapter_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ChapterRecordsPk.class)
public class ChapterRecords extends AbstractRecord implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private ChapterRecordsPk chapterRecordsPk;

    private String studentId;

    private String courseId;

    private String chapterId;

    @Column(name = "video_duration", columnDefinition = "INT(11) DEFAULT 0 COMMENT '视频时长(单位秒)'", nullable = false)
    private Integer videoDuration = this.videoDuration == null ? 0 : this.videoDuration;

    @Column(name = "location_time", columnDefinition = "VARCHAR(32) COMMENT '观看位置时间'")
    private String locationTime;
}