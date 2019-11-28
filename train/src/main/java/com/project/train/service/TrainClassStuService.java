package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.train.domain.TrainClassStu;
import com.project.train.domain.TrainPlanFinish;
import com.project.train.repository.TrainClassStuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.mysql.service.BaseMySqlService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * 项目计划班级成员信息记录
 */

@Slf4j
@Service
public class TrainClassStuService extends BaseMySqlService {

    @Resource
    private TrainClassStuRepository trainClassStuRepository;

    @Resource
    private TrainClassService trainClassService;

    @Resource
    private TrainPlanFinishService trainPlanFinishService;

    /**
     * 项目计划班级成员添加
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainClassStu save(TrainClassStu trainClassStu) {
        trainClassStu.setTrainStuId(IdUtil.fastSimpleUUID());
        trainClassStuRepository.save(trainClassStu);

        String planId=trainClassStu.getPjPlanId();
        //计划班级数量
        int pcount=trainClassService.countClass(planId);
        //添加班级学生的班级数量
       int ccount=countClass(planId);

       //如果两个数量相等，改变计划完成情况的班级添加完成状态为1
        if((pcount==ccount)&&(pcount!=0)){
            TrainPlanFinish tf=trainPlanFinishService.findPjPlanId(planId);
            tf.setIsStudent(1);
            trainPlanFinishService.save(tf);
        }

        //判断是否全部完善信息了

        trainPlanFinishService.updateAll(planId);
        return trainClassStu;
    }

    /**
     * 项目计划班级成员修改
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainClassStu update(TrainClassStu trainClassStu) {
        TrainClassStu obj = findId(trainClassStu.getTrainStuId());
        BeanUtil.copyProperties(trainClassStu, obj);
        return trainClassStuRepository.save(obj);
    }


    /**
     * 项目计划班级成员BYID
     *
     * @param planId
     * @return
     */
    public TrainClassStu findId(String planId) {
        Optional<TrainClassStu> obj = trainClassStuRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }


    /**
     * @param pjPlanId   获取计划项目的班级成员列表
     * @param pageable
     * @return
     */
    public Page<TrainClassStu> findByPlanIdPageAll(String pjPlanId, Pageable pageable) {

        return trainClassStuRepository.findByPjPlanIdOrderByCreateTimeDesc(pjPlanId, pageable);
    }

    public Page<TrainClassStu> findByCenterAreaIdAndAgoDayAll(String centerAreaId, String pjPlanId, int agoDay, Pageable pageable) {
        //提前天数的日期
        String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
        return trainClassStuRepository.findAllByCenterAreaIdAndPjPlanIdAndCreateTimeAfterOrderByCreateTimeDesc(centerAreaId, pjPlanId, fromDay, pageable);
    }

    public Page<TrainClassStu> findAllPage(Pageable pageable) {

        return trainClassStuRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }

    public Page<TrainClassStu> findAgoDay(int agoDay, PageRequest pageable) {
        //提前天数的日期
        String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
        return trainClassStuRepository.findAllByCreateTimeAfterOrderByCreateTimeDesc(fromDay, pageable);
    }

    /**
     * @param classId
     * @param pageable
     * @return
     */
    public Page<TrainClassStu> findClassPage(String classId, Pageable pageable) {

        return trainClassStuRepository.findByTrainClassId(classId, pageable);
    }

    public Page<TrainClassStu> findAllPage(String centerAreaId, String pjPlanId, int agoDay, Pageable pageable){
        return trainClassStuRepository.findAll((Root<TrainClassStu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = null;
            if (StrUtil.isNotBlank(centerAreaId)) {
                predicate = criteriaBuilder.equal(root.get("centerAreaId"), centerAreaId);
            }
            if (StrUtil.isNotBlank(pjPlanId)) {
                predicate = criteriaBuilder.equal(root.get("pjPlanId"), pjPlanId);
            }
            if (StrUtil.isNotBlank(String.valueOf(agoDay))){
                String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
                predicate = criteriaBuilder.between(root.get("createTime"), fromDay, DateUtil.today());
            }
            return predicate;
        }, pageable);
    }

    /**
     * 返回计划下的班级数量
     * @param pjPlanId
     * @return
     */
    public int countClass(String pjPlanId){
        return trainClassStuRepository.countClass(pjPlanId);
    }


}
