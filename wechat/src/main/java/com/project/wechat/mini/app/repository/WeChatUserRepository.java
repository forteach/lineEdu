package com.project.wechat.mini.app.repository;

import com.project.wechat.mini.app.domain.WeChatUser;
import com.project.wechat.mini.app.dto.IWeChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-10 17:10
 * @Version: 1.0
 * @Description:
 */
public interface WeChatUserRepository extends JpaRepository<WeChatUser, String> {
    /**
     * 根据微信账号查询绑定学生信息
     *
     * @param openId
     * @return
     */
    @Transactional(readOnly = true)
    List<WeChatUser> findByOpenId(String openId);

    /**
     * 根据学生id查询对应微信登录信息
     *
     * @param studentId
     * @return
     */
    @Transactional(readOnly = true)
    List<WeChatUser> findByStudentId(String studentId);

    @Query(value = "select " +
            " studentId as studentId, " +
            " studentName as studentName, " +
            " avatarUrl as portrait, " +
            " classId as classId, " +
            " className as className," +
            " centerAreaId as centerAreaId," +
            " roleId as roleId " +
            " from WeChatUser " +
            " where isValidated = '0' and openId = ?1 ")
    @Transactional(readOnly = true)
    Optional<IWeChatUser> findAllByIsValidatedEqualsAndOpenId(String openId);

    Optional<WeChatUser> findAllByOpenId(String openId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteAllByStudentId(String studentId);
}