package com.project.course.web.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-11 16:58
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseChapterEditReq implements Serializable {

    private String chapterId;

    private String chapterName;

    private String publish;

    private Integer randomQuestionsNumber;
}