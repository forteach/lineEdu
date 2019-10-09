package com.project.databank.service;

import com.project.databank.domain.ziliao.AbsDatum;
import com.project.databank.web.res.DatumResp;
import com.project.databank.web.vo.CourseVerifyRequest;
import com.project.databank.web.vo.DataDatumVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-26 11:05
 * @Version: 1.0
 * @Description:
 */
public interface ChapteDataService {

    public String save(String courseId, String chapterId, String datumType, List<DataDatumVo> files, String createUser, String centerAreaId, String centerName, String teacherName);
    /**
     * 课程资料详细列表
     *
     * @param chapterId
     * @param datumType
     * @param pageable
     * @return
     */
    public List<DatumResp> findDatumList(String chapterId, String datumType, Pageable pageable, String isValidated);

    public List<DatumResp> findDatumList(String chapterId, Pageable pageable, String isValidated);

    List<? extends AbsDatum> findAllDatumByChapterId(String chapterId, String datumType);

    List<? extends AbsDatum> findAllDatumByChapterIdAndVerifyStatus(String chapterId, String datumType, String verifyStatus);

    void removeChapteDataList(String courseId, String chapterId, String datumType);

    void removeOne(String fileId, String datumType);

//    void verifyChapter(String chapterId, String userId, String verifyStatus);

    void verifyData(CourseVerifyRequest request, String datumType);
}