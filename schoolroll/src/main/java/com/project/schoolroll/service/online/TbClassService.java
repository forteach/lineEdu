package com.project.schoolroll.service.online;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.TbClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    TbClasses getClassIdByClassName(String className, String centerAreaId, String userId){
           return tbClassesRepository.findByClassNameAndCenterAreaId(className, centerAreaId)
                   .orElse(tbClassesRepository.save(new TbClasses(centerAreaId, IdUtil.simpleUUID(), className, userId)));
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

    public Page<TbClasses> findAllPage(Pageable pageable){
        return tbClassesRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }

    public Page<TbClasses> findAllPageByCenterAreaId(String centerAreaId, Pageable pageable){
        return tbClassesRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageable);
    }
}