package com.project.schoolroll.service.impl;

import com.project.schoolroll.domain.StudentExpandDictionary;
import com.project.schoolroll.repository.StudentExpandDictionaryRepository;
import com.project.schoolroll.repository.dto.StudentExpandDictionaryDto;
import com.project.schoolroll.service.StudentExpandDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-30 16:19
 * @version: 1.0
 * @description:
 */
@Service
public class StudentExpandDictionaryServiceImpl implements StudentExpandDictionaryService {
    private final StudentExpandDictionaryRepository studentExpandDictionaryRepository;

    public StudentExpandDictionaryServiceImpl(StudentExpandDictionaryRepository studentExpandDictionaryRepository) {
        this.studentExpandDictionaryRepository = studentExpandDictionaryRepository;
    }

    @Override
    public List<StudentExpandDictionaryDto> findDto(){
        return studentExpandDictionaryRepository.findByIsValidatedEquals(TAKE_EFFECT_OPEN);
    }

    @Override
    public List<StudentExpandDictionary> findAll() {
        return studentExpandDictionaryRepository.findAll();
    }
}
