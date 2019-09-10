package com.project.teachplan.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.repository.TbClassesRepository;
import com.project.schoolroll.service.online.TbClassService;
import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.repository.PlanFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

/**
 * 班级资料
 */

@Slf4j
@Service
public class PlanFileService extends BaseMySqlService {

    private final PlanFileRepository planFileRepository;
    private final TbClassesRepository tbClassesRepository;
    private final TbClassService tbClassService;

    @Autowired
    public PlanFileService(TbClassesRepository tbClassesRepository, PlanFileRepository planFileRepository, TbClassService tbClassService) {
        this.planFileRepository = planFileRepository;
        this.tbClassesRepository = tbClassesRepository;
        this.tbClassService = tbClassService;
    }


    /**
     * 文件明细添加
     */
    @Transactional
    public PlanFile save(PlanFile classFile) {
        classFile.setFileId(IdUtil.fastSimpleUUID());
        planFileRepository.save(classFile);

        String planId = classFile.getPlanId();

        //计划班级数量
        int pCount = planFileRepository.countClass(planId);
        //添加班级学生的班级数量
        int cCount = countClass(planId);

//        //如果两个数量相等，改变计划完成情况的班级添加完成状态为1
//        if(pCount == cCount && (pCount != 0)){
//            TrainPlanFinish tf=tbClassesRepository.f(planId);
//            tf.setIsFile(1);
//            trainPlanFinishService.save(tf);
//        }

        //判断是否全部完善信息了
//        trainPlanFinishService.updateAll(planId);

        return classFile;
    }

    /**
     * 文件修改
     */
    public PlanFile update(PlanFile planFile) {
        PlanFile obj = findId(planFile.getFileId());
        BeanUtil.copyProperties(planFile, obj);
        return planFileRepository.save(obj);
    }


    /**
     * 文件 findById
     *
     * @param fileId
     * @return ClassFile
     */
    public PlanFile findId(String fileId) {
        Optional<PlanFile> obj = planFileRepository.findById(fileId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.orElse(new PlanFile());
    }

    /**
     * @param centerAreaId 获取文件列表
     * @param classId
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<PlanFile> findAllPage(String centerAreaId, String classId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndCenterAreaIdAndClassIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, classId, pageable);
    }


    /**
     * @param centerAreaId 获取文件列表
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<PlanFile> findAllPage(String centerAreaId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageable);
    }


    public Page<PlanFile> findByPjPlanIdPageAll(String pjPlanId, Pageable pageable) {
        return planFileRepository.findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pjPlanId, pageable);
    }

    /**
     * 返回计划下的班级文件资料数量
     *
     * @param planId
     * @return
     */
    public int countClass(String planId) {
        return planFileRepository.countClass(planId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByClassId(String classId) {
        List<PlanFile> list = planFileRepository.findAllByIsValidatedEqualsAndClassId(TAKE_EFFECT_OPEN, classId)
                .stream()
                .peek(classFile -> classFile.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        planFileRepository.saveAll(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByPjPlanId(String pjPlanId) {
        List<PlanFile> list = planFileRepository.findAllByIsValidatedEqualsAndPlanId(TAKE_EFFECT_OPEN, pjPlanId)
                .stream()
                .peek(classFile -> classFile.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        planFileRepository.saveAll(list);
    }
}