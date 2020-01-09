package com.project.train.repository;

import com.project.train.domain.TrainClassStu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 培训项目班级学生管理
 */
@Repository("trainClassStuRepository")
public interface TrainClassStuRepository extends JpaRepository<TrainClassStu, String>, JpaSpecificationExecutor<TrainClassStu> {

    //获得计划下的班级成员信息
    public Page<TrainClassStu> findByPjPlanIdOrderByCreateTimeDesc(String pjPlanId, Pageable pageable);

    boolean existsByPjPlanIdAndStuName(String pjPlanId, String stuName);

    //获得班级下的班级成员信息
    public Page<TrainClassStu> findByTrainClassId(String classId, Pageable pageable);

    public Page<TrainClassStu> findAllByCenterAreaIdAndPjPlanIdAndCreateTimeAfterOrderByCreateTimeDesc(String centerId, String pjPlanId, String form, Pageable pageable);

    public Page<TrainClassStu> findAllByCreateTimeAfterOrderByCreateTimeDesc(String form, Pageable pageable);

    public Page<TrainClassStu> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    //获得计划下面的班级数量
    @Query(value = "select count(*) from (select distinct train_class_id from train_class_stu where  is_validated = '0'AND pj_plan_id=?1) as t", nativeQuery = true)
    public int countClass(String pjPlanId);
}
