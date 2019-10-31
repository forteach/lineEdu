package com.project.course.service;

import com.project.course.domain.record.ChapterRecords;
import com.project.course.domain.record.CourseRecords;
import com.project.course.web.req.CourseRecordsSaveReq;
import org.springframework.data.domain.Page;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 10:53
 * @version: 1.0
 * @description:
 */
public interface CourseRecordsService {

    void saveCourseRecord(CourseRecordsSaveReq req);

    void saveChapterRecord(CourseRecordsSaveReq req);

    ChapterRecords findChapterRecordsByStudentIdAndChapterId(String studentId, String courseId, String chapterId);

    CourseRecords findCourseRecordsByStudentIdAndCourseId(String studentId, String courseId);

    Page<CourseRecords> findCourseByStudentId(String studentId, int page, int size);

    Page<ChapterRecords> findCourseByCourseIdAndStudentId(String studentId, String courseId, int page, int size);
}