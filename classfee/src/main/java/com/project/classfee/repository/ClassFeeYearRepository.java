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

}
