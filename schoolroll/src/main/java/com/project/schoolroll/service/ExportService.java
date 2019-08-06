package com.project.schoolroll.service;

import java.io.OutputStream;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:48
 * @Version: 1.0
 * @Description:
 */
public interface ExportService<T> {
    public List<List<String>> exportStudentTemplate();

    List<List<T>>  exportStudents();

    List<List<T>> exportStudents(List<String> strings);
}
