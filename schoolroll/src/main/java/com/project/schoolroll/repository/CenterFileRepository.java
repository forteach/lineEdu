package com.project.schoolroll.repository;

import com.project.schoolroll.domain.CenterFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 18:38
 * @version: 1.0
 * @description:
 */
public interface CenterFileRepository extends JpaRepository<CenterFile, String> {
    @Transactional(readOnly = true)
    List<CenterFile> findAllByIsValidatedEqualsAndCenterId(String isValidated, String centerId);

//    @Modifying(flushAutomatically = true)
//    int deleteAllByCenterAreaId(String centerAreaId);

//    @Modifying(flushAutomatically = true)
//    long deleteAllByFileIdIn(List<String> fileIds);

    @Transactional(readOnly = true)
    List<CenterFile> findAllByCenterId(String centerId);
}