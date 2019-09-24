package com.project.user.service;

import com.project.user.domain.Teacher;
import com.project.user.domain.TeacherFile;
import com.project.user.repository.dto.TeacherDto;
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

    public Page<Teacher> findAllPageByCenterAreaId(String centerAreaId, PageRequest pageRequest);

    public List<Teacher> findAllByCenterAreaId(String centerAreaId);

    public void deleteByTeacherCode(String teacherCode);

    public void removeByTeacherId(String teacherId);

    public void deleteByTeacherId(String teacherId);

    public Teacher findById(String teacherId);

    public Page<TeacherDto> findAllPageDto(PageRequest pageRequest);

    public Page<TeacherDto> findAllPageByCenterAreaIdDto(String centerAreaId, PageRequest pageRequest);

    void updateState(String teacherId, String status, String userId);

    TeacherFile saveFile(TeacherFile teacherFile);

    List<TeacherFile> findTeacherFile(String teacherId);

    void deleteTeacherFile(String fileId);

    void updateStatus(String teacherId, String userId);
}