package com.project.schoolroll.service.impl;

import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.repository.dto.LearnCenterDto;
import com.project.schoolroll.service.LearnCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;

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

    public LearnCenterServiceImpl(LearnCenterRepository learnCenterRepository) {
        this.learnCenterRepository = learnCenterRepository;
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
}
