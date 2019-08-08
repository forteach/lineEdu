package com.project.flow.repository;

import com.project.flow.domain.LinkLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程连线管理
 */
@Repository("linkLineRepository")
public interface LinkLineRepository extends JpaRepository<LinkLine, String>, JpaSpecificationExecutor<LinkLine> {


}
