package com.project.schoolroll.service;

import java.io.InputStream;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:18
 * @Version: 1.0
 * @Description:
 */
public interface ImportService {

    void studentsExcel07Reader(InputStream inputStream);

    void studentsExcel03Reader(InputStream inputStream);
}
