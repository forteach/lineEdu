package com.project.databank.repository.ziliao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface IDatumRepoitory<T, ID> extends JpaRepository<T, ID> {

    @Transactional(readOnly = true)
    Page<T> findByIsValidated(String isValidated, Pageable pageable);

    /**
     * 根据有效状态可科目ID查询章节资料信息
     *
     * @param isValidated
     * @param chapterId
     * @return
     */
    @Transactional(readOnly = true)
    List<T> findByChapterIdAndIsValidated(String chapterId, String isValidated);

    /**
     * 通过章节Id和状态查询资料信息
     * @param chapterId
     * @param verifyStatus
     * @return
     */
    @Transactional(readOnly = true)
    List<T> findAllByChapterIdAndVerifyStatusOrderByCreateTimeDesc(String chapterId, String verifyStatus);

    @Transactional(readOnly = true)
    List<T> findAllByVerifyStatusOrderByCreateTimeDesc(String verifyStatus);

    /**
     * 查询chapterId
     * @param chapterId
     * @return
     */
    @Transactional(readOnly = true)
    List<T> findAllByChapterIdOrderByCreateTimeDesc(String chapterId);
    /**
     * 分页查询科目课程文件信息
     *
     * @param isValidated
     * @param chapterId
     * @return
     */
    @Transactional(readOnly = true)
    Page<T> findByChapterIdAndIsValidated(String chapterId, String isValidated, Pageable pageable);


    /**
     * 分页查询科目章节文件类型文件信息
     *
     * @param isValidated
     * @param chapterId
     * @return
     */
    @Transactional(readOnly = true)
    Page<T> findByChapterIdAndDatumTypeAndIsValidated(String chapterId, String datumType, String isValidated, Pageable pageable);

    /**
     * 多条件查询课程科目文件挂载
     *
     * @param specification
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    Page<T> findAll(Specification<T> specification, Pageable pageable);

    /**
     * 通过章节和课程id查询
     * @param chapterId
     * @param courseId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    List<T> findByChapterIdAndCourseId(String chapterId, String courseId);

    /**
     * 删除对应资料信息
     * @param chapterId
     * @param courseId
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    int deleteAllByCourseIdAndChapterId(String courseId, String chapterId);
}