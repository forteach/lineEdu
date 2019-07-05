package com.project.schoolroll.service;

import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
import com.project.schoolroll.web.vo.StudentVo;
import org.springframework.data.domain.Page;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-1 15:10
 * @version: 1.0
 * @description:
 */
public interface StudentService {

    Page<StudentVo> findStudentsPageAll(FindStudentDtoPageAllVo vo);
}
