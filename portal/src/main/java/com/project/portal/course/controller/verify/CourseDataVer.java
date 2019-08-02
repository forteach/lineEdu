package com.project.portal.course.controller.verify;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.databank.request.ChapteDataReq;
import org.springframework.stereotype.Component;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-26 14:44
 * @version: 1.0
 * @description:
 */
@Component
public class CourseDataVer {

    /**
     * 校验更新
     *
     * @param chapteDataReq
     */
    public void updateAreaAndShare(ChapteDataReq chapteDataReq) {
        MyAssert.blank(chapteDataReq.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        MyAssert.blank(chapteDataReq.getCourseId(), DefineCode.ERR0010, "科目编号不为空");
//        MyAssert.blank(chapteDataReq.getKNodeId(), DefineCode.ERR0010, "知识点id不为空");
        MyAssert.blank(chapteDataReq.getFileId(), DefineCode.ERR0010, "资料编号不为空");
        MyAssert.blank(chapteDataReq.getDatumArea(), DefineCode.ERR0010, "资料领域不为空");
        MyAssert.blank(chapteDataReq.getDatumType(), DefineCode.ERR0010, "资料类型不为空");
//        MyAssert.blank(chapteDataReq.getTeachShare(), DefineCode.ERR0010, "教师共享不为空");
//        MyAssert.blank(chapteDataReq.getStuShare(), DefineCode.ERR0010, "学生共享不为空");
    }
}
