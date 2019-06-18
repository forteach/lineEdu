package com.project.classfee.repository;

import com.project.classfee.domain.ClassStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费标准
 */
@Repository("classStandardRepository")
public interface  ClassStandardRepository extends JpaRepository<ClassStandard, String>, JpaSpecificationExecutor<ClassStandardRepository> {

}
