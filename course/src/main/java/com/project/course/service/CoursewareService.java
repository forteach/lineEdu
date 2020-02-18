package com.project.course.service;


import com.project.course.web.req.CoursewareAll;
import com.project.course.web.req.ImpCoursewareAll;
import com.project.databank.web.vo.CourseVerifyRequest;

import java.util.List;
import java.util.Set;

public interface CoursewareService {

    /**
     * 保存除图集以外，主要课件文件信息
     *
     * @param obj
     * @return
     */
    void saveFile(ImpCoursewareAll obj, String centerId);

    void deleteCourseChapterId(String chapterId);

    List<CoursewareAll> findByChapterId(String chapterId);

    List<CoursewareAll> findByChapterIdAndVerifyStatus(String courseId, String chapterId, String userId);

    void updateVerifyCourseware(CourseVerifyRequest request);

    int findVideoTimeSum(String courseId);

    void deleteBathByChapterIds(Set<String> stringSet, String courseId);

    void deleteByCourseId(String courseId);
}