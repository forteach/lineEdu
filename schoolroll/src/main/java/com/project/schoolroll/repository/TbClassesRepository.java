package com.project.schoolroll.repository;

import com.project.schoolroll.domain.online.TbClasses;
import org.springframework.data.jpa.repository.JpaRepository;

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

//    public boolean existsByClassName(String className);

    public Optional<TbClasses> findByClassName(String className);

    public List<TbClasses> findAllByIsValidatedEquals(String isValidated);

}
