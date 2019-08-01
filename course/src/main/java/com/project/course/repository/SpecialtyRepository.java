package com.project.course.repository;

import com.project.course.domain.Specialty;
import com.project.course.repository.dto.SpecialtyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 16:52
 * @version: 1.0
 * @description:
 */
public interface SpecialtyRepository extends JpaRepository<Specialty, String> {

    @Transactional(readOnly = true)
    public Page<Specialty> findAllByIsValidatedEquals(String isValidated, Pageable pageable);

    /**
     * 查询有效专业信息
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Query(value = "SELECT specialtyId AS specialtyId, specialtyName AS specialtyName FROM Specialty WHERE isValidated = '0' ")
    public List<SpecialtyDto> findAllByIsValidatedEqualsDto();

    @Transactional(readOnly = true)
    public Specialty findBySpecialtyName(String specialtyName);

    /**
     * 分页查询生效科目信息
     *
     * @param isValidated
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    Page<Specialty> findByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    /**
     * 根据名字和有效状态查询课程信息
     * @param isValidated
     * @param specialtyName
     * @return
     */
    @Transactional(readOnly = true)
    Specialty findByIsValidatedEqualsAndSpecialtyName(String isValidated, String specialtyName);
}
