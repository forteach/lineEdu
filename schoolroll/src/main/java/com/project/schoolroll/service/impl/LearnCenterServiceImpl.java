package com.project.schoolroll.service.impl;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.repository.CenterFileRepository;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.repository.dto.LearnCenterDto;
import com.project.schoolroll.service.LearnCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:43
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class LearnCenterServiceImpl implements LearnCenterService {
    private final LearnCenterRepository learnCenterRepository;
    private final CenterFileRepository centerFileRepository;

    public LearnCenterServiceImpl(LearnCenterRepository learnCenterRepository, CenterFileRepository centerFileRepository) {
        this.learnCenterRepository = learnCenterRepository;
        this.centerFileRepository = centerFileRepository;
    }

    @Override
    public List<LearnCenterDto> findAllDto() {
        return learnCenterRepository.findAllByIsValidatedEquals();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(String centerId) {
        learnCenterRepository.findById(centerId).ifPresent(learnCenter -> {
            learnCenter.setIsValidated(TAKE_EFFECT_CLOSE);
            learnCenterRepository.save(learnCenter);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(List<CenterFile> files, String centerId, String userId) {
        List<CenterFile> fileList = files.stream()
                .map(f -> {
                    f.setCenterId(centerId);
                    f.setFileId(IdUtil.fastSimpleUUID());
                    f.setCreateUser(userId);
                    f.setUpdateUser(userId);
                    f.setCenterAreaId(centerId);
                    return f;
                }).collect(toList());
        centerFileRepository.saveAll(fileList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        centerFileRepository.deleteById(fileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteAllFilesByFileIds(List<String> fileIds){
        return centerFileRepository.deleteAllByFileIdIn(fileIds);
    }

    @Override
    public List<CenterFile> findAll(String centerId) {
        return centerFileRepository.findAllByIsValidatedEqualsAndCenterAreaId(TAKE_EFFECT_OPEN, centerId);
    }
//
//    @Override
//    public void updateStatus(String centerId, String userId) {
//        learnCenterRepository.findById(centerId).ifPresent(c -> {
//            String centerName = c.getCenterName();
//            String status = c.getIsValidated();
//            if (TAKE_EFFECT_CLOSE.equals(status)){
//
//            }
//        });
//        MyAssert.isNull(null, DefineCode.ERR0014, "不存在对应的学习中心");
//    }
}