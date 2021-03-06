package com.project.databank.repository.ziliao;

import com.project.databank.domain.ziliao.AudioDatum;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-19 10:52
 * @Version: 1.0
 * @Description: 音频资料库
 */
public interface AudioDatumRepository extends IDatumRepoitory<AudioDatum, String> {

//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE AudioDatum set isValidated = :isValidated WHERE courseId = :courseId and chapterId = :chapterId")
//    int updateIsValidated(String isValidated, String courseId, String chapterId);
}