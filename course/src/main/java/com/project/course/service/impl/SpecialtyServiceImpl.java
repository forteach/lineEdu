package com.project.course.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.Specialty;
import com.project.course.repository.SpecialtyRepository;
import com.project.course.repository.dto.ISpecialtyDto;
import com.project.course.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

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
    @Transactional(rollbackFor = Exception.class)
    public Specialty saveUpdate(String specialtyId, String specialtyName, String centerAreaId) {
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
            return specialtyRepository.save(new Specialty(IdUtil.fastSimpleUUID(), specialtyName, centerAreaId));
        }
    }

    @Override
    public Specialty findBySpecialtyName(String specialtyName) {
        return specialtyRepository.findBySpecialtyName(specialtyName);
    }

    @Override
    public List<ISpecialtyDto> findAllSpecialty() {
        return specialtyRepository.findAllByIsValidatedEqualsDto();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String specialtyId) {
        specialtyRepository.deleteById(specialtyId);
    }

    @Override
    public Page<Specialty> findAllPage(int page, int size) {
        return specialtyRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN, PageRequest.of(page, size));
    }

    @Override
    public Specialty getSpecialtyById(String specialtyId) {
        return specialtyRepository.findById(specialtyId).orElseGet(Specialty::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(String specialtyId) {
        specialtyRepository.findById(specialtyId)
                .ifPresent(specialty -> {
                    specialty.setIsValidated(TAKE_EFFECT_CLOSE);
                    specialtyRepository.save(specialty);
                });
    }
}