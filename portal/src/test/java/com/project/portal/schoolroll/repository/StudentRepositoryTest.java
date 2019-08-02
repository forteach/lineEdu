package com.project.portal.schoolroll.repository;

import cn.hutool.core.util.StrUtil;
import com.project.schoolroll.domain.Student;
import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.domain.StudentPeople;
import com.project.schoolroll.repository.StudentExpandRepository;
import com.project.schoolroll.repository.StudentPeopleRepository;
import com.project.schoolroll.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-2 09:41
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentPeopleRepository studentPeopleRepository;
    @Autowired
    private StudentExpandRepository studentExpandRepository;

    @Test
    public void trimTest(){
        List<Student> listStudent = studentRepository.findAll()
                .parallelStream()
                .filter(Objects::nonNull)
                .map(s -> {
                    s.setStuId(StrUtil.trim(s.getStuId()));
                    return s;
                }).collect(Collectors.toList());
        studentRepository.saveAll(listStudent);

        List<StudentPeople> listPeople = studentPeopleRepository.findAll()
                .parallelStream()
                .filter(Objects::nonNull)
                .map(s -> {
                    s.setStuIDCard(StrUtil.trim(s.getStuIDCard()));
                    return s;
                }).collect(Collectors.toList());
        studentPeopleRepository.saveAll(listPeople);

        List<StudentExpand> listExpand = studentExpandRepository.findAll()
                .parallelStream()
                .filter(Objects::nonNull)
                .map(s -> {
                    s.setStuId(StrUtil.trim(s.getStuId()));
                    return s;
                }).collect(Collectors.toList());
        studentExpandRepository.saveAll(listExpand);
    }
}
