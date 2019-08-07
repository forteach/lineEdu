package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.project.course.domain.ziliao.ImportantCourseware;
import com.project.course.repository.ziliao.CourseArlitsRepository;
import com.project.course.repository.ziliao.ImpCoursewareRepoitory;
import com.project.course.repository.ziliao.PhotosRepository;
import com.project.course.service.CoursewareService;
import com.project.course.web.req.CoursewareAll;
import com.project.course.web.req.ImpCoursewareAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class CoursewareServiceImpl implements CoursewareService {

    /**
     * 课程重要课件
     */
    @Resource
    private ImpCoursewareRepoitory impCoursewareRepoitory;

    /**
     * 图集信息
     */
    @Resource
    private CourseArlitsRepository courseArlitsRepository;

    /**
     * 图集图片操作
     */
    @Resource
    private PhotosRepository photoDatumRepository;


    /**
     * 保存除图集以外，重要课件文件
     *
     * @param obj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(ImpCoursewareAll obj) {
        //删除原来视频信息
        impCoursewareRepoitory.deleteAllByChapterId(obj.getChapterId());
        //保存新的视频信息
        ImportantCourseware importantCourseware = new ImportantCourseware();
        BeanUtil.copyProperties(obj, importantCourseware);
        importantCourseware.setImportantType("2");
        importantCourseware.setDatumType("3");
        impCoursewareRepoitory.save(importantCourseware);
    }

    /**
     * 保存图集
     *
     * @param obj
     * @return
     */
//    @Override
//    public List<CoursewareAll> saveCourseAtlit(ImpCoursewareAll obj) {
//
//        CourseAtlits ca = new CourseAtlits();
//        ca.setId(IdUtil.fastSimpleUUID());
//        ca.setChapterId(obj.getChapterId());
//        ca.setFileName(obj.getPhotoDatumName());
//        //保存图集信息
//        courseArlitsRepository.save(ca);
//
//        List<Photos> list = obj.getFiles().stream().map(item -> {
//            Photos photo = new Photos();
//            photo.setId(IdUtil.fastSimpleUUID());
//            photo.setArlitsId(ca.getId());
//            photo.setChapterId(obj.getChapterId());
//            BeanUtil.copyProperties(item, photo);
//            return photo;
//        }).collect(toList());
//
//        photoDatumRepository.saveAll(list);
//        return getCourseArlitsList(obj.getChapterId());
//
//    }

    /**
     * 获得重要除图集以外，课件文件列表
     *
     * @param chapterId
     * @param importantType
     * @param datumType
     * @return
     */
//    @Override
//    public ImpCoursewareAll getImpCourseware(String chapterId, String importantType, String datumType) {
//        List<ImportantCourseware> list = impCoursewareRepoitory.findByChapterIdAndDatumTypeAndImportantTypeAndIsValidated(chapterId, datumType, importantType, Dic.TAKE_EFFECT_OPEN);
//        List<CoursewareAll> files = list.stream().map((ImportantCourseware item) -> {
//            CoursewareAll ca = new CoursewareAll();
//            BeanUtil.copyProperties(item, ca);
//            return ca;
//        }).collect(toList());
//
//        if (list.size() > 0) {
//            return new ImpCoursewareAll(chapterId, importantType, files.size(), datumType, "", files);
//        }
//        return null;
//    }

    /**
     * 获得图集列表
     *
     * @param chapterId
     * @return
     */
//    @Override
//    public List<CoursewareAll> getCourseArlitsList(String chapterId) {
//
//        List<CoursewareAll> list = courseArlitsRepository.findByChapterIdAndIsValidated(chapterId, Dic.TAKE_EFFECT_OPEN)
//                .stream().map((item) -> {
//                            CoursewareAll ca = new CoursewareAll();
//                            BeanUtil.copyProperties(item, ca);
//                            return ca;
//                        }
//                ).collect(toList());
//
//        return list;
//
//    }

//    @Override
//    public List<CoursewareAll> getPhotoList(String arlitId) {
//
//        List<CoursewareAll> phlist = photoDatumRepository.findByArlitsIdAndIsValidated(arlitId, Dic.TAKE_EFFECT_OPEN)
//                .stream().map((item) -> {
//                    CoursewareAll ca = new CoursewareAll();
//                    BeanUtil.copyProperties(item, ca);
//                    return ca;
//                }).collect(toList());
//        return phlist;
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void removePhotoList(String arlitId) {
//        List<Photos> photosList = photoDatumRepository.findByArlitsIdAndIsValidated(arlitId, Dic.TAKE_EFFECT_OPEN)
//                .stream()
//                .map(photos -> {
//                    photos.setIsValidated(TAKE_EFFECT_CLOSE);
//                    return photos;
//                }).collect(toList());
//        photoDatumRepository.saveAll(photosList);
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void removeCourseArlitsList(String chapterId) {
//        List<CourseAtlits> courseAtlitsList = courseArlitsRepository.findByChapterIdAndIsValidated(chapterId, Dic.TAKE_EFFECT_OPEN).stream()
//                .map(courseAtlits -> {
//                    courseAtlits.setIsValidated(TAKE_EFFECT_CLOSE);
//                    return courseAtlits;
//                }).collect(toList());
//        courseArlitsRepository.saveAll(courseAtlitsList);
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void removeCourseware(String chapterId, String importantType, String datumType) {
//        List<ImportantCourseware> list = impCoursewareRepoitory.findByChapterIdAndDatumTypeAndImportantTypeAndIsValidated(chapterId, datumType, importantType, Dic.TAKE_EFFECT_OPEN)
//                .stream().map(importantCourseware -> {
//                    importantCourseware.setImportantType(TAKE_EFFECT_CLOSE);
//                    return importantCourseware;
//                }).collect(toList());
//        impCoursewareRepoitory.saveAll(list);
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void removeCourseAtlit(String chapterId) {
//        removeCourseArlitsList(chapterId);
//        List<Photos> photosList = photoDatumRepository.findByChapterId(chapterId).stream()
//                .map(photos -> {
//                    photos.setIsValidated(TAKE_EFFECT_CLOSE);
//                    return photos;
//                }).collect(toList());
//        photoDatumRepository.saveAll(photosList);
//    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourseChapterId(String chapterId) {
        impCoursewareRepoitory.deleteAllByChapterId(chapterId);
    }

    @Override
    public List<CoursewareAll> findByChapterId(String chapterId) {
        return impCoursewareRepoitory.findByIsValidatedEqualsAndChapterId(TAKE_EFFECT_OPEN, chapterId).stream()
                .map(i -> {
                    CoursewareAll coursewareAll = new CoursewareAll();
                    coursewareAll.setFileName(i.getFileName());
                    coursewareAll.setFileUrl(i.getFileUrl());
                    coursewareAll.setVideoTime(i.getVideoTime());
                    coursewareAll.setId(i.getId());
                    return coursewareAll;
                }).collect(toList());
    }
}

