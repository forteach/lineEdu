package com.project.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.user.domain.Teacher;
import com.project.user.domain.TeacherFile;
import com.project.user.domain.TeacherVerify;
import com.project.user.repository.TeacherFileRepository;
import com.project.user.repository.TeacherRepository;
import com.project.user.repository.TeacherVerifyRepository;
import com.project.user.repository.dto.TeacherDto;
import com.project.user.service.TeacherService;
import com.project.user.service.UserService;
import com.project.user.web.vo.RegisterTeacherVo;
import com.project.user.web.vo.TeacherVerifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

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
    private final UserService userService;
    private final TeacherFileRepository teacherFileRepository;
    private final TeacherVerifyRepository teacherVerifyRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherVerifyRepository teacherVerifyRepository,
                              UserService userService, TeacherFileRepository teacherFileRepository) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.teacherFileRepository = teacherFileRepository;
        this.teacherVerifyRepository = teacherVerifyRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherVerify save(TeacherVerify teacherVerify) {
        teacherVerify.setTeacherId(teacherVerify.getPhone());
        teacherVerify.setVerifyStatus(VERIFY_STATUS_APPLY);
        return teacherVerifyRepository.save(teacherVerify);
        // 添加到用户保存用户
//        RegisterTeacherVo vo = new RegisterTeacherVo();
//        BeanUtil.copyProperties(teacher, vo);
//        userService.registerTeacher(vo);
//        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherVerify update(TeacherVerify teacherVerify) {
        Optional<TeacherVerify> optionalTeacher = teacherVerifyRepository.findById(teacherVerify.getTeacherId());
        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "没有要修改的数据");
        TeacherVerify t = optionalTeacher.get();
        String centerId = t.getCenterAreaId();
        BeanUtil.copyProperties(teacherVerify, t);
        t.setVerifyStatus(VERIFY_STATUS_APPLY);
        t.setCenterAreaId(centerId);

//        if (StrUtil.isNotBlank(teacherVerify.getPhone()) && !t.getPhone().equals(teacherVerify.getPhone())) {
//            userService.updateTeacher(t.getPhone(), teacherVerify.getPhone(), teacherVerify.getUpdateUser());
//        }

        return teacherVerifyRepository.save(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyTeacher(TeacherVerifyVo vo) {
        Optional<TeacherVerify> optionalTeacher = teacherVerifyRepository.findById(vo.getTeacherId());
        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "没有要修改的数据");
        TeacherVerify teacherVerify = optionalTeacher.get();
        if (VERIFY_STATUS_AGREE.equals(vo.getVerifyStatus())) {
            //同意修改的内容去覆盖原来的
            teacherVerify.setVerifyStatus(VERIFY_STATUS_AGREE);
            teacherVerify.setIsValidated(TAKE_EFFECT_OPEN);
            Teacher teacher = new Teacher();
            Optional<Teacher> teacherOptional = teacherRepository.findById(vo.getTeacherId());
            if (teacherOptional.isPresent()) {
                teacher = teacherOptional.get();
                if (!teacher.getPhone().equals(teacherVerify.getPhone())) {
                    userService.updateTeacher(teacher.getPhone(), teacherVerify.getPhone(), teacherVerify.getUpdateUser());
                }
            } else {
//              添加到用户保存用户
                RegisterTeacherVo registerTeacherVo = new RegisterTeacherVo();
                BeanUtil.copyProperties(teacherVerify, registerTeacherVo);
                userService.registerTeacher(registerTeacherVo);
            }
            BeanUtil.copyProperties(teacherVerify, teacher);
            teacher.setUpdateTime(DateUtil.now());
            teacherRepository.save(teacher);
        }
        if (StrUtil.isNotBlank(vo.getRemark())) {
            teacherVerify.setRemark(vo.getRemark());
        }
        teacherVerify.setVerifyStatus(vo.getVerifyStatus());
        teacherVerify.setUpdateTime(DateUtil.now());
        teacherVerifyRepository.save(teacherVerify);
        //修改教师信息对应的资料信息
        updateTeacherFileIsValidated(vo.getTeacherId(), vo.getVerifyStatus());
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateTeacherFileIsValidated(String teacherId, String isValidated) {
        List<TeacherFile> list = teacherFileRepository.findAllByTeacherId(teacherId).stream().peek(t -> t.setIsValidated(isValidated)).collect(toList());
        teacherFileRepository.saveAll(list);
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
        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "不存在对应的教师信息");
        return optionalTeacher.get();
    }

    /**
     * 管理端通过状态查询的教师信息
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<TeacherDto> findAllPageDto(PageRequest pageRequest) {
        return teacherVerifyRepository.findAllByDto(pageRequest);
    }

    @Override
    public Page<TeacherDto> findAllPageDtoByVerifyStatus(String verifyStatus, PageRequest pageRequest) {
        return teacherVerifyRepository.findAllByVerifyStatusEqualsDto(verifyStatus, pageRequest);
    }

    @Override
    public Page<TeacherDto> findAllPageDtoByVerifyStatusAndCenterAreaId(String verifyStatus, String centerAreaId, PageRequest pageRequest) {
        return teacherVerifyRepository.findAllByVerifyStatusEqualsAndCenterAreaIdDto(verifyStatus, centerAreaId, pageRequest);
    }

    /**
     * 学习中心查询的教师信息
     *
     * @param centerAreaId
     * @param pageRequest
     * @return
     */
    @Override
    public Page<TeacherDto> findAllPageByCenterAreaIdDto(String centerAreaId, PageRequest pageRequest) {
        return teacherVerifyRepository.findAllByCenterAreaIdDto(centerAreaId, pageRequest);
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
        teacherFile.setIsValidated(TAKE_EFFECT_CLOSE);
        return teacherFileRepository.save(teacherFile);
    }

    @Override
    public List<TeacherFile> findTeacherFile(String teacherId) {
        return teacherFileRepository.findAllByTeacherId(teacherId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeacherFile(String fileId) {
        teacherFileRepository.deleteById(fileId);
    }

    @Override
    public void updateStatus(String teacherId, String userId) {
        Optional<Teacher> optional = teacherRepository.findById(teacherId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0013, "不存在要修改的教师信息");
        optional.ifPresent(t -> {
            String status = t.getIsValidated();
            if (TAKE_EFFECT_CLOSE.equals(status)) {
                t.setIsValidated(TAKE_EFFECT_OPEN);
                userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_OPEN, userId);
            } else {
                t.setIsValidated(TAKE_EFFECT_CLOSE);
                userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_CLOSE, userId);
            }
            t.setUpdateUser(userId);
            teacherRepository.save(t);
        });
    }
}