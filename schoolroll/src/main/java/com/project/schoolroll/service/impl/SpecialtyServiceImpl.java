package com.project.schoolroll.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.Specialty;
import com.project.schoolroll.repository.dto.SpecialtyDto;
import com.project.schoolroll.repository.SpecialtyRepository;
import com.project.schoolroll.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/8 22:04
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty saveUpdate(String specialtyId, String specialtyName) {
        if (StrUtil.isNotBlank(specialtyId)) {
            Optional<Specialty> optionalSpecialty = specialtyRepository.findById(specialtyId);
            if (optionalSpecialty.isPresent()) {
                Specialty specialty = optionalSpecialty.get();
                specialty.setSpecialtyName(specialtyName);
                return specialtyRepository.save(specialty);
            }
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在要修改的专业");
            return null;
        } else {
            return specialtyRepository.save(new Specialty(IdUtil.fastSimpleUUID(), specialtyName));
        }
    }

    @Override
    public Specialty findBySpecialtyName(String specialtyName) {
        return specialtyRepository.findBySpecialtyName(specialtyName);
    }

    @Override
    public List<SpecialtyDto> findAllSpecialty() {
        return specialtyRepository.findAllByIsValidatedEqualsDto();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String specialtyId) {
        specialtyRepository.deleteById(specialtyId);
    }
}
