package com.project.schoolroll.service;

import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.repository.dto.LearnCenterDto;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:38
 * @version: 1.0
 * @description:
 */
public interface LearnCenterService {
    /**
     * 查询全部有效学习中心
     *
     * @return
     */
    public List<LearnCenterDto> findAllDto();

    public void removeById(String centerId);

    void saveFile(List<CenterFile> files, String centerId, String userId);

    void deleteByFileId(String fileId);

    long deleteAllFilesByFileIds(List<String> fileIds);

    List<CenterFile> findAll(String centerId);

//    void updateStatus(String centerId, String userId);
}
