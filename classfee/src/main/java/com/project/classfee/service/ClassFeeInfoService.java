package com.project.classfee.service;


import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassFee;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.domain.ClassFeeYear;
import com.project.classfee.repository.ClassFeeInfoRepository;
import com.project.classfee.repository.ClassFeeRepository;
import com.project.classfee.repository.ClassFeeYearRepository;
import com.project.mysql.service.BaseMySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
     * 导入的课时文件
     * @param roms
     * @param year   年份
     * @param month  月份
     * @param centerId  中心ID
     * @return
     */
    @Transactional
    public boolean impFile(List<ClassFeeInfo> roms,String year,String month,String centerId){

        //获得文件里面的专业信息
        List<String> sepcialtyIds=filterSpecialty(roms);

        //判断和执行导入数据,并初始化年度和月份数据
        sepcialtyIds.stream().forEach(id->isExecImp(year,month,id,centerId));

        //保存课时文件记录
        int saveCount= saveAllInfo(roms);
        MyAssert.isNull(saveCount>0,DefineCode.ERR0012,"导入课时信息记录失败！");

        //汇总导入的数据课时和课时费

        return true;
    }

    //判断和执行导入数据,并初始化年度和月份数据
    private boolean isExecImp(String year,String month,String sepcialtyId,String centerId){

        //创建年度学年汇总记录
        boolean isExists= classFeeYearRepository.existsByFeeYearIdAndSpecialtyIdsAndCenterAreaId(year,sepcialtyId,centerId);
        if(!isExists){
            ClassFeeYear classFeeYear=new ClassFeeYear();
            classFeeYear.setFeeYearId(year);
            classFeeYear.setSpecialtyIds(sepcialtyId);
            classFeeYear.setOutState("N");  //未超出
            classFeeYearRepository.save(classFeeYear);
        }

        //创建该月份的课时费管理记录
        //创建年度学年汇总记录
        int feeMonth=Integer.parseInt(month);
        isExists= classFeeRepository.existsByFeeYearIdAndCenterAreaIdAndCreateMonth(year,centerId,feeMonth);
        if(!isExists) {
            ClassFee classFee = new ClassFee();
            classFee.setFeeYearId(year);
            classFee.setSpecialtyIds(sepcialtyId);
            classFee.setCreateMonth(feeMonth);
            classFee.setBalanceState("no");  //未结算
            classFeeRepository.save(classFee);
        }
        return true;
    }


    //过滤文件包含的专业信息
    private List<String> filterSpecialty(List<ClassFeeInfo> roms){
        return  roms.stream().map(obj->obj.getSpecialtyIds()).distinct().collect(Collectors.toList());
    }


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
