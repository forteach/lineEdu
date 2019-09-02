package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.ClassFile;
import com.project.train.repository.ClassFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 班级资料
 */

@Slf4j
@Service
public class ClassFileService extends BaseMySqlService {

    @Resource
    private ClassFileRepository classFileRepository;

    @Resource
    private TrainPlanFinishService trainPlanFinishService;

    /**
     * 文件明细添加
     */
    @Transactional
    public ClassFile save(ClassFile classFile) {
        classFile.setFileId(IdUtil.fastSimpleUUID());
         classFileRepository.save(classFile);

        //判断是否全部完善信息了
        String pjPlanId=classFile.getPjPlanId();
        trainPlanFinishService.updateAll(pjPlanId);

        return classFile;
    }

    /**
     * 文件修改
     */
    public ClassFile update(ClassFile classFile) {
        ClassFile obj = findId(classFile.getFileId());
        BeanUtil.copyProperties(classFile, obj);
        return classFileRepository.save(obj);
    }


    /**
     * 文件 findById
     * @param fileId
     * @return ClassFile
     */
    public ClassFile findId(String fileId) {
        Optional<ClassFile> obj = classFileRepository.findById(fileId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.orElse(new ClassFile());
    }

    /**
     * @param centerAreaId 获取文件列表
     * @param classId
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<ClassFile> findAllPage(String centerAreaId, String classId, Pageable pageable) {

        return classFileRepository.findAllByCenterAreaIdAndClassIdOrderByCreateTimeDesc(centerAreaId, classId, pageable);
    }


    /**
     * @param centerAreaId 获取文件列表
     * @param pageable
     * @return Page<ClassFile>
     */
    public Page<ClassFile> findAllPage(String centerAreaId, Pageable pageable) {

        return classFileRepository.findAllByCenterAreaIdOrderByCreateTimeDesc(centerAreaId, pageable);
    }


    public Page<ClassFile> findByPjPlanIdPageAll(String pjPlanId, Pageable pageable){
        return classFileRepository.findAllByPjPlanIdOrderByCreateTimeDesc(pjPlanId, pageable);
    }
}
