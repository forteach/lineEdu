package com.project.classfee.service;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.classfee.domain.ClassStandard;
import com.project.classfee.repository.ClassStandardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class ClassStandardService {

    @Resource
    private ClassStandardRepository classStandardRepository;

    /**
     * 添加课时费标准
     * @param createYear
     * @param studentSum
     * @param studentSubsidies
     * @param subsidiesSum
     * @param class_fee
     * @return
     */
    public ClassStandard save(String createYear,int studentSum,int studentSubsidies,int subsidiesSum,int class_fee,String centerId){
        ClassStandard classStandard=new ClassStandard(IdUtil.fastSimpleUUID(),createYear,studentSum,studentSubsidies,subsidiesSum,class_fee,centerId);
        return classStandardRepository.save(classStandard);
    }

    /**
     * 修改课时费标准
     * @param standardId
     * @param createYear
     * @param studentSum
     * @param studentSubsidies
     * @param subsidiesSum
     * @param class_fee
     * @return
     */
    public ClassStandard update(String standardId,String createYear,int studentSum,int studentSubsidies,int subsidiesSum,int class_fee){
        ClassStandard obj= findId(standardId);
        ClassStandard classStandard=new ClassStandard();
        UpdateUtil.copyProperties(obj,classStandard);
        classStandard.setCreateYear(createYear);
        classStandard.setStudentSum(studentSum);
        classStandard.setStudentSubsidies(studentSubsidies);
        classStandard.setSubsidiesSum(subsidiesSum);
        classStandard.setClass_fee(class_fee);
        return classStandardRepository.save(classStandard);
    }

    public ClassStandard findId(String standardId){
        Optional<ClassStandard> obj=classStandardRepository.findById(standardId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }
}
