package com.project.classfee.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassStandard;
import com.project.classfee.repository.ClassStandardRepository;
import com.project.mysql.service.BaseMySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
@Service
public class ClassStandardService extends BaseMySqlService {

    @Resource
    private ClassStandardRepository classStandardRepository;

    /**
     * 添加课时费标准
     * @param createYear
     * @param studentSum
     * @param studentSubsidies
     * @param subsidiesSum
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ClassStandard save(String createYear,String specialtyIds,int studentSum,int studentSubsidies,int subsidiesSum,String centerId, String createUser){
        ClassStandard classStandard=new ClassStandard(IdUtil.fastSimpleUUID(),createYear,specialtyIds,studentSum,studentSubsidies,subsidiesSum,centerId, createUser);
        return classStandardRepository.save(classStandard);
    }

    /**
     * 修改课时费标准
     * @param standardId
     * @param createYear
     * @param studentSum
     * @param studentSubsidies
     * @param subsidiesSum
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ClassStandard update(String standardId,String createYear,String specialtyIds,int studentSum,int studentSubsidies,int subsidiesSum, String updateUser){
        ClassStandard obj= findId(standardId);
        ClassStandard classStandard=new ClassStandard();
        BeanUtil.copyProperties(obj,classStandard);
        classStandard.setCreateYear(createYear);
        classStandard.setSpecialtyIds(specialtyIds);
        classStandard.setStudentSum(studentSum);
        classStandard.setStudentSubsidies(studentSubsidies);
        classStandard.setSubsidiesSum(subsidiesSum);
        classStandard.setUpdateUser(updateUser);
        return classStandardRepository.save(classStandard);
    }

    public ClassStandard findId(String standardId){
        Optional<ClassStandard> obj=classStandardRepository.findById(standardId);
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
    public Page<ClassStandard> findAllPage(String centerAreaId,String createYear, Pageable pageable) {
        List<String> paramName= Arrays.asList("centerAreaId","createYear");
        Map<String,String> paramValue=new HashMap<String,String>();
        paramValue.put("centerAreaId",centerAreaId);
        paramValue.put("createYear",createYear);
        return classStandardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecificationEqu(root, criteriaBuilder,paramName, paramValue);
        }, pageable);
    }




}
