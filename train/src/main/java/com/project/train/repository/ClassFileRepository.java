package com.project.train.repository;

import com.project.train.domain.ClassFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目班级资料管理
 */
@Repository("classFileRepository")
public interface ClassFileRepository extends JpaRepository<ClassFile, String>, JpaSpecificationExecutor<ClassFile> {

    //所有的文件列表
    public Page<ClassFile> findAllByCenterAreaIdOrderByCreateTimeDesc(String centerId, Pageable pageable);

    //班级所有的文件列表
    public Page<ClassFile> findAllByCenterAreaIdAndClassIdOrderByCreateTimeDesc(String centerId, String classId, Pageable pageable);

    /**
     * 根据计划编号查询对应的班级资料分页信息
     * @param pjPlanId
     * @param pageable
     * @return
     */
    public Page<ClassFile> findAllByPjPlanIdOrderByCreateTimeDesc(String pjPlanId, Pageable pageable);

}
