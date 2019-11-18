package com.project.base.common.keyword;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/30 15:07
 */
public class Dic {
    /**
     * isValidated 的默认值
     */
    public static final String TAKE_EFFECT_OPEN = "0";

    public static final String TAKE_EFFECT_CLOSE = "1";

    /**
     *学习中心类别 0 总部 1 其它分中心
     */
    public static final Integer CENTER_ROLE_ID_ADMIN = 0;
    public static final Integer CENTER_ROLE_ID = 1;

    /**申请审核状态 0 同意，1 申请 2 拒绝*/
    public static final String VERIFY_STATUS_AGREE = "0";
    public static final String VERIFY_STATUS_APPLY = "1";
    public static final String VERIFY_STATUS_REFUSE = "2";

    /**
     * 课程章节发布
     */
    public static final String PUBLISH_YES = "Y";
    /**
     * 课程章节不发布
     */
    public static final String PUBLISH_NO = "N";
    /**
     * 章节科目父节点ID
     */
    public static final String COURSE_CHAPTER_CHAPTER_PARENT_ID = "0";
    /**
     * 是当前树结构第一个
     */
    public static final String COURSE_CHAPTER_SORT = "1";
    /**
     * 所处的层级是2
     */
    public static final String COURSE_CHAPTER_LEVERL = "2";
    /**
     * 教师信息 roleId 0 studentId 1
     */
    public static final String WECHAT_ROLE_ID_TEACHER = "0";
    public static final String WECHAT_ROLE_ID_STUDENT = "1";


    /**
     * 1文档　2图册　3视频　4音频　5链接
     */
    public static final String COURSE_ZILIAO_FILE = "1";
    public static final String COURSE_ZILIAO_PHOTO = "2";
    public static final String COURSE_ZILIAO_VIEW = "3";
    public static final String COURSE_ZILIAO_AUDIO = "4";
    public static final String COURSE_ZILIAO_LINK = "5";

    /**
     * 学生信息前缀
     */
    public static final String STUDENT_ADO = "studentsData$";

    /**
     * 微信登录用户是否绑定信息标识 0 (绑定)　1(未绑定)
     */
    public final static String WX_INFO_BINDIND_0 = "0";
    public final static String WX_INFO_BINDIND_1 = "1";

    /**
     * 微信　token 信息
     */
    public final static String USER_PREFIX = "userToken$";


    /**
     * mongodb _id
     */
    public static final String MONGDB_ID = "_id";

    public static final String QUESTION_ID = "questionId$";
    public static final String QUESTION_CHAPTER = "questionChapter$";
    public static final String QUESTIONS_VERIFY = "questionsVerify";

    public static final String TEACH_PLAN_CLASS_COURSEVO = "teachPlanClassCourseVo$";

    public static final String PLAN_COURSE_STUDENT_STUDY = "studyCourse$";
    /**
     * 教学计划统计信息，计划完成 0,计划进行 1 (默认)
     */
    public static final int PLAN_STATUS_SUCCESS = 0;
    public static final int PLAN_STATUS_ONGOING = 1;
    /**
     * 是否统计学生成绩计划 0 计算过,1没有计算过
     */
    public static final int PLAN_COUNT_STATUS_SUCCESS = 0;
    public static final int PLAN_COUNT_STATUS_ONGOING = 1;
}