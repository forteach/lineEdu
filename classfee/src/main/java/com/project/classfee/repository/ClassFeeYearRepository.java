package com.project.classfee.repository;

import com.project.classfee.domain.ClassFeeYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费管理
 */
@Repository("classFeeYearRepository")
public interface ClassFeeYearRepository extends JpaRepository<ClassFeeYear, String>, JpaSpecificationExecutor<ClassFeeYear> {

    //判断该年度的某个专业是否存在
    public boolean existsByFeeYearIdAndSpecialtyIdsAndCenterAreaId(String feeYearId,String specialtyIds,String centerId);

    //判断该年度的某个专业是否存在
    public ClassFeeYear findByFeeYearIdAndSpecialtyIdsAndCenterAreaId(String feeYearId,String specialtyIds,String centerId);
}
