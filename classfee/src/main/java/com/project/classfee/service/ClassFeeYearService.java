package com.project.classfee.service;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.classfee.domain.ClassFeeYear;
import com.project.classfee.repository.ClassFeeYearRepository;
import com.project.mysql.service.BaseMySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 课时添加及课时费信息记录
 */

@Slf4j
@Service
public class ClassFeeYearService extends BaseMySqlService {

    @Resource
    private ClassFeeYearRepository classFeeYearRepository;


    /**
     * 年度课时费汇总记录
     * @param createYear
     * @param classFeeSum
     * @param createMonth
     * @param classSum   课时数量'
     * @param outMonth   课时费超出那个月份超出
     * @param outFee     课时费超出金额
     * @param outState   课时费超出状态  Y 超出 N 未超出
     * @param centerId
     * @return
     */
    public ClassFeeYear save( String createYear,String specialtyIds, int classFeeSum, int createMonth, int classSum, int outMonth, int outFee,String outState, String centerId){
        ClassFeeYear classFeeInfo=new ClassFeeYear(IdUtil.fastSimpleUUID(),createYear,specialtyIds,classFeeSum,createMonth,classSum,outMonth,outFee,outState,centerId);
        return classFeeYearRepository.save(classFeeInfo);
    }


    /**
     * 年度课时费汇总记录
     * @param feeYearId
     * @param classFeeSum
     * @param classSum
     * @param outMonth
     * @param outFee
     * @param outState
     * @return
     */
    public ClassFeeYear update(String feeYearId,int classFeeSum, int classSum, int outMonth, int outFee,String outState){
        ClassFeeYear obj= findId(feeYearId);
        ClassFeeYear classFee=new ClassFeeYear();
        UpdateUtil.copyProperties(obj,classFee);
        classFee.setOutMonth(outMonth);
        classFee.setOutFee(outFee);
        classFee.setOutState(outState);
        return classFeeYearRepository.save(classFee);
    }

    public ClassFeeYear findId(String feeYearId){
        Optional<ClassFeeYear> obj=classFeeYearRepository.findById(feeYearId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }

    /**
     *
     * @param centerAreaId  教学中心
     * @param createYear    所属年份
     * @param pageable
     * @return
     */
    public Page<ClassFeeYear> findAllPage(String centerAreaId, String createYear, Pageable pageable) {
        List<String> paramName= Arrays.asList("centerAreaId","createYear");
        Map<String,String> paramValue=new HashMap<String,String>();
        paramValue.put("centerAreaId",centerAreaId);
        paramValue.put("createYear",createYear);
        return classFeeYearRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecificationEqu(root, criteriaBuilder,paramName, paramValue);
        }, pageable);
    }


}
