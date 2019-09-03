package com.project.schoolroll.service.online;

import cn.hutool.core.util.IdUtil;
import com.project.schoolroll.domain.online.TbClasses;
import com.project.schoolroll.repository.TbClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
