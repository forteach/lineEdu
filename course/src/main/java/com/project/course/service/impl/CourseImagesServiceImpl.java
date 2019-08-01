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
    public void saveCourseImages(String courseId, List<DataDatumVo> dataList) {
        List<CourseImages> list = new ArrayList<>();
        List<DataDatumVo> dataDatumVos = dataList;
        for (int i = 0; i < dataDatumVos.size(); i++) {
            DataDatumVo dataDatumVo = dataDatumVos.get(i);
            list.add(CourseImages.builder()
                    .courseId(courseId)
                    .indexNum(i + 1)
                    .imageName(dataDatumVo.getFileName())
                    .imageUrl(dataDatumVo.getFileUrl())
                    .build());
        }
        courseImagesRepository.saveAll(list);
    }

    @Override
    public List<CourseImages> findImagesByCourseId(String courseId) {
        return courseImagesRepository.findByIsValidatedEqualsAndCourseIdOrderByIndexNumAsc(TAKE_EFFECT_OPEN, courseId);
    }

    @Override
    public int deleteImagesByCourseId(String courseId) {
        return courseImagesRepository.deleteAllByCourseId(courseId);
    }
}
