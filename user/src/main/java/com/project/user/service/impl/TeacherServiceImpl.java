package com.project.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.user.domain.Teacher;
import com.project.user.domain.TeacherFile;
import com.project.user.repository.SysUsersRepository;
import com.project.user.repository.TeacherFileRepository;
import com.project.user.repository.TeacherRepository;
import com.project.user.repository.dto.TeacherDto;
import com.project.user.service.TeacherService;
import com.project.user.service.UserService;
import com.project.user.web.vo.RegisterTeacherVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:55
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final SysUsersRepository sysUsersRepository;
    private final UserService userService;
    private final TeacherFileRepository teacherFileRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              UserService userService, TeacherFileRepository teacherFileRepository,
                              SysUsersRepository sysUsersRepository) {
        this.teacherRepository = teacherRepository;
        this.sysUsersRepository = sysUsersRepository;
        this.userService = userService;
        this.teacherFileRepository = teacherFileRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Teacher save(Teacher teacher) {
        teacher.setTeacherId(teacher.getPhone());
        // 添加到用户保存用户
        RegisterTeacherVo vo = new RegisterTeacherVo();
        BeanUtil.copyProperties(teacher, vo);
        userService.registerTeacher(vo);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Teacher update(Teacher teacher) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacher.getTeacherId());
        if (optionalTeacher.isPresent()) {
            Teacher t = optionalTeacher.get();
            BeanUtil.copyProperties(teacher, t);
            if (StrUtil.isNotBlank(teacher.getPhone()) && !t.getPhone().equals(teacher.getPhone())) {
                userService.updateTeacher(t.getPhone(), teacher.getPhone(), teacher.getUpdateUser());
            }
            return teacherRepository.save(teacher);
        }
        MyAssert.isNull(null, DefineCode.ERR0014, "没有要修改的数据");
        return null;
    }

    @Override
    public Page<Teacher> findAllPageByCenterAreaId(String centerAreaId, PageRequest pageRequest) {
        return teacherRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageRequest);
    }

    @Override
    public List<Teacher> findAllByCenterAreaId(String centerAreaId) {
        return teacherRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTeacherCode(String teacherCode) {
        teacherRepository.deleteTeacherByTeacherCode(teacherCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByTeacherId(String teacherId) {
        teacherRepository.findById(teacherId).ifPresent(t -> {
            t.setIsValidated(TAKE_EFFECT_CLOSE);
            teacherRepository.save(t);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTeacherId(String teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Teacher findById(String teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            return optionalTeacher.get();
        }
        MyAssert.isNull(null, DefineCode.ERR0014, "不存在对应的教师信息");
        return null;
    }

    @Override
    public Page<TeacherDto> findAllPageDto(PageRequest pageRequest) {
        return teacherRepository.findAllDto(pageRequest);
    }

    @Override
    public Page<TeacherDto> findAllPageByCenterAreaIdDto(String centerAreaId, PageRequest pageRequest) {
        return teacherRepository.findAllByCenterAreaIdDto(centerAreaId, pageRequest);
    }

    @Async
    @Override
    public void updateState(String teacherId, String status, String userId) {
        teacherRepository.findById(teacherId).ifPresent(t -> {
            t.setIsValidated(status);
            t.setUpdateUser(userId);
            teacherRepository.save(t);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherFile saveFile(TeacherFile teacherFile) {
        return teacherFileRepository.save(teacherFile);
    }
    @Override
    public List<TeacherFile> findTeacherFile(String teacherId){
        return teacherFileRepository.findAllByIsValidatedEqualsAndTeacherId(TAKE_EFFECT_OPEN, teacherId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeacherFile(String fileId){
        teacherFileRepository.deleteById(fileId);
    }

    @Override
    public void updateStatus(String teacherId, String userId) {
        Optional<Teacher> optional = teacherRepository.findById(teacherId);
        if (optional.isPresent()){
            optional.ifPresent(t ->{
                String status = t.getIsValidated();
                if (TAKE_EFFECT_CLOSE.equals(status)){
                    t.setIsValidated(TAKE_EFFECT_OPEN);
                    userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_OPEN, userId);
                }else {
                    t.setIsValidated(TAKE_EFFECT_CLOSE);
                    userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_CLOSE, userId);
                }
                t.setUpdateUser(userId);
                teacherRepository.save(t);
            });
        }else {
            MyAssert.isNull(null, DefineCode.ERR0013, "不存在要修改的教师信息");
        }
    }
}