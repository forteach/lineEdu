package com.project.course.service;

import com.project.course.domain.CourseImages;
import com.project.databank.web.vo.DataDatumVo;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-31 19:54
 * @version: 1.0
 * @description:
 */
public interface CourseImagesService {

    public void saveCourseImages(String courseId, List<DataDatumVo> dataList, String createUser, String centerAreaId);

    public List<CourseImages> findImagesByCourseId(String courseId);

    public int deleteImagesByCourseId(String courseId);
}
