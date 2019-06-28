package com.project.classfee.service;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.classfee.domain.ClassFee;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.repository.ClassFeeInfoRepository;
import com.project.classfee.repository.ClassFeeRepository;
import com.project.classfee.repository.ClassStandardRepository;
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
public class classFeeService extends BaseMySqlService {

    @Resource
    private ClassFeeRepository classFeeRepository;


    /**
     * 课时费管理
     * @param createYear     创建年份
     * @param classFeeSum    课时费总金额
     * @param create_month  创建月份
     * @param classSum   课时总数量
     * @param balanceState  课时费结算状态 no 未结算，part部分结算、all全部结算
     * @param balanceSum  课时费已结算金额
     * @param centerId
     * @return
     */
    public ClassFee save( String createYear, int classFeeSum, int create_month, int classSum, int balanceState, int balanceSum, String centerId){
        ClassFee classFeeInfo=new ClassFee(IdUtil.fastSimpleUUID(),createYear,classFeeSum,balanceState,create_month,classSum,balanceSum,centerId);
        return classFeeRepository.save(classFeeInfo);
    }


    /**
     * 修改按月导入的课时费信息
     * @param classFeeId
     * @param calssFeeSum
     * @param classSum
     * @param balanceState
     * @param balanceSum
     * @return
     */
    public ClassFee update(String classFeeId,int classFeeSum, int classSum, int balanceState, int balanceSum){
        ClassFee obj= findId(classFeeId);
        ClassFee classFee=new ClassFee();
        UpdateUtil.copyProperties(obj,classFee);
        classFee.setClassFeeSum(classFeeSum);
        classFee.setClassSum(classSum);
        classFee.setBalanceState(balanceState);
        classFee.setBalanceSum(balanceSum);
        return classFeeRepository.save(classFee);
    }

    public ClassFee findId(String classFeeId){
        Optional<ClassFee> obj=classFeeRepository.findById(classFeeId);
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
    public Page<ClassFee> findAllPage(String centerAreaId,String createYear, Pageable pageable) {
        List<String> paramName= Arrays.asList("centerAreaId","createYear");
        Map<String,String> paramValue=new HashMap<String,String>();
        paramValue.put("centerAreaId",centerAreaId);
        paramValue.put("createYear",createYear);
        return classFeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecificationEqu(root, criteriaBuilder,paramName, paramValue);
        }, pageable);
    }




}
