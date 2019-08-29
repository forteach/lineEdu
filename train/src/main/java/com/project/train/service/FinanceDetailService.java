package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.FinanceDetail;
import com.project.train.repository.FinanceDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class FinanceDetailService extends BaseMySqlService {

    @Resource
    private FinanceDetailRepository financeDetailRepository;


    /**
     * 财务类型明细添加
     */
    public FinanceDetail save(FinanceDetail financeDetail) {
        financeDetail.setDetailId(IdUtil.fastSimpleUUID());
        String[] happenTime = StrUtil.split(financeDetail.getHappenTime(), "-");
        //发生年年
        financeDetail.setCreateYear(happenTime[0]);
        //发生月
        financeDetail.setCreateMonth(happenTime[1]);
        return financeDetailRepository.save(financeDetail);
    }

    /**
     * 财务类型明细修改
     */
    public FinanceDetail update(FinanceDetail financeDetail) {
        FinanceDetail obj = findId(financeDetail.getDetailId());
        BeanUtil.copyProperties(financeDetail, obj);
        return financeDetailRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param planId
     * @return
     */
    public FinanceDetail findId(String planId) {
        Optional<FinanceDetail> obj = financeDetailRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 获取前多少天项目计划列表
     * @param agoDay       前多少天
     * @param pageable
     * @return
     */
    public Page<FinanceDetail> findAllPage(String centerAreaId, int agoDay, Pageable pageable) {
        //提前天数的日期
        String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
        //当前日期
        return financeDetailRepository.findAllByCenterAreaIdAndCreateTimeAfterOrderByCreateTimeDesc(centerAreaId, fromDay, pageable);
    }

    /**
     * @param centerAreaId 获取所有项目计划财务明细列表
     * @param pageable
     * @return
     */
    public Page<FinanceDetail> findAllPage(String centerAreaId, Pageable pageable) {

        return financeDetailRepository.findAllByCenterAreaIdOrderByCreateTimeDesc(centerAreaId, pageable);
    }

    /**
     * @param pjPlanId 获取所有项目计划财务明细列表
     * @param agoDay
     * @param pageable
     * @return
     */
    public Page<FinanceDetail> findByPjPlandIdAllPage(String pjPlanId, int agoDay, Pageable pageable) {
        //提前天数的日期
        String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
        return financeDetailRepository.findAllByPjPlanIdAndCreateTimeAfterOrderByCreateTimeDesc(pjPlanId, fromDay, pageable);
    }


    public Page<FinanceDetail> findByPjPlandIdAllPage(String pjPlanId, Pageable pageable){
        return financeDetailRepository.findAllByPjPlanIdOrderByCreateTimeDesc(pjPlanId, pageable);
    }
}
