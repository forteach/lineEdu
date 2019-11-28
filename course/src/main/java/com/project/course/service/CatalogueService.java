package com.project.course.service;


import com.project.course.domain.Catalogue;
import com.project.course.web.req.CourseChapterEditReq;
import com.project.course.web.resp.CourseChapterSaveResp;
import com.project.course.web.resp.CourseTreeResp;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 17:09
 * @Version: 1.0
 * @Description: 科目章节操作service
 */
public interface CatalogueService {

    public CourseChapterSaveResp save(Catalogue courseChapter);

    public CourseChapterSaveResp edit(CourseChapterEditReq courseChapterEditReq);

    public CourseChapterSaveResp getCourseChapterById(String chapterId);

    public void delete(Catalogue courseChapter);

    public void deleteById(String chapterId);

    public void deleteIsValidById(String chapterId);

    public List<CourseTreeResp> findByCourseId(String courseId);


}
