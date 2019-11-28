package com.project.databank.repository.ziliao;

import com.project.databank.domain.ziliao.ViewDatum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-19 10:16
 * @Version: 1.0
 * @Description: 视频资料库
 */
public interface ViewDatumRepository extends IDatumRepoitory<ViewDatum, String> {
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ViewDatum set isValidated = :isValidated WHERE courseId = :courseId and chapterId = :chapterId")
    int updateIsValidated(String isValidated, String courseId, String chapterId);

    @Transactional(readOnly = true)
    List<ViewDatum> findAllByIsValidatedEqualsAndCourseIdAndDatumTypeAndVerifyStatus(String isValidated, String courseId, String datumType, String verifyStatus);
}