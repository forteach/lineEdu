package com.project.wechat.mini.app.repository;

import com.project.wechat.mini.app.domain.WeChatUser;
import com.project.wechat.mini.app.dto.IWeChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 查询用户信息
     *
     * @param openId
     * @return
     */
//    @Query(value = " select " +
//            " w.studentId as studentId, " +
//            " s.studentName as studentName, " +
//            " w.avatarUrl as portrait, " +
//            " s.classId as classId, " +
//            " s.className as className " +
//            " from WeChatUser as w " +
//            " left join Student as s on w.studentId = s.studentId " +
//            " where w.isValidated = '0' and s.isValidated = '0' and w.openId = ?1 ")
//    @Transactional(readOnly = true)
//    IWeChatUser findByIsValidatedEqualsAndOpenId(String openId);

    @Query(value = "select " +
            " w.studentId as studentId, " +
            " s.studentName as studentName, " +
            " w.avatarUrl as portrait, " +
            " s.classId as classId, " +
            " s.className as className," +
            " s.centerAreaId as centerAreaId " +
            " from WeChatUser as w " +
            " left join StudentOnLine as s on s.studentId = w.studentId " +
            " where w.isValidated = '0' and s.isValidated = '0' and w.openId = ?1 ")
    @Transactional(readOnly = true)
    IWeChatUser findAllByIsValidatedEqualsAndOpenId(String openId);
}