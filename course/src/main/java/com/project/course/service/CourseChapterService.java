package com.project.course.service;


import com.project.course.domain.CourseChapter;
import com.project.course.repository.dto.ICourseChapterDto;
import com.project.course.web.req.CourseChapterEditReq;
import com.project.course.web.resp.CourseChapterSaveResp;
import com.project.course.web.resp.CourseTreeResp;
import com.project.course.web.vo.ChapterDataFileVo;
import com.project.course.web.vo.ChapterSortVo;
import com.project.course.web.vo.CourseChapterVo;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 17:09
 * @Version: 1.0
 * @Description: 科目章节操作service
 */
public interface CourseChapterService {

    public CourseChapterSaveResp save(CourseChapter courseChapter);

    public CourseChapterSaveResp edit(CourseChapterEditReq courseChapterEditReq);

    public CourseChapterSaveResp getCourseChapterById(String chapterId);

    void updateChapterSort(List<ChapterSortVo> list, String userId);

    public void delete(CourseChapter courseChapter);

    public void deleteById(String chapterId);

    public void deleteIsValidById(String chapterId);

    public List<CourseTreeResp> findByCourseId(String courseId, String studentId);

    public List<ICourseChapterDto> findByChapterParentId(String isValidated, String chapterParentId);

    public List<ICourseChapterDto> findAllCourseChapter(CourseChapterVo vo);

    void saveChapterDataList(String courseId, String courseName, String chapterParentId, ChapterDataFileVo files, String teacherName, String centerName, String userId, String centerId);

    void deleteAllByCourseId(String courseId);

//    void verifyCourse(CourseChapterVerifyVo verifyVo);
}
