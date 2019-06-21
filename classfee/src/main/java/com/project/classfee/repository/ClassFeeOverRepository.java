package com.project.classfee.repository;

import com.project.classfee.domain.ClassFeeOver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费管理
 */
@Repository("classFeeOverRepository")
public interface ClassFeeOverRepository extends JpaRepository<ClassFeeOver, String>, JpaSpecificationExecutor<ClassFeeOver> {

}
