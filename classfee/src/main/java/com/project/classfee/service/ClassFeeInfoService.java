package com.project.classfee.service;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.domain.ClassStandard;
import com.project.classfee.repository.ClassFeeInfoRepository;
import com.project.classfee.repository.ClassFeeRepository;
import com.project.classfee.repository.ClassFeeYearRepository;
import com.project.classfee.repository.ClassStandardRepository;
import com.project.mysql.service.BaseMySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 课时详情信息导入，创建课时费管理、年度课时费汇总、初始记录
 */

@Slf4j
@Service
public class ClassFeeInfoService extends BaseMySqlService {

    //课时详情信息
    @Resource
    private ClassFeeInfoRepository classFeeInfoRepository;

    //年度课时费汇总
    @Resource
    private ClassFeeYearRepository classFeeYearRepository;

    //课时费管理
    @Resource
    private ClassFeeRepository classFeeRepository;


    /**
     * 添加课时费
     * @param classFeeId
     * @param fullName       姓名
     * @param createYear     年份
     * @param createMonth    月份
     * @param specialtyIds   专业
     * @param classFee     课时费标准
     * @param classCount  课时
     * @param centerId
     * @return
     */
    public ClassFeeInfo save(String classFeeId, String fullName, String createYear, String createMonth, String specialtyIds, int classFee, int classCount,String centerId){
        ClassFeeInfo classFeeInfo=new ClassFeeInfo(IdUtil.fastSimpleUUID(),classFeeId,fullName,createYear,createMonth,specialtyIds,classFee,classCount,centerId);
        return classFeeInfoRepository.save(classFeeInfo);
    }

    /**
     * 课时费列表导入添加
     */
    private int saveAllInfo(List<ClassFeeInfo> list){

        return classFeeInfoRepository.saveAll(list).size();
    }


    public ClassFeeInfo findId(String standardId){
        Optional<ClassFeeInfo> obj=classFeeInfoRepository.findById(standardId);
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
    public Page<ClassFeeInfo> findAllPage(String centerAreaId,String createYear, Pageable pageable) {
        List<String> paramName= Arrays.asList("centerAreaId","createYear");
        Map<String,String> paramValue=new HashMap<String,String>();
        paramValue.put("centerAreaId",centerAreaId);
        paramValue.put("createYear",createYear);
        return classFeeInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecificationEqu(root, criteriaBuilder,paramName, paramValue);
        }, pageable);
    }




}
