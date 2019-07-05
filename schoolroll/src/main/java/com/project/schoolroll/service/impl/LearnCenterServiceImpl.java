package com.project.schoolroll.service.impl;

import com.project.schoolroll.dto.LearnCenterDto;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.service.LearnCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<LearnCenterDto> findAll() {
        return learnCenterRepository.findAllByIsValidatedEquals();
    }
}
