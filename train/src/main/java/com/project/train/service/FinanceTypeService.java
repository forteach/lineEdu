package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.FinanceType;
import com.project.train.repository.FinanceTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class FinanceTypeService extends BaseMySqlService {

    @Resource
    private FinanceTypeRepository financeTypeRepository;


    /**
     * 财务类型明细添加
     */
    public FinanceType save(FinanceType financeType) {
        financeType.setFinanceTypeId(IdUtil.fastSimpleUUID());
        return financeTypeRepository.save(financeType);
    }

    /**
     * 财务类型明细修改
     */
    public FinanceType update(FinanceType financeType) {
        FinanceType obj = findId(financeType.getFinanceTypeId());
        BeanUtil.copyProperties(financeType, obj);
        return financeTypeRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param planId
     * @return
     */
    public FinanceType findId(String planId) {
        Optional<FinanceType> obj = financeTypeRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 财务类型明细，不分页
     * @return
     */
    public List<FinanceType> findAll(String centerAreaId) {

        return financeTypeRepository.findAllByCenterAreaId(centerAreaId);
    }


}
