package com.project.schoolroll.service.impl;

import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.repository.CenterFileRepository;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.repository.dto.LearnCenterDto;
import com.project.schoolroll.service.LearnCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public void saveFile(CenterFile centerFile) {
        centerFileRepository.save(centerFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        centerFileRepository.deleteById(fileId);
    }

    @Override
    public List<CenterFile> findAll(String centerId) {
        return centerFileRepository.findAllByIsValidatedEqualsAndCenterId(TAKE_EFFECT_OPEN, centerId);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileStatus(String centerId, String status, String userId) {
        List<CenterFile> centerFiles =centerFileRepository.findAllByCenterId(centerId).stream().filter(Objects::nonNull)
                .peek(c -> {
                    c.setUpdateUser(userId);
                    c.setIsValidated(status);
                }).collect(toList());
        centerFileRepository.saveAll(centerFiles);
    }
}