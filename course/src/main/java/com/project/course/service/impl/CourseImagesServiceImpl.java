package com.project.course.service.impl;

import com.project.course.domain.CourseImages;
import com.project.course.repository.CourseImagesRepository;
import com.project.course.service.CourseImagesService;
import com.project.databank.web.vo.DataDatumVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class CourseImagesServiceImpl implements CourseImagesService {

    @Resource
    private CourseImagesRepository courseImagesRepository;

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void saveCourseImages(String courseId, List<DataDatumVo> dataList, String createUser, String centerAreaId) {
        List<CourseImages> list = new ArrayList<>();
        List<DataDatumVo> dataDatumVos = dataList;
        for (int i = 0; i < dataDatumVos.size(); i++) {
            DataDatumVo dataDatumVo = dataDatumVos.get(i);
            list.add(new CourseImages(createUser, createUser, centerAreaId,
                    dataDatumVo.getFileName(), dataDatumVo.getFileUrl(), courseId, i + 1));
        }
        courseImagesRepository.saveAll(list);
    }

    @Override
    public List<CourseImages> findImagesByCourseId(String courseId, String verifyStatus) {
        return courseImagesRepository.findByIsValidatedEqualsAndCourseIdAndVerifyStatusOrderByIndexNumAsc(TAKE_EFFECT_OPEN, courseId, verifyStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteImagesByCourseId(String courseId) {
        return courseImagesRepository.deleteAllByCourseId(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<CourseImages> courseImages){
        courseImagesRepository.saveAll(courseImages);
    }
}
