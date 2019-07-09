package com.project.schoolroll.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.schoolroll.domain.Specialty;
import com.project.schoolroll.repository.dto.SpecialtyDto;
import com.project.schoolroll.repository.SpecialtyRepository;
import com.project.schoolroll.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void saveUpdate(String specialtyId, String specialtyName){
        if (StrUtil.isNotBlank(specialtyId)){
            specialtyRepository.findById(specialtyId).ifPresent(specialty -> {
                specialty.setSpecialtyName(specialtyName);
                specialtyRepository.save(specialty);
            });
        }else {
            specialtyRepository.save(new Specialty(IdUtil.fastSimpleUUID(), specialtyName));
        }
    }
    @Override
    public List<SpecialtyDto> findAllSpecialty(){
        return specialtyRepository.findAllByIsValidatedEqualsDto();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String specialtyId) {
        specialtyRepository.deleteById(specialtyId);
    }
}
