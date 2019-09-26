package com.project.user.service;

import com.project.user.domain.Teacher;
import com.project.user.domain.TeacherFile;
import com.project.user.domain.TeacherVerify;
import com.project.user.repository.dto.TeacherDto;
import com.project.user.web.vo.TeacherVerifyVo;
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
    TeacherVerify save(TeacherVerify teacher);

    TeacherVerify update(TeacherVerify teacher);

    void verifyTeacher(TeacherVerifyVo vo);

    Page<Teacher> findAllPageByCenterAreaId(String centerAreaId, PageRequest pageRequest);

    List<Teacher> findAllByCenterAreaId(String centerAreaId);

    void deleteByTeacherCode(String teacherCode);

    public void removeByTeacherId(String teacherId);

    public void deleteByTeacherId(String teacherId);

    public Teacher findById(String teacherId);

    Page<TeacherDto> findAllPageDto(String isValidated, PageRequest pageRequest);

    Page<TeacherDto> findAllPageByCenterAreaIdDto(String centerAreaId, PageRequest pageRequest);

    void updateState(String teacherId, String status, String userId);

    TeacherFile saveFile(TeacherFile teacherFile);

    List<TeacherFile> findTeacherFile(String teacherId);

    void deleteTeacherFile(String fileId);

    void updateStatus(String teacherId, String userId);
}