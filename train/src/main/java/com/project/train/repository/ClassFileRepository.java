package com.project.train.repository;

import com.project.train.domain.ClassFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 培训项目班级资料管理
 */
@Repository("classFileRepository")
public interface ClassFileRepository extends JpaRepository<ClassFile, String>, JpaSpecificationExecutor<ClassFile> {

    //所有的文件列表
    public Page<ClassFile> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerId, Pageable pageable);

    //班级所有的文件列表
    public Page<ClassFile> findAllByIsValidatedEqualsAndCenterAreaIdAndClassIdOrderByCreateTimeDesc(String isValidated, String centerId, String classId, Pageable pageable);

    /**
     * 根据计划编号查询对应的班级资料分页信息
     * @param pjPlanId
     * @param pageable
     * @return
     */
    public Page<ClassFile> findAllByIsValidatedEqualsAndPjPlanIdOrderByCreateTimeDesc(String isValidated, String pjPlanId, Pageable pageable);

    //获得计划下面的班级数量
    @Query(value = "SELECT COUNT(*) FROM class_file WHERE is_validated = '0' AND class_id IN(SELECT DISTINCT class_id form class_file WHERE is_validated = '0' AND pj_plan_id=?1)", nativeQuery = true)
    public int countClass(String pjPlanId);

    @Transactional(readOnly = true)
    public List<ClassFile> findAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    public List<ClassFile> findAllByIsValidatedEqualsAndPjPlanId(String isValidated, String pjPlanId);
}
