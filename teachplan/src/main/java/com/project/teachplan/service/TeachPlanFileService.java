package com.project.teachplan.service;

import com.project.teachplan.domain.TeachPlanFile;
import com.project.teachplan.repository.TeachPlanFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;
import static java.util.stream.Collectors.toList;

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
        teachPlanFile.setVerifyStatus(VERIFY_STATUS_APPLY);
        return teachPlanFileRepository.save(teachPlanFile);
    }

    public List<TeachPlanFile> findAllByPlanId(String planId) {
        return teachPlanFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId);
    }

    public Map<String, List<TeachPlanFile>> findAllByPlanIdGroupByType(String planId) {
        return groupByType(findAllByPlanId(planId));
    }

    public Map<String, List<TeachPlanFile>> findAllByPlanIdAndVerifyStatus(String planId, String verifyStatus) {
        return groupByType(teachPlanFileRepository.findAllByIsValidatedEqualsAndPlanIdAndVerifyStatusOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, planId, verifyStatus));
    }

    /** 将查出来的文件资料数据进行分组返回*/
    private Map<String, List<TeachPlanFile>> groupByType(List<TeachPlanFile> list) {
        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(TeachPlanFile::getType));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        teachPlanFileRepository.deleteById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        teachPlanFileRepository.deleteAllByPlanId(planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachPlan(String planId, String verifyStatus, String remark, String userId) {
        List<TeachPlanFile> list = findAllByPlanId(planId)
                .stream()
                .filter(Objects::nonNull)
                .peek(p -> {
                    p.setVerifyStatus(verifyStatus);
                    p.setRemark(remark);
                    p.setUpdateUser(userId);
                }).collect(toList());
        if (!list.isEmpty()) {
            teachPlanFileRepository.saveAll(list);
        }
    }
    @Transactional
    public void updateStatus(String planId, String status, String userId){
        List<TeachPlanFile> list = findAllByPlanId(planId).stream().filter(Objects::nonNull)
            .peek(p -> {
                p.setUpdateUser(userId);
                p.setIsValidated(status);
            }).collect(toList());
        if (!list.isEmpty()){
            teachPlanFileRepository.saveAll(list);
        }
    }
}