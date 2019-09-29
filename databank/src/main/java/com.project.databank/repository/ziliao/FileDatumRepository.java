package com.project.databank.repository.ziliao;

import com.project.databank.domain.ziliao.FileDatum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-19 10:33
 * @Version: 1.0
 * @Description:　文档资料库
 */
public interface FileDatumRepository extends IDatumRepoitory<FileDatum, String> {
    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE FileDatum set isValidated = :isValidated WHERE courseId = :courseId and chapterId = :chapterId")
    public int updateIsValidated(String isValidated, String courseId, String chapterId);
}