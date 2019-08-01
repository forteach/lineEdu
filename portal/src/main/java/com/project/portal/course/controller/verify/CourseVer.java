package com.project.portal.course.controller.verify;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.course.request.CourseSaveReq;
import org.springframework.stereotype.Component;

@Component
public class CourseVer {

    public static void saveValide(CourseSaveReq req) {
        MyAssert.isNull(req.getCourse(), DefineCode.ERR0010, "课程信息未填写");
        MyAssert.isNull(req.getCourse().getCourseName(), DefineCode.ERR0010, "课程名称不能为空");
        MyAssert.isNull(req.getCourse().getCourseNumber(), DefineCode.ERR0010, "课程编号不能为空");

    }

}
