package com.project.databank.service.imp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.common.keyword.Dic;
import com.project.base.exception.AssertErrorException;
import com.project.base.exception.MyAssert;
import com.project.databank.domain.CourseChapter;
import com.project.databank.domain.verify.CourseVerifyVo;
import com.project.databank.domain.ziliao.*;
import com.project.databank.repository.CourseChapter2Repository;
import com.project.databank.repository.ziliao.*;
import com.project.databank.service.ChapteDataService;
import com.project.databank.service.CourseVerifyVoService;
import com.project.databank.web.res.DatumResp;
import com.project.databank.web.vo.CourseVerifyRequest;
import com.project.databank.web.vo.DataDatumVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.*;
import static com.project.databank.domain.verify.CourseVerifyEnum.CHAPTER_DATE;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-26 11:04
 * @Version: 1.0
 * @Description: 课程资料操作
 */
@Slf4j
@Service
public class ChapteDataServiceImpl implements ChapteDataService {

    @Resource
    private FileDatumRepository fileDatumRepository;
    @Resource
    private AudioDatumRepository audioDatumRepository;
    @Resource
    private LinkDatumRepository linkDatumRepository;
    @Resource
    private ViewDatumRepository viewDatumRepository;
    @Resource
    private CourseChapter2Repository courseChapter2Repository;
    @Resource
    private CourseVerifyVoService courseVerifyVoService;


    /**
     * @param chapterId
     * @param datumType
     * @param files
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(String courseId, String chapterId, String datumType, List<DataDatumVo> files, String createUser, String centerAreaId, String centerName, String teacherName, String courseName) {
        //根据文件类型，对应保存信息
        //1文档　3视频　4音频　5链接
        String size = "";
        switch (datumType) {
            //文档
            case Dic.COURSE_ZILIAO_FILE:
                size = saveT(courseId, chapterId, datumType, files, fileDatumRepository, new FileDatum(), createUser, centerAreaId, centerName, teacherName, courseName);
                break;
            //视频
            case Dic.COURSE_ZILIAO_VIEW:
                size = saveT(courseId, chapterId, datumType, files, viewDatumRepository, new ViewDatum(), createUser, centerAreaId, centerName, teacherName, courseName);
                break;
            //音频
            case Dic.COURSE_ZILIAO_AUDIO:
                size = saveT(courseId, chapterId, datumType, files, audioDatumRepository, new AudioDatum(), createUser, centerAreaId, centerName, teacherName, courseName);
                break;
            //链接
            case Dic.COURSE_ZILIAO_LINK:
                size = saveT(courseId, chapterId, datumType, files, linkDatumRepository, new LinkDatum(), createUser, centerAreaId, centerName, teacherName, courseName);
                break;
            default:
                MyAssert.fail(DefineCode.ERR0010, new AssertErrorException(DefineCode.ERR0010, "文件类型不正确"), "文件类型不正确");
        }
        //添加成功后的文件数量
        return size;
    }

    /**
     * 根据资料领域、课程、章节、资料列表
     *
     * @param chapterId 章节编号
     * @param datumType 文件类型
     * @param pageable  分页对象
     * @return 资料文件列表
     */
    @Override
    public List<DatumResp> findDatumList(String chapterId, String datumType, Pageable pageable, String isValidated) {
        //1、根据分拣类型，获得资料列表
        Page<? extends AbsDatum> plist = null;
        //文件
        if (datumType.equals(Dic.COURSE_ZILIAO_FILE)) {
            plist = findFileDatumPage(chapterId, datumType, pageable, isValidated);
        }
        //音频
        if (datumType.equals(Dic.COURSE_ZILIAO_AUDIO)) {
            plist = findAudioDatumPage(chapterId, datumType, pageable, isValidated);
        }
        //视频
        if (datumType.equals(Dic.COURSE_ZILIAO_VIEW)) {
            plist = findViewDatumPage(chapterId, datumType, pageable, isValidated);
        }
        //链接
        if (datumType.equals(Dic.COURSE_ZILIAO_LINK)) {
            plist = findLinkDatumPage(chapterId, datumType, pageable, isValidated);
        }

        //2、转换LIST对象
        return plist.getContent()
                .stream()
                .map((AbsDatum item) -> {
                    DatumResp dr = new DatumResp();
                    BeanUtil.copyProperties(item, dr);
                    return dr;
                }).collect(toList());
    }

    @Override
    public List<? extends AbsDatum> findAllDatumByChapterId(String chapterId, String datumType) {
        //文件
        if (datumType.equals(Dic.COURSE_ZILIAO_FILE)) {
            return findAllFileDatum(chapterId, null);
        }
        //音频
        if (datumType.equals(Dic.COURSE_ZILIAO_AUDIO)) {
            return findAllAudioDatum(chapterId, null);
        }
        //视频
        if (datumType.equals(Dic.COURSE_ZILIAO_VIEW)) {
            return findAllViewDatum(chapterId, null);
        }
        //链接
        if (datumType.equals(Dic.COURSE_ZILIAO_LINK)) {
            return findAllLinkDatum(chapterId, null);
        }
        MyAssert.isNull(null, DefineCode.ERR0002, "文件类型错误");
        return null;
    }

    @Override
    public List<? extends AbsDatum> findAllDatumByChapterIdAndVerifyStatus(String chapterId, String datumType, String verifyStatus) {
        //文件
        if (datumType.equals(Dic.COURSE_ZILIAO_FILE)) {
            return findAllFileDatum(chapterId, verifyStatus);
        }
        //音频
        if (datumType.equals(Dic.COURSE_ZILIAO_AUDIO)) {
            return findAllAudioDatum(chapterId, verifyStatus);
        }
        //视频
        if (datumType.equals(Dic.COURSE_ZILIAO_VIEW)) {
            return findAllViewDatum(chapterId, verifyStatus);
        }
        //链接
        if (datumType.equals(Dic.COURSE_ZILIAO_LINK)) {
            return findAllLinkDatum(chapterId, verifyStatus);
        }
        MyAssert.isNull(null, DefineCode.ERR0002, "文件类型错误");
        return null;
    }

    private List<FileDatum> findAllFileDatum(String chapterId, String verifyStatus) {
        if (StrUtil.isNotBlank(verifyStatus)) {
            return fileDatumRepository.findAllByChapterIdAndVerifyStatusOrderByCreateTimeDesc(chapterId, verifyStatus);
        }
        return fileDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
    }

    private List<ViewDatum> findAllViewDatum(String chapterId, String verifyStatus) {
        if (StrUtil.isNotBlank(verifyStatus)) {
            return viewDatumRepository.findAllByChapterIdAndVerifyStatusOrderByCreateTimeDesc(chapterId, verifyStatus);
        }
        return viewDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
    }

    private List<LinkDatum> findAllLinkDatum(String chapterId, String verifyStatus) {
        if (StrUtil.isNotBlank(verifyStatus)) {
            return linkDatumRepository.findAllByChapterIdAndVerifyStatusOrderByCreateTimeDesc(chapterId, verifyStatus);
        }
        return linkDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
    }

    private List<AudioDatum> findAllAudioDatum(String chapterId, String verifyStatus) {
        if (StrUtil.isNotBlank(verifyStatus)) {
            return audioDatumRepository.findAllByChapterIdAndVerifyStatusOrderByCreateTimeDesc(chapterId, verifyStatus);
        }
        return audioDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
    }


    public Page<FileDatum> findFileDatumPage(String chapterId, String datumType, Pageable pageable, String isValidated) {
        return fileDatumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecification(root, criteriaQuery, criteriaBuilder, chapterId, datumType, isValidated);
        }, pageable);
    }

    public Page<ViewDatum> findViewDatumPage(String chapterId, String datumType, Pageable pageable, String isValidated) {
        return viewDatumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecification(root, criteriaQuery, criteriaBuilder, chapterId, datumType, isValidated);
        }, pageable);
    }

    public Page<AudioDatum> findAudioDatumPage(String chapterId, String datumType, Pageable pageable, String isValidated) {
        return audioDatumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecification(root, criteriaQuery, criteriaBuilder, chapterId, datumType, isValidated);
        }, pageable);
    }

    public Page<LinkDatum> findLinkDatumPage(String chapterId, String datumType, Pageable pageable, String isValidated) {
        return linkDatumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecification(root, criteriaQuery, criteriaBuilder, chapterId, datumType, isValidated);
        }, pageable);
    }

    /**
     * 按章节、知识点、资料类型动态查询数据
     *
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     * @param chapterId
     * @param datumType
     * @return
     */
    private Predicate setSpecification(Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String chapterId, String datumType, String isValidated) {
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        if (StrUtil.isNotBlank(isValidated)) {
            predicatesList.add(criteriaBuilder.equal(root.get("isValidated"), isValidated));
        }

        if (StrUtil.isNotBlank(chapterId)) {
            predicatesList.add(criteriaBuilder.equal(root.get("chapterId"), chapterId));
        }

        //资料类型 1文档　　3视频　4音频　5链接
        if (StrUtil.isNotBlank(datumType)) {
            predicatesList.add(criteriaBuilder.equal(root.get("datumType"), datumType));
        }
        return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    }

    /**
     * @param chapterId
     * @param pageable
     * @return
     */
    @Override
    public List<DatumResp> findDatumList(String chapterId, Pageable pageable, String isValidated) {
        List<AbsDatum> fileList = CollUtil.newArrayList();
        //查询全部类型资源数据
        if (StrUtil.isNotBlank(isValidated)) {
            findfileListIsValidatedOpen(fileList, chapterId);
        } else {
            findfileList(fileList, chapterId);
        }

        //转换LIST对象
        return fileList.stream()
                .filter(Objects::nonNull)
                .map((AbsDatum item) -> {
                    DatumResp dr = new DatumResp();
                    BeanUtil.copyProperties(item, dr);
                    return dr;
                }).collect(toList());
    }

    private void findfileListIsValidatedOpen(List<AbsDatum> fileList, String chapterId) {
        //查询全部类型资源数据
        List<FileDatum> fileDatums = fileDatumRepository.findByChapterIdAndIsValidated(chapterId, TAKE_EFFECT_OPEN);
        if (fileDatums != null && fileDatums.size() > 0) {
            fileList.addAll(fileDatums);
        }

        List<AudioDatum> audioDatas = audioDatumRepository.findByChapterIdAndIsValidated(chapterId, TAKE_EFFECT_OPEN);
        if (audioDatas != null && audioDatas.size() > 0) {
            fileList.addAll(audioDatas);
        }

        List<ViewDatum> viewDatas = viewDatumRepository.findByChapterIdAndIsValidated(chapterId, TAKE_EFFECT_OPEN);
        if (viewDatas != null && viewDatas.size() > 0) {
            fileList.addAll(viewDatas);
        }

        List<LinkDatum> linkDatas = linkDatumRepository.findByChapterIdAndIsValidated(chapterId, TAKE_EFFECT_OPEN);
        if (linkDatas != null && linkDatas.size() > 0) {
            fileList.addAll(linkDatas);
        }
    }

    private void findfileList(List<AbsDatum> fileList, String chapterId) {
        //查询全部类型资源数据
        List<FileDatum> fileDatums = fileDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
        if (fileDatums != null && fileDatums.size() > 0) {
            fileList.addAll(fileDatums);
        }

        List<AudioDatum> audioDatas = audioDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
        if (audioDatas != null && audioDatas.size() > 0) {
            fileList.addAll(audioDatas);
        }

        List<ViewDatum> viewDatas = viewDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
        if (viewDatas != null && viewDatas.size() > 0) {
            fileList.addAll(viewDatas);
        }

        List<LinkDatum> linkDatas = linkDatumRepository.findAllByChapterIdOrderByCreateTimeDesc(chapterId);
        if (linkDatas != null && linkDatas.size() > 0) {
            fileList.addAll(linkDatas);
        }
    }

    private String saveT(String courseId, String chapterId, String datumType, List<DataDatumVo> files, IDatumRepoitory rep, AbsDatum fd, String createUser, String centerAreaId, String centerName, String teacherName, String courseName) {
        List<CourseVerifyVo> verifyVos = new ArrayList<>();
        //1、添加资料文件列表明细
        List<AbsDatum> fileDatumList = new ArrayList<>();
        Integer videoTime = 0;
        for (DataDatumVo dataDatumVo : files) {
            String uuid = IdUtil.fastSimpleUUID();
            fd.setChapterId(chapterId);
            fd.setFileId(uuid);
            fd.setFileName(dataDatumVo.getFileName());
            fd.setFileType(FileUtil.extName(dataDatumVo.getFileName()));
            fd.setFileUrl(dataDatumVo.getFileUrl());
            fd.setDatumType(datumType);
            fd.setCreateUser(createUser);
            fd.setUpdateUser(createUser);
            fd.setCenterAreaId(centerAreaId);
            if (fd instanceof ViewDatum && dataDatumVo.getVideoDuration() != null) {
                ((ViewDatum) fd).setVideoDuration(dataDatumVo.getVideoDuration());
                videoTime = dataDatumVo.getVideoDuration();
            }
            fd.setCourseId(courseId);
            fd.setIsValidated(TAKE_EFFECT_CLOSE);
            CourseVerifyVo verifyVo = new CourseVerifyVo();
            BeanUtil.copyProperties(fd, verifyVo);
            verifyVo.setIsValidated(TAKE_EFFECT_OPEN);
            verifyVo.setSubmitType("添加章节资料");
            verifyVo.setCenterName(centerName);
            verifyVo.setTeacherId(createUser);
            verifyVo.setTeacherName(teacherName);
            verifyVo.setCreateUser(createUser);
            verifyVo.setCourseType(CHAPTER_DATE.getValue());
            verifyVo.setCourseName(courseName);
            verifyVos.add(verifyVo);
            fileDatumList.add(fd);
        }
        rep.saveAll(fileDatumList);
        //判断是否设置长度值
        if (videoTime != null && videoTime > 0) {
            Optional<CourseChapter> optional = courseChapter2Repository.findById(chapterId);
            if (optional.isPresent()) {
                CourseChapter courseChapter = optional.get();
                courseChapter.setVideoTime(videoTime);
                courseChapter.setUpdateUser(createUser);
                courseChapter2Repository.save(courseChapter);
            }
        }

        //异步更新记录信息
        courseVerifyVoService.saveAll(verifyVos);
        //返回资料文件数量
        return String.valueOf(fileDatumList.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeChapteDataList(String courseId, String chapterId, String datumType) {
        if (StrUtil.isBlank(datumType)) {
            //没有传需要删除类型需要全部删除
            //文档
            fileDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
            //视频
            viewDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
            //音频
            audioDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
            //链接
            linkDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);

        } else {
            //传值,有具体要删除的类型
            removeTypeDataList(courseId, chapterId, datumType);
        }
    }

    private void removeTypeDataList(String courseId, String chapterId, String datumType) {
        //删除文件信息列表
        switch (datumType) {
            //文档
            case Dic.COURSE_ZILIAO_FILE:
                fileDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
                break;
            //视频
            case Dic.COURSE_ZILIAO_VIEW:
                viewDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
                break;
            //音频
            case Dic.COURSE_ZILIAO_AUDIO:
                audioDatumRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
                break;
            //链接
            case Dic.COURSE_ZILIAO_LINK:
                linkDatumRepository.deleteAllByCourseIdAndChapterId(chapterId, courseId);
                break;
            default:
                MyAssert.fail(DefineCode.ERR0010, new AssertErrorException(DefineCode.ERR0010, "文件类型不正确"), "文件类型不正确");
        }
    }

    /**
     * 删除单个文件信息和列表
     *
     * @param fileId
     */
    @Override
    public void deleteById(String fileId, String datumType) {
        //删除文件列表
        switch (datumType) {
            //文档
            case Dic.COURSE_ZILIAO_FILE:
                deleteDatumById(fileId, fileDatumRepository);
                break;
            //视频
            case Dic.COURSE_ZILIAO_VIEW:
                deleteDatumById(fileId, viewDatumRepository);
                break;
            //音频
            case Dic.COURSE_ZILIAO_AUDIO:
                deleteDatumById(fileId, audioDatumRepository);
                break;
            //链接
            case Dic.COURSE_ZILIAO_LINK:
                deleteDatumById(fileId, linkDatumRepository);
                break;
            default:
                MyAssert.fail(DefineCode.ERR0010, new AssertErrorException(DefineCode.ERR0010, "文件类型不正确"), "文件类型不正确");
        }
        courseVerifyVoService.deleteByFileId(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    void deleteDatumById(String fileId, IDatumRepoitory rep) {
        rep.deleteById(fileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyData(CourseVerifyRequest request, String datumType) {
        //删除文件列表
        switch (datumType) {
            //文档
            case Dic.COURSE_ZILIAO_FILE:
                fileDatumRepository.findById(request.getId()).ifPresent(fileDatum -> {
                    updateChapterData(request, fileDatumRepository, fileDatum);
                });
                break;
            //视频
            case Dic.COURSE_ZILIAO_VIEW:
                viewDatumRepository.findById(request.getId()).ifPresent(viewDatum -> {
                    updateChapterData(request, viewDatumRepository, viewDatum);
                });
                break;
            //音频
            case Dic.COURSE_ZILIAO_AUDIO:
                audioDatumRepository.findById(request.getId()).ifPresent(audioDatum -> {
                    updateChapterData(request, audioDatumRepository, audioDatum);
                });
                break;
            //链接
            case Dic.COURSE_ZILIAO_LINK:
                linkDatumRepository.findById(request.getId()).ifPresent(linkDatum -> {
                    updateChapterData(request, linkDatumRepository, linkDatum);
                });
                break;
            default:
                MyAssert.fail(DefineCode.ERR0010, new AssertErrorException(DefineCode.ERR0010, "文件类型不正确"), "文件类型不正确");
        }
    }

    private void updateChapterData(CourseVerifyRequest request, IDatumRepoitory rep, AbsDatum fd) {
        if (VERIFY_STATUS_AGREE.equals(request.getVerifyStatus())) {
            //审核通过
            fd.setIsValidated(TAKE_EFFECT_OPEN);
        }
        fd.setUpdateUser(request.getUserId());
        fd.setRemark(request.getRemark());
        fd.setVerifyStatus(request.getVerifyStatus());
        rep.save(fd);
    }
}