package com.project.schoolroll.repository;

import com.project.schoolroll.domain.online.TbClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 20:08
 * @version: 1.0
 * @description:
 */
public interface TbClassesRepository extends JpaRepository<TbClasses, String> {

    @Transactional(readOnly = true)
    public Optional<TbClasses> findByClassNameAndCenterAreaId(String className, String centerAreaId);

    @Transactional(readOnly = true)
    public List<TbClasses> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId);

}
