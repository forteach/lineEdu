package com.project.classfee.repository;

import com.project.classfee.domain.ClassFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费管理
 */
@Repository("classFeeRepository")
public interface ClassFeeRepository extends JpaRepository<ClassFee, String>, JpaSpecificationExecutor<ClassFee> {

    public boolean existsByFeeYearIdAndCenterAreaIdAndCreateMonth(String feeYearId,String cengterId,int month);

    public ClassFee findByFeeYearIdAndCenterAreaIdAndCreateMonth(String feeYearId,String cengterId,int month);
}
