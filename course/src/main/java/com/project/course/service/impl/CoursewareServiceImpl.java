package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.record.ChapterRecords;
import com.project.course.domain.ziliao.ImportantCourseware;
import com.project.course.repository.ziliao.ImpCoursewareRepoitory;
import com.project.course.service.CourseRecordsService;
import com.project.course.service.CoursewareService;
import com.project.course.web.req.CoursewareAll;
import com.project.course.web.req.ImpCoursewareAll;
import com.project.databank.domain.verify.CourseVerifyVo;
import com.project.databank.service.CourseVerifyVoService;
import com.project.databank.web.vo.CourseVerifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.*;
import static com.project.databank.domain.verify.CourseVerifyEnum.COURSE_FILE_DATA;
import static com.project.databank.domain.verify.CourseVerifyEnum.VIEW_DATUM;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class CoursewareServiceImpl implements CoursewareService {

    /**
     * 课程重要课件
     */
    @Resource
    private ImpCoursewareRepoitory impCoursewareRepoitory;

    @Resource
    private CourseVerifyVoService courseVerifyVoService;
    @Resource
    private CourseRecordsService courseRecordsService;


    /**
     * 保存除图集以外，重要课件文件
     *
     * @param obj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(ImpCoursewareAll obj, String centerId) {
        //删除原来视频信息
        impCoursewareRepoitory.deleteAllByChapterId(obj.getChapterId());
        //保存新的视频信息
        ImportantCourseware important = new ImportantCourseware();
        BeanUtil.copyProperties(obj, important);
        important.setFileId(IdUtil.fastSimpleUUID());
        important.setImportantType("2");
        important.setDatumType(COURSE_ZILIAO_VIEW);
        important.setUpdateUser(obj.getCreateUser());
        important.setCenterAreaId(centerId);
        important.setFileType(StrUtil.subAfter(obj.getFileName(), ".", true));

        //删除原来没有审核的，防止出错
        courseVerifyVoService.deleteAllByCourseIdAndChapterIdAndVerifyStatusAndCourseType(obj.getCourseId(), obj.getChapterId(), VERIFY_STATUS_APPLY, COURSE_FILE_DATA.getValue());

        ImportantCourseware importantCourseware = impCoursewareRepoitory.save(important);
        CourseVerifyVo courseVerifyVo = new CourseVerifyVo();
        BeanUtil.copyProperties(importantCourseware, courseVerifyVo);
        courseVerifyVo.setSubmitType("添加课件");
        courseVerifyVo.setTeacherId(obj.getTeacherId());
        courseVerifyVo.setTeacherName(obj.getTeacherName());
        courseVerifyVo.setCourseName(obj.getCourseName());
        courseVerifyVo.setCourseId(obj.getCourseId());
        courseVerifyVo.setCenterName(obj.getCenterName());
        courseVerifyVo.setCenterAreaId(centerId);
        courseVerifyVo.setUpdateUser(obj.getCreateUser());
        courseVerifyVo.setCreateUser(obj.getCreateUser());
        courseVerifyVo.setCourseType(COURSE_FILE_DATA.getValue());
        courseVerifyVo.setFileType(VIEW_DATUM.getValue());
        courseVerifyVoService.save(courseVerifyVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourseChapterId(String chapterId) {
        impCoursewareRepoitory.deleteAllByChapterId(chapterId);
    }

    @Override
    public List<CoursewareAll> findByChapterId(String chapterId) {
        return findCoursewareAll(impCoursewareRepoitory.findByIsValidatedEqualsAndChapterId(TAKE_EFFECT_OPEN, chapterId));
    }

    @Override
    public List<CoursewareAll> findByChapterIdAndVerifyStatus(String courseId, String chapterId, String userId) {
        return findCoursewareAll(findImportantCoursewareByChapterId(chapterId), courseId, chapterId, userId);
    }

    private List<ImportantCourseware> findImportantCoursewareByChapterId(String chapterId){
        return impCoursewareRepoitory.findAllByIsValidatedEqualsAndChapterIdAndVerifyStatus(TAKE_EFFECT_OPEN, chapterId, VERIFY_STATUS_AGREE);
    }

    private List<CoursewareAll> findCoursewareAll(List<ImportantCourseware> list, String courseId, String chapterId, String userId) {
        return list.stream().map(i -> {
            ChapterRecords chapterRecords = courseRecordsService.findChapterRecordsByStudentIdAndChapterId(userId, courseId, chapterId);
            return CoursewareAll.builder()
                    .fileId(i.getFileId())
                    .fileName(i.getFileName())
                    .fileUrl(i.getFileUrl())
                    .videoTime(i.getVideoTime())
                    .remark(i.getRemark())
                    .verifyStatus(i.getVerifyStatus())
                    .locationTime(chapterRecords.getLocationTime())
                    .videoDuration(chapterRecords.getVideoDuration())
                    .build();
        }).collect(toList());
    }

    private List<CoursewareAll> findCoursewareAll(List<ImportantCourseware> list) {
        return list.stream().map(i -> CoursewareAll.builder()
                .fileId(i.getFileId())
                .fileName(i.getFileName())
                .fileUrl(i.getFileUrl())
                .videoTime(i.getVideoTime())
                .remark(i.getRemark())
                .verifyStatus(i.getVerifyStatus())
                .build()
        ).collect(toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVerifyCourseware(CourseVerifyRequest request) {
        Optional<ImportantCourseware> coursewareOptional = impCoursewareRepoitory.findById(request.getId());
        MyAssert.isFalse(coursewareOptional.isPresent(), DefineCode.ERR0010, "资料Id不存在");
        ImportantCourseware importantCourseware = coursewareOptional.get();
        if (VERIFY_STATUS_AGREE.equals(request.getVerifyStatus())) {
            //审核通过
            importantCourseware.setIsValidated(TAKE_EFFECT_OPEN);
        }
        importantCourseware.setUpdateUser(request.getUserId());
        importantCourseware.setRemark(request.getRemark());
        importantCourseware.setVerifyStatus(request.getVerifyStatus());
        impCoursewareRepoitory.save(importantCourseware);
    }
    @Override
    public int findVideoTimeSum(String courseId){
        return impCoursewareRepoitory.findAllByIsValidatedEqualsAndCourseIdAndDatumTypeAndVerifyStatus(TAKE_EFFECT_OPEN, courseId, COURSE_ZILIAO_VIEW, VERIFY_STATUS_AGREE)
                .stream()
                .mapToInt(ImportantCourseware::getVideoTime)
                .sum();
    }
}

