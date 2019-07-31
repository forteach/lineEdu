package com.project.schoolroll.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.repository.dto.StudentExpandDto;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.service.StudentExpandService;
import com.project.schoolroll.web.vo.StudentExpandVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 15:47
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class StudentExpandServiceImpl implements StudentExpandService {
    private final StudentExpandRepository studentExpandRepository;

    public StudentExpandServiceImpl(StudentExpandRepository studentExpandRepository) {
        this.studentExpandRepository = studentExpandRepository;
    }

    @Override
    public List<StudentExpandDto> findStudentExpandInfo(String stuId) {
        return studentExpandRepository.findByIsValidatedEqualsAndStuId(stuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAllStudentExpandByStuId(String stuId) {
        return studentExpandRepository.deleteAllByStuId(stuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String expandId) {
        studentExpandRepository.deleteById(expandId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdateStudentExpand(List<StudentExpandVo> studentExpandVos, String stuId) {
        if (studentExpandVos.size() > 0){
            studentExpandRepository.deleteAllByStuId(stuId);
        }
        studentExpandVos.parallelStream().filter(Objects::nonNull)
                .forEach(v -> {
                    // 删除学生对应的扩展字段
                    StudentExpand studentExpand = new StudentExpand();
                    BeanUtil.copyProperties(v, studentExpand);
                    studentExpand.setStuId(stuId);
                    studentExpand.setExpandId(IdUtil.fastSimpleUUID());
                    studentExpandRepository.save(studentExpand);
                });
    }
}
