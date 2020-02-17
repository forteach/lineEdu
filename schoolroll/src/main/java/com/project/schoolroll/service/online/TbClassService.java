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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

@Service
public class TbClassService {

    private final TbClassesRepository tbClassesRepository;

    @Autowired
    public TbClassService(TbClassesRepository tbClassesRepository) {
        this.tbClassesRepository = tbClassesRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    TbClasses getClassIdByClassName(String className, String centerAreaId, String userId, String specialtyName, String grade) {
        return tbClassesRepository.findBySpecialtyNameAndGradeAndCenterAreaId(specialtyName, grade, centerAreaId)
                .orElseGet(() -> tbClassesRepository.save(new TbClasses(centerAreaId, IdUtil.simpleUUID(), className, userId, specialtyName, grade)));
    }

    public Optional<TbClasses> findByClassNameAndCenterAreaId(String className, String centerAreaId){
        return tbClassesRepository.findByClassNameAndCenterAreaId(className, centerAreaId);
    }

    public TbClasses findById(String classId) {
        Optional<TbClasses> classesOptional = tbClassesRepository.findById(classId);
        MyAssert.isFalse(classesOptional.isPresent(), DefineCode.ERR0010, "不存在对应的班级信息");
        return classesOptional.get();
    }


    public TbClasses findClassByClassId(String classId) {
        Optional<TbClasses> optionalTbClasses = tbClassesRepository.findById(classId);
        if (optionalTbClasses.isPresent()) {
            return optionalTbClasses.get();
        } else {
            MyAssert.isNull(null, DefineCode.ERR0010, "不存在对应班级信息");
            return null;
        }
    }

    public List<TbClasses> findAllByCenterAreaId(String centerAreaId) {
        return tbClassesRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId);
    }

    public List<TbClasses> findAllByCenterAreaId(String centerAreaId, Set<String> classIds) {
        return findAllByCenterAreaId(centerAreaId)
                .stream()
                .filter(Objects::nonNull)
                .filter(o -> filterClass(classIds, o.getClassId()))
                .collect(toList());
    }

    /**
     * 过滤已经添加过的班级id
     */
    private boolean filterClass(Set<String> classIds, String classId) {
        if (classIds.isEmpty()) {
            return true;
        }
        return !classIds.contains(classId);
    }

    public Page<TbClasses> findAllPage(Pageable pageable) {
        return tbClassesRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }

    public Page<TbClasses> findAllPageByCenterAreaId(String centerAreaId, Pageable pageable) {
        return tbClassesRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageable);
    }
}