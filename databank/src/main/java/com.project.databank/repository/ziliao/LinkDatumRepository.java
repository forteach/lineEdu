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
    /**
     * 修改资料领域
     *
     * @param fileId
     * @param datumArea
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE LinkDatum p SET p.datumArea = :datumArea where p.fileId = :fileId")
    public void updateDatumArea(String fileId, String datumArea);

    /**
     * 修改教师分享
     *
//     * @param fileId
//     * @param teachShare
     */
//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE LinkDatum p SET p.teachShare = :teachShare where p.fileId = :fileId")
//    public void updateTeachShare(String fileId, String teachShare);

    /**
     * 修改学生可见
     *
//     * @param fileId
//     * @param stuShare
     */
//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE LinkDatum p SET p.stuShare = :stuShare where p.fileId = :fileId")
//    public void updateStuShare(String fileId, String stuShare);
}
