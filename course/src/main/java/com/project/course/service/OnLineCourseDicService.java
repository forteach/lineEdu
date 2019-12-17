package com.project.course.service;

import com.project.course.domain.OnLineCourseDic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 15:32
 * @Version: 1.0
 * @Description:
 */
public interface OnLineCourseDicService {
    public OnLineCourseDic save(OnLineCourseDic onLineCourseDic);

    public OnLineCourseDic update(OnLineCourseDic onLineCourseDic);

    public OnLineCourseDic findId(String courseId);

//    public List<OnLineCourseDic> findAllByCenterAreaId(String centerAreaId);

    public List<OnLineCourseDic> findAll();

    public void removeByCourseId(String courseId);

    public void deleteByCourseId(String courseId);

    void updateStatus(String courseId, String userId);

    Page<OnLineCourseDic> findAllPage(PageRequest pageRequest);

    Page<OnLineCourseDic> findAllPageByType(PageRequest pageRequest, String type);
}