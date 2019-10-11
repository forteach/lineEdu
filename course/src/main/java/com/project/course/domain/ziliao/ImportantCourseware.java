package com.project.course.domain.ziliao;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 重要课件资料（教案、课件）图集类型除外
 */
@Data
@Entity
@Table(name = "important_courseware", indexes = {
        @Index(columnList = "chapter_id", name = "chapter_id_index"),
        @Index(name = "file_id_index", columnList = "file_id")
})
@EqualsAndHashCode(callSuper = true)
public class ImportantCourseware extends AbsFile implements Serializable {

    /**
     * 重要类型分类 1教案 2课件
     */
    @Column(name = "important_type", columnDefinition = "VARCHAR(1) COMMENT '重要资料类型 1教案　2课件　'")
    private String importantType;

    @Column(name = "video_time", columnDefinition = "VARCHAR(32) COMMENT '视频时间长度(单位秒)'")
    private String videoTime;
}