package com.project.portal.databank.controller;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.databank.request.ChapteDataReq;
import org.springframework.stereotype.Component;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-26 14:55
 * @version: 1.0
 * @description:
 */
@Component
public class ChapterDataVerify {

    public void saveVerify(ChapteDataReq chapteDataReq) {
        MyAssert.elt(0, chapteDataReq.getFiles().size(), DefineCode.ERR0010, "文件对象不为空");
        verify(chapteDataReq);
    }


    private void verify(ChapteDataReq chapteDataReq) {
        MyAssert.blank(chapteDataReq.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        MyAssert.blank(chapteDataReq.getCourseId(), DefineCode.ERR0010, "科目编号不为空");
        MyAssert.blank(chapteDataReq.getDatumType(), DefineCode.ERR0010, "资料类型不为空");
    }

}
