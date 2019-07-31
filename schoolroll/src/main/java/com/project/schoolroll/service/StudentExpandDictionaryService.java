package com.project.schoolroll.service;

import com.project.schoolroll.domain.StudentExpandDictionary;
import com.project.schoolroll.repository.dto.StudentExpandDictionaryDto;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-30 16:19
 * @version: 1.0
 * @description:
 */
public interface StudentExpandDictionaryService {

    public List<StudentExpandDictionaryDto> findDto();

    public List<StudentExpandDictionary> findAll();
}
