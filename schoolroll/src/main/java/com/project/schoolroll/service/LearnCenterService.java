package com.project.schoolroll.service;

import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.repository.dto.LearnCenterDto;

import java.util.List;
import java.util.Map;

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

    void saveFile(CenterFile centerFile);

    void deleteByFileId(String fileId);

    Map<String, List<CenterFile>> findAll(String centerId);

    LearnCenter findByCenterId(String centerId);

    void updateFileStatus(String centerId, String status, String userId);
}