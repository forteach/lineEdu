package com.project.teachplan.service;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.teachplan.domain.TeachPlanFile;
import com.project.teachplan.repository.TeachPlanFileRepository;
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

    /**
     * 将查出来的文件资料数据进行分组返回
     */
    private Map<String, List<TeachPlanFile>> groupByType(List<TeachPlanFile> list) {
        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(TeachPlanFile::getType));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        Optional<TeachPlanFile> optional = teachPlanFileRepository.findById(fileId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在要删除的信息");
        String verifyStatus = optional.get().getVerifyStatus();
        MyAssert.isTrue(VERIFY_STATUS_AGREE.equals(verifyStatus), DefineCode.ERR0010, "已经审核过的信息不能删除");
        teachPlanFileRepository.deleteById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileIdAndAdmin(String fileId) {
        Optional<TeachPlanFile> optional = teachPlanFileRepository.findById(fileId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在要删除的信息");
        teachPlanFileRepository.deleteById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanId(String planId) {
        teachPlanFileRepository.deleteAllByPlanId(planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByPlanIdAndAdmin(String planId) {
        List<TeachPlanFile> lists = teachPlanFileRepository.findAllByPlanId(planId);
        lists.forEach(f -> MyAssert.isTrue(VERIFY_STATUS_AGREE.equals(f.getVerifyStatus()), DefineCode.ERR0010, "已经审核过的不能删除"));
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
    @Transactional(rollbackFor = Exception.class)
    public void verifyTeachPlanFile(String fileId, String verifyStatus, String remark, String userId){
        Optional<TeachPlanFile> optional = teachPlanFileRepository.findById(fileId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0010, "不存在信息");
        TeachPlanFile teachPlanFile = optional.get();
        teachPlanFile.setUpdateUser(userId);
        teachPlanFile.setVerifyStatus(verifyStatus);
        teachPlanFile.setRemark(remark);
        teachPlanFileRepository.save(teachPlanFile);
    }

    @Transactional
    public void updateStatus(String planId, String status, String userId) {
        List<TeachPlanFile> list = findAllByPlanId(planId).stream().filter(Objects::nonNull)
                .peek(p -> {
                    p.setUpdateUser(userId);
                    p.setIsValidated(status);
                }).collect(toList());
        if (!list.isEmpty()) {
            teachPlanFileRepository.saveAll(list);
        }
    }
}