package com.project.portal.course.controller.verify;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.course.request.CourseSaveReq;
import com.project.portal.course.vo.RCourse;
import org.springframework.stereotype.Component;

@Component
public class CourseVer {
    public static void saveValide(RCourse req) {
        MyAssert.isNull(req, DefineCode.ERR0010, "课程信息未填写");
        MyAssert.isNull(req.getCourseName(), DefineCode.ERR0010, "课程名称不能为空");
    }
}
