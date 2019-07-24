package com.project.schoolroll.service;

import java.io.OutputStream;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:48
 * @Version: 1.0
 * @Description:
 */
public interface ExportService {
    public void exportStudentTemplate();

    OutputStream exportStudents();
}
