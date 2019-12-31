package com.project.schoolroll.web.vo;

import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-31 16:05
 * @version: 1.0
 * @description:
 */
@Data
public class StudentScorePageAllVo {
    private String studentId;
    private String courseId;
    private String term;
    private String type;
    private String schoolYear;
    private String startDate;
    private String endDate;
}
