//package com.project.course.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.IdUtil;
//import com.project.course.domain.ziliao.CourseData;
//import com.project.course.domain.ziliao.CourseDatumArea;
//import com.project.course.repository.ziliao.CourseDataRepository;
//import com.project.course.repository.ziliao.CourseDatumAreaRepository;
//import com.project.course.service.CourseDataService;
//import com.project.course.web.req.CourseDataDeleteReq;
//import com.project.course.web.vo.RCourseData;
//import com.project.databank.web.res.DatumResp;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
//import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
//import static java.util.stream.Collectors.toList;
//
///**
// * @Auther: zhangyy
// * @Email: zhang10092009@hotmail.com
// * @Date: 18-11-26 11:04
// * @Version: 1.0
// * @Description: 课程资料挂载
// */
//@Service
//@Slf4j
//public class CourseDataServiceImpl implements CourseDataService {
//
//    @Resource
//    private CourseDataRepository courseDataRepository;
//
//    @Resource
//    private CourseDatumAreaRepository courseDatumAreaRepository;
//
//    /**
//     * 保存课程挂在文件
//     *
//     * @param files 需要添加挂载的文件列表
//     * @return int 添加的文件数量
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int save(String chapterId, List<RCourseData> files) {
//        //1、按课程章节编号，删除历史挂在记录
//        courseDatumAreaRepository.deleteByChapterId(chapterId);
//        courseDataRepository.deleteByChapterId(chapterId);
//
//        //2、添加为挂载资料文件列表明细
//        List<CourseData> fileDatumList = files.stream()
//                .map(item -> {
//                    CourseData cd = new CourseData();
//                    cd.setDataId(item.getDataId());
//                    BeanUtil.copyProperties(item, cd);
//                    cd.setDatumExt(FileUtil.extName(cd.getDatumName()));
//                    return cd;
//                }).collect(Collectors.toList());
//
//        courseDataRepository.saveAll(fileDatumList);
//
//        //3、添加文件所属领域信息--不经常频繁的添加资料
//        fileDatumList.stream().forEach((absDatum) ->
//        {
//            final String id = absDatum.getDataId();
//            final String type = absDatum.getDatumType();
//            final String knodeId = absDatum.getKNodeId();
//            List<CourseDatumArea> list = new ArrayList<CourseDatumArea>();
//            Arrays.stream(absDatum.getDatumArea().split(",")).forEach((area) -> {
//                CourseDatumArea da = new CourseDatumArea();
//                da.setFileId(id);
//                da.setDatumArea(area);
//                da.setDatumType(type);
//                da.setChapterId(chapterId);
//                da.setKNodeId(knodeId);
//                list.add(da);
//
//            });
//            courseDatumAreaRepository.saveAll(list);
//        });
//
//        return fileDatumList.size();
//    }
//
//    /**
//     * 单个资料领域修改
//     *
//     * @param fileId     资料主表主键编号
//     * @param datumType  资料类型
//     * @param datumArea  资料领域
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public String updateAreaAndShare(String courseId, String chapterId, String fileId, String datumType, String datumArea) {
//        //1、根据资料编号和领域编号，获得领域表信息
//        CourseDatumArea da = courseDatumAreaRepository.findByFileIdAndDatumArea(fileId, datumArea);
//
//        //2、如果存在就删除，相反就添加
//        if (da != null) {
//            courseDatumAreaRepository.deleteByFileIdAndDatumArea(fileId, datumArea);
//        } else {
//            da = new CourseDatumArea();
//            da.setFileId(IdUtil.fastSimpleUUID());
//            da.setDatumArea(datumArea);
//            da.setDatumType(datumType);
//            da.setChapterId(chapterId);
//            da.setCourseId(courseId);
//            courseDatumAreaRepository.save(da);
//        }
//
//        //4、根据资料主表的资料ID，结合所选的单个资料领域，修改文件资料表的资料领域字段
//        courseDataRepository.updateDatumArea(fileId, datumArea);
//
//        return "ok";
//    }
//
//    /**
//     * 获得按资料领域、课程章节、资料列表
//     *
//     * @param chapterId
//     * @param datumType
//     * @param pageable
//     * @return
//     */
//    @Override
//    public List<DatumResp> findDatumList(String chapterId, String datumType, Pageable pageable) {
//
//        //再根据资料编号查找资料信息，转换LIST对象
//        List<DatumResp> list = courseDataRepository.findByChapterIdAndDatumTypeAndIsValidatedOrderByCreateTimeAsc(chapterId, datumType, TAKE_EFFECT_OPEN, pageable).getContent()
//                .stream()
//                .map((item) -> {
//                    DatumResp dr = new DatumResp();
//                    BeanUtil.copyProperties(item, dr);
//                    dr.setFileId(item.getDataId());
//                    dr.setFileName(item.getDatumName());
//                    dr.setFileUrl(item.getDatumUrl());
//                    return dr;
//                }).collect(toList());
//
//        return list;
//    }
//
//    /**
//     * 获得按资料领域、课程章节、资料列表
//     *
//     * @param chapterId
//     * @param pageable
//     * @return
//     */
//    @Override
//    public List<DatumResp> findDatumList(String chapterId, Pageable pageable) {
//
//        //再根据资料编号查找资料信息，转换LIST对象
//        List<DatumResp> list = courseDataRepository.findByChapterIdAndIsValidatedOrderByCreateTimeAsc(chapterId, TAKE_EFFECT_OPEN, pageable).getContent()
//                .stream()
//                .map((item) -> {
//                    DatumResp dr = new DatumResp();
//                    BeanUtil.copyProperties(item, dr);
//                    dr.setFileId(item.getDataId());
//                    dr.setFileName(item.getDatumName());
//                    dr.setFileUrl(item.getDatumUrl());
//                    return dr;
//                }).collect(toList());
//
//        return list;
//    }
//
//    @Override
//    public void delete(CourseData chapteData) {
//
//    }
//
//    @Override
//    public void deleteById(String dataId) {
//
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void removeCourseData(CourseDataDeleteReq courseDataDeleteReq) {
//        //修改资料表无效
//        List<CourseDatumArea> courseDatumAreas = courseDatumAreaRepository.findByChapterId(courseDataDeleteReq.getChapterId())
//                .stream()
//                .map(courseDatumArea -> {
//                    if (!courseDataDeleteReq.getFileIds().isEmpty() && courseDataDeleteReq.getFileIds().contains(courseDatumArea.getDataId())) {
//                        courseDatumArea.setIsValidated(TAKE_EFFECT_CLOSE);
//                    } else {
//                        courseDatumArea.setIsValidated(TAKE_EFFECT_CLOSE);
//                    }
//                    return courseDatumArea;
//                }).collect(toList());
//        courseDatumAreaRepository.saveAll(courseDatumAreas);
//        //修改
//        List<CourseData> courseDataList = courseDataRepository.findByChapterId(courseDataDeleteReq.getChapterId())
//                .stream()
//                .map(courseData -> {
//                    if (!courseDataDeleteReq.getFileIds().isEmpty() && courseDataDeleteReq.getFileIds().contains(courseData.getDataId())) {
//                        courseData.setIsValidated(TAKE_EFFECT_CLOSE);
//                    } else {
//                        courseData.setIsValidated(TAKE_EFFECT_CLOSE);
//                    }
//                    return courseData;
//                }).collect(toList());
//        courseDataRepository.saveAll(courseDataList);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteCourseData(CourseDataDeleteReq courseDataDeleteReq) {
//        courseDataRepository.deleteByChapterIdAndDataIdIn(courseDataDeleteReq.getChapterId(), courseDataDeleteReq.getFileIds());
//        courseDatumAreaRepository.deleteByChapterIdAndFileIdIn(courseDataDeleteReq.getChapterId(), courseDataDeleteReq.getFileIds());
//    }
//}