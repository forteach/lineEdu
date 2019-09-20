package com.project.databank.service;

import com.project.databank.web.res.DatumResp;
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

    public String save(String courseId, String chapterId, String datumType, List<DataDatumVo> files, String createUser);
    /**
     * 课程资料详细列表
     *
     * @param chapterId
     * @param datumType
     * @param pageable
     * @return
     */
    public List<DatumResp> findDatumList(String chapterId, String datumType, Pageable pageable);

    public List<DatumResp> findDatumList(String chapterId, Pageable pageable);

    void removeChapteDataList(String courseId, String chapterId, String datumType);

    void removeOne(String fileId, String datumType);
}