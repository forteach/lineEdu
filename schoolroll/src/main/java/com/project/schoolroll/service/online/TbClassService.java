package com.project.schoolroll.service.online;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.TbClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class TbClassService {
    private final TbClassesRepository tbClassesRepository;

    @Autowired
    public TbClassService(TbClassesRepository tbClassesRepository) {
        this.tbClassesRepository = tbClassesRepository;
    }

    public TbClasses getClassIdByClassName(String className){
           return tbClassesRepository.findByClassName(className)
                   .orElse(tbClassesRepository.save(new TbClasses(IdUtil.simpleUUID(), className)));
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

    public List<TbClasses> findAll(){
        return tbClassesRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
    }
}
