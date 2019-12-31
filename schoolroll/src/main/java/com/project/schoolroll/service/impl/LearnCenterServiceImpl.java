package com.project.schoolroll.service.impl;

import cn.hutool.core.date.DateUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.repository.CenterFileRepository;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.repository.dto.LearnCenterDto;
import com.project.schoolroll.service.LearnCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
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
    public Map<String, List<CenterFile>> findAll(String centerId) {
        return centerFileRepository.findAllByIsValidatedEqualsAndCenterId(TAKE_EFFECT_OPEN, centerId).stream()
                .filter(Objects::nonNull).collect(Collectors.groupingBy(CenterFile::getType));
    }

    @Override
    public LearnCenter findByCenterId(String centerId) {
        Optional<LearnCenter> optional = learnCenterRepository.findById(centerId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0014, "不存在对应的学习中心信息");
        return optional.get();
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

    /**
     * 修改有效期已经到的学习中心状态
     * @return 需要修改的学习中心名称集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> findCenterListByEndDate(){
        List<LearnCenter> collect = learnCenterRepository
                .findAllByIsValidatedEqualsAndRoleIdAndEndDateBefore(TAKE_EFFECT_OPEN, CENTER_ROLE_ID, DateUtil.today())
                .stream()
                .filter(Objects::nonNull)
                .peek(c -> c.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        if (!collect.isEmpty()){
            learnCenterRepository.saveAll(collect);
        }
        return collect.stream().filter(Objects::nonNull).map(LearnCenter::getCenterName).collect(toList());
    }
}