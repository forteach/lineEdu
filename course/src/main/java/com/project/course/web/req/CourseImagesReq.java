package com.project.course.web.req;

import com.project.databank.web.vo.DataDatumVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-29 15:08
 * @Version: 1.0
 * @Description: 课程ID和图片
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseImagesReq implements Serializable {

    private String courseId;

    private List<DataDatumVo> images;

    private String createUser;

    private String centerAreaId;
}