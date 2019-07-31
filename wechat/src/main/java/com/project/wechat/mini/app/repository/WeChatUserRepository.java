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
     * @param stuId
     * @return
     */
    @Transactional(readOnly = true)
    List<WeChatUser> findByStuId(String stuId);

    /**
     * 查询用户信息
     *
     * @param openId
     * @return
     */
//    @Query(value = " select " +
//            " w.stuId as stuId, " +
//            " w.classId as classId, " +
//            " c.className as className, " +
//            " s.stuName as stuName, " +
//            " s.portrait as portrait " +
//            " from WeChatUser as w " +
//            " left join Classes as c on w.classId = c.classId " +
//            " left join StudentEntitys as s on w.stuId = s.id " +
//            " where w.isValidated = '0' and w.openId = ?1 ")
//    @Transactional(readOnly = true)
//    IWeChatUser findByIsValidatedEqualsAndOpenId(String openId);
}
