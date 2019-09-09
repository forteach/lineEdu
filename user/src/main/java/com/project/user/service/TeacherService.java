package com.project.user.service;

import com.project.user.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:56
 * @version: 1.0
 * @description:
 */
public interface TeacherService {
    public Teacher save(Teacher teacher);

    public Teacher update(Teacher teacher);

    public Page<Teacher> findAllPage(PageRequest pageRequest);

    public List<Teacher> findAll();

    public void deleteByTeacherCode(String teacherCode);

    public void removeByTeacherId(String teacherId);

    public void deleteByTeacherId(String teacherId);
}