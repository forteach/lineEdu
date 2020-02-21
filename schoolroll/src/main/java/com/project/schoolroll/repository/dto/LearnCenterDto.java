package com.project.schoolroll.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:39
 * @version: 1.0
 * @description:
 */
public interface LearnCenterDto {
    /**
     * 学习中心id
     *
     * @return
     */
    public String getCenterId();

    /**
     * 学习中心名称
     *
     * @return
     */
    public String getCenterName();

    /**
     * 中心地址
     *
     * @return
     */
    public String getAddress();

    /**
     * 负责人
     *
     * @return
     */
    public String getPrincipal();

    /**
     * 联系电话
     *
     * @return
     */
    public String getPhone();

    /**
     * 银行账户
     *
     * @return
     */
    public String getBankingAccount();

    /**
     * 开户人
     *
     * @return
     */
    public String getAccountHolder();

    /**
     * 开户人电话
     *
     * @return
     */
    public String getAccountHolderPhone();

    /**
     * 开户行地址
     *
     * @return
     */
    public String getBankingAccountAddress();

    /**
     * 企业地址
     */
    public String getCompanyAddress();

    /**
     * 企业名称
     */
    public String getCompanyName();

    public String getSchoolPhone();
    public String getSchoolAdmin();
}