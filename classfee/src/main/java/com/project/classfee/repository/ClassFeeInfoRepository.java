package com.project.classfee.repository;

import com.project.classfee.domain.ClassFeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费管理
 */
@Repository("classFeeInfoRepository")
public interface ClassFeeInfoRepository extends JpaRepository<ClassFeeInfo, String>, JpaSpecificationExecutor<ClassFeeInfo> {

}
