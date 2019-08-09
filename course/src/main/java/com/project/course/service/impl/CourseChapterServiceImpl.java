package com.project.course.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.util.UpdateUtil;
import com.project.course.domain.CourseChapter;
import com.project.course.repository.CourseChapterRepository;
import com.project.course.repository.dto.ICourseChapterDto;
import com.project.course.service.CourseChapterService;
import com.project.course.web.req.CourseChapterEditReq;
import com.project.course.web.req.State;
import com.project.course.web.resp.CourseChapterSaveResp;
import com.project.course.web.resp.CourseTreeResp;
import com.project.course.web.vo.CourseChapterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 17:14
 * @Version: 1.0
 * @Description: 科目章节管理
 */
@Slf4j
@Service
public class CourseChapterServiceImpl implements CourseChapterService {


    @Resource
    private CourseChapterRepository courseChapterRepository;

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public CourseChapterSaveResp save(CourseChapter courseChapter) {
        if (StrUtil.isBlank(courseChapter.getChapterId())) {
            //1、判断是顶层章节，设置目录层级为1
            if (COURSE_CHAPTER_CHAPTER_PARENT_ID.equals(courseChapter.getChapterParentId())) {
                courseChapter.setChapterLevel("1");

            } else {
                //获得当前层级+1
                courseChapterRepository.findById(courseChapter.getChapterParentId())
                        .ifPresent(c -> {
                            courseChapter.setChapterLevel(String.valueOf(Integer.parseInt(1 + c.getChapterLevel())));
                        });
            }
            //2、查询当前科目章节有多少条数据
            int count = courseChapterRepository.countByIsValidatedEqualsAndCourseIdAndChapterParentId(TAKE_EFFECT_OPEN, courseChapter.getCourseId(), courseChapter.getChapterParentId());

            //3、设置当前章节下的最大序号
            courseChapter.setSort(String.valueOf(count + 1));
            courseChapterRepository.save(courseChapter);

            //4、创建输出对象
            CourseChapterSaveResp resp = new CourseChapterSaveResp();
            BeanUtil.copyProperties(courseChapter, resp);
            return resp;
        }else {
            CourseChapterEditReq chapterEditReq = new CourseChapterEditReq();
            BeanUtil.copyProperties(courseChapter, chapterEditReq);
            return edit(chapterEditReq);
        }
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public CourseChapterSaveResp edit(CourseChapterEditReq courseChapterEditReq) {
        //1、获得当前数据库对象
        CourseChapterSaveResp resp = new CourseChapterSaveResp();
        courseChapterRepository.findById(courseChapterEditReq.getChapterId())
                .ifPresent(source -> {
//                    CourseChapter courseChapter = CourseChapter.builder().build();
//                    BeanUtils.copyProperties(courseChapterEditReq, courseChapter);
                    UpdateUtil.copyProperties(courseChapterEditReq, source);
                    //2、设置创建时间
//                    courseChapter.setCreateTime(source.getCreateTime());
                    courseChapterRepository.save(source);

                    //3、创建输出对象
                    BeanUtil.copyProperties(source, resp);
                });
        return resp;
    }

    @Override
    public CourseChapterSaveResp getCourseChapterById(String chapterId) {
        CourseChapterSaveResp resp = new CourseChapterSaveResp();
        courseChapterRepository.findById(chapterId)
                .ifPresent(courseChapter -> {
                    //创建输出对象
                    BeanUtil.copyProperties(courseChapter, resp);
                });
        return resp;
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void delete(CourseChapter courseChapter) {
        courseChapterRepository.delete(courseChapter);
    }

    /**
     * 删除树状结构科目课程章节及子章节
     *
     * @param chapterId
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void deleteById(String chapterId) {
        CourseChapter courseChapter = courseChapterRepository.findById(chapterId).get();
        Set<String> stringSet = findLists(courseChapter.getCourseId(), chapterId);
        stringSet.add(chapterId);
        int result = courseChapterRepository.deleteBathIds(stringSet);
        log.info("chapterId : {}, deleteBath : {}", result);
    }

    /**
     * 根据父节点查询子章节的ID信息
     *
     * @param courseId
     * @param chapterParentId
     * @return
     */
    private Set<String> findLists(String courseId, String chapterParentId) {
        List<CourseChapter> lists = courseChapterRepository.findByCourseIdAndAndChapterParentId(courseId, chapterParentId);
        Set<String> stringSet = lists.stream().filter(courseChapter -> !COURSE_CHAPTER_CHAPTER_PARENT_ID.equals(courseChapter.getChapterParentId()))
                .map(CourseChapter::getChapterId)
                .collect(Collectors.toSet());
        stringSet.parallelStream().map(s -> {
            //查询对应的目录集合
            return findLists(s, courseId);
        });
        return stringSet;
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void deleteIsValidById(String chapterId) {
        CourseChapter courseChapter = courseChapterRepository.findById(chapterId).get();
        Set<String> stringSet = findLists(courseChapter.getCourseId(), chapterId);
        stringSet.add(chapterId);
        int result = courseChapterRepository.updateIsValidatedIds(TAKE_EFFECT_CLOSE, stringSet);
        log.info("chapterId : {}, resoult : {}", chapterId, result);
    }

    /**
     * 根据科目ID查询章节信息
     * 客户端用
     *
     * @param courseId
     * @return
     */
    @Override
    public List<CourseTreeResp> findByCourseId(String courseId) {
        List<ICourseChapterDto> dtoList = courseChapterRepository.findByCourseId(courseId);
        List<CourseTreeResp> courseTreeResps = new ArrayList<>();
        for (int i = 0; i < dtoList.size(); i++) {
            State state = new State();
            ICourseChapterDto courseChapterDto = dtoList.get(i);
            CourseTreeResp courseTreeResp = new CourseTreeResp();
            courseTreeResp.setId(courseChapterDto.getChapterId());
            courseTreeResp.setText(courseChapterDto.getChapterName());
            courseTreeResp.setParent(courseChapterDto.getChapterParentId());
            courseTreeResp.setRandomQuestionsNumber(courseChapterDto.getRandomQuestionsNumber());
            courseTreeResp.setVideoTime(courseChapterDto.getVideoTime());
            if (PUBLISH_YES.equals(courseChapterDto.getPublish())) {
                courseTreeResp.setIcon("fa fa-briefcase icon-state-success");
            } else if (PUBLISH_NO.equals(courseChapterDto.getPublish())) {
                courseTreeResp.setIcon("fa fa-send-o icon-state-success");
            }
            if (i == 0) {
                state.setSelected(true);
            }
            courseTreeResp.setState(state);
            courseTreeResps.add(courseTreeResp);
        }
        return courseTreeResps;
    }

    /**
     * 通过父章节目录信息查询子小节信息
     *
     * @param chapterParentId
     * @return
     */
    @Override
    public List<ICourseChapterDto> findByChapterParentId(String isValidated, String chapterParentId) {
        return courseChapterRepository.findByChapterParentId(isValidated, chapterParentId);
    }

    /**
     * １. 章节ID
     * ２．是否有效　0 有效　1无效
     * 根据条件筛选查询对应的章节信息
     *
     * @param vo
     * @return
     */
    @Override
    public List<ICourseChapterDto> findAllCourseChapter(CourseChapterVo vo) {
        return courseChapterRepository.findCourseId(vo.getIsValidated(), vo.getCourseId());
    }
}
