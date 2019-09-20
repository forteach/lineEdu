package com.project.schoolroll.repository;

import com.project.schoolroll.domain.CenterFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 18:38
 * @version: 1.0
 * @description:
 */
public interface CenterFileRepository extends JpaRepository<CenterFile, String> {
    List<CenterFile> findAllByIsValidatedEqualsAndCenterAreaId(String isValidated, String centerAreaId);
    @Modifying(flushAutomatically = true)
    int deleteAllByCenterAreaId(String centerAreaId);
}
