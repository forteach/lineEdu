package com.project.schoolroll.service.online;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.TbClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class TbClassService {
    private final TbClassesRepository tbClassesRepository;

    @Autowired
    public TbClassService(TbClassesRepository tbClassesRepository) {
        this.tbClassesRepository = tbClassesRepository;
    }

    TbClasses getClassIdByClassName(String className, String centerAreaId){
           return tbClassesRepository.findByClassNameAndCenterAreaId(className, centerAreaId)
                   .orElse(tbClassesRepository.save(new TbClasses(centerAreaId, IdUtil.simpleUUID(), className)));
    }


    public TbClasses findClassByClassId(String classId){
        Optional<TbClasses> optionalTbClasses = tbClassesRepository.findById(classId);
        if (optionalTbClasses.isPresent()){
            return optionalTbClasses.get();
        }else {
            MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应班级信息");
            return null;
        }
    }

    public List<TbClasses> findAllByCenterAreaId(String centerAreaId){
        return tbClassesRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId);
    }
}
