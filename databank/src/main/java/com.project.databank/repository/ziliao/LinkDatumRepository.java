package com.project.databank.repository.ziliao;

import com.project.databank.domain.ziliao.LinkDatum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-19 10:21
 * @Version: 1.0
 * @Description: 链接资料库
 */
public interface LinkDatumRepository extends IDatumRepoitory<LinkDatum, String> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE LinkDatum set isValidated = :isValidated WHERE courseId = :courseId and chapterId = :chapterId")
    int updateIsValidated(String isValidated, String courseId, String chapterId);
}
