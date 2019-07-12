package com.project.schoolroll.service.impl;

import com.project.schoolroll.repository.SchoolRollChangeRepository;
import com.project.schoolroll.repository.SchoolRollRepository;
import com.project.schoolroll.service.SchoolRollChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-12 12:25
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class SchoolRollChangeImpl implements SchoolRollChangeService {
    private final SchoolRollChangeRepository schoolRollChangeRepository;
    private final SchoolRollRepository schoolRollRepository;

    public SchoolRollChangeImpl(SchoolRollChangeRepository schoolRollChangeRepository, SchoolRollRepository schoolRollRepository) {
        this.schoolRollChangeRepository = schoolRollChangeRepository;
        this.schoolRollRepository = schoolRollRepository;
    }

}
