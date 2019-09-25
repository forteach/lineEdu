package com.project.teachplan.service;

import com.project.teachplan.domain.TeachPlanFile;
import com.project.teachplan.repository.TeachPlanFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-25 16:42
 * @version: 1.0
 * @description:
 */
@Service
public class TeachPlanFileService {
    private final TeachPlanFileRepository teachPlanFileRepository;

    public TeachPlanFileService(TeachPlanFileRepository teachPlanFileRepository) {
        this.teachPlanFileRepository = teachPlanFileRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public TeachPlanFile save(TeachPlanFile teachPlanFile) {
        return teachPlanFileRepository.save(teachPlanFile);
    }

    public List<TeachPlanFile> findAllByPlan(String planId) {
        return teachPlanFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        teachPlanFileRepository.deleteById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        teachPlanFileRepository.deleteAllByPlanId(planId);
    }
}