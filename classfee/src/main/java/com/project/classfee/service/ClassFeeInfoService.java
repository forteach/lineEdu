package com.project.classfee.service;

import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.classfee.domain.ClassFee;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.domain.ClassFeeYear;
import com.project.classfee.repository.ClassFeeInfoRepository;
import com.project.classfee.repository.ClassFeeRepository;
import com.project.classfee.repository.ClassFeeYearRepository;
import com.project.mysql.service.BaseMySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 课时详情信息导入，创建课时费管理、年度课时费汇总、初始记录
 */

@Slf4j
@Service
public class ClassFeeInfoService extends BaseMySqlService {

    /**课时详情信息*/
    @Resource
    private ClassFeeInfoRepository classFeeInfoRepository;

    /** 年度课时费汇总 */
    @Resource
    private ClassFeeYearRepository classFeeYearRepository;

    /** 课时费管理*/
    @Resource
    private ClassFeeRepository classFeeRepository;

    /** 课时费EXCEL导入处理*/
    @Resource
    private FeeImpServiceImpl feeImpServiceImpl;

    public List<ClassFeeInfo> excelReader(InputStream inputStream, Class<ClassFeeInfo> obj) {
        return feeImpServiceImpl.excelReader(inputStream, obj);
    }

    public void delExcelKey() {
        feeImpServiceImpl.delRedisKey();
    }


    /**
     * 导入的课时文件
     *
     * @param roms
     * @param year     年份
     * @param month    月份
     * @param centerId 中心ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean impFile(List<ClassFeeInfo> roms, String year, String month, String centerId, String createUser) {

        //获得文件里面的专业信息
        List<String> sepcialtyIds = filterSpecialty(roms);

        //判断和执行导入数据,并初始化年度和月份数据
        sepcialtyIds.stream()
                .filter(Objects::nonNull).
                forEach(id -> isExecImp(year, month, id, centerId, createUser));

        //保存课时文件记录
        int saveCount = saveAllInfo(roms);
        MyAssert.isNull(saveCount > 0, DefineCode.ERR0012, "导入课时信息记录失败！");

        //按专业汇总导入的数据课时,
        Map<String, Integer> classCount = roms.stream().collect(Collectors.groupingBy(ClassFeeInfo::getSpecialtyIds, Collectors.summingInt(ClassFeeInfo::getClassCount)));

        //按专业汇总导入数据的课时费
        Map<String, Integer> classFeeMap = roms.stream().collect(Collectors.groupingBy(ClassFeeInfo::getSpecialtyIds, Collectors.summingInt(ClassFeeInfo::getClassFee)));

        //根据专业名称数据更新课时费和课时
        sepcialtyIds.stream().distinct().forEach(id -> {
            //年度数据更新
            ClassFeeYear feeYear = classFeeYearRepository.findByFeeYearIdAndSpecialtyIdsAndCenterAreaId(year, id, centerId);
            //课时费
            int classFeeValue = classFeeMap.get("id");
            //课时
            int classCountValue = classCount.get("id");
            feeYear.setClassFeeSum(feeYear.getClassFeeSum() + classFeeValue);
            feeYear.setClassSum(feeYear.getClassSum() + classCountValue);
            feeYear.setCreateUser(createUser);
            feeYear.setUpdateUser(createUser);
            classFeeYearRepository.save(feeYear);

            //课时费管理数据更新
            ClassFee classFee = classFeeRepository.findByFeeYearIdAndCenterAreaIdAndCreateMonth(year, centerId, Integer.parseInt(month));
            classFee.setClassFeeSum(feeYear.getClassFeeSum() + classFeeValue);
            classFee.setClassSum(feeYear.getClassSum() + classCountValue);
            feeYear.setCreateUser(createUser);
            feeYear.setUpdateUser(createUser);
            classFeeRepository.save(classFee);
        });

        return true;
    }

    /**判断和执行导入数据,并初始化年度和月份数据*/
    private boolean isExecImp(String year, String month, String sepcialtyId, String centerId, String createUser) {

        //创建年度学年汇总记录
        boolean isExists = classFeeYearRepository.existsByFeeYearIdAndSpecialtyIdsAndCenterAreaId(year, sepcialtyId, centerId);
        if (!isExists) {
            ClassFeeYear classFeeYear = new ClassFeeYear();
            classFeeYear.setFeeYearId(year);
            classFeeYear.setSpecialtyIds(sepcialtyId);
            //未超出
            classFeeYear.setOutState("N");
            classFeeYear.setCreateUser(createUser);
            classFeeYear.setUpdateUser(createUser);
            classFeeYearRepository.save(classFeeYear);
        }

        //创建该月份的课时费管理记录
        //创建年度学年汇总记录
        int feeMonth = Integer.parseInt(month);
        isExists = classFeeRepository.existsByFeeYearIdAndCenterAreaIdAndCreateMonth(year, centerId, feeMonth);
        if (!isExists) {
            ClassFee classFee = new ClassFee();
            classFee.setFeeYearId(year);
            classFee.setSpecialtyIds(sepcialtyId);
            classFee.setCreateMonth(feeMonth);
            //未结算
            classFee.setBalanceState("no");
            classFee.setCreateUser(createUser);
            classFee.setUpdateUser(createUser);
            classFeeRepository.save(classFee);
        }
        return true;
    }


    /**过滤文件包含的专业信息*/
    private List<String> filterSpecialty(List<ClassFeeInfo> roms) {
        return roms.stream().map(obj -> obj.getSpecialtyIds()).distinct().collect(Collectors.toList());
    }


    /**
     * 添加课时费
     *
     * @param classFeeId
     * @param fullName     姓名
     * @param createYear   年份
     * @param createMonth  月份
     * @param specialtyIds 专业
     * @param classFee     课时费标准
     * @param classCount   课时
     * @param centerId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ClassFeeInfo save(String classFeeId, String fullName, String createYear, String createMonth, String specialtyIds, int classFee, int classCount, String centerId, String createUser) {
        ClassFeeInfo classFeeInfo = new ClassFeeInfo(IdUtil.fastSimpleUUID(), classFeeId, fullName, createYear, createMonth, specialtyIds, classFee, classCount, centerId, createUser);
        return classFeeInfoRepository.save(classFeeInfo);
    }

    /**
     * 课时费列表导入添加
     */
    private int saveAllInfo(List<ClassFeeInfo> list) {
        return classFeeInfoRepository.saveAll(list).size();
    }


    public ClassFeeInfo findId(String standardId) {
        Optional<ClassFeeInfo> obj = classFeeInfoRepository.findById(standardId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 教学中心
     * @param createYear   所属年份
     * @param pageable
     * @return
     */
    public Page<ClassFeeInfo> findAllPage(String centerAreaId, String createYear, Pageable pageable) {
        List<String> paramName = Arrays.asList("centerAreaId", "createYear");
        Map<String, String> paramValue = new HashMap<String, String>();
        paramValue.put("centerAreaId", centerAreaId);
        paramValue.put("createYear", createYear);
        return classFeeInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return setSpecificationEqu(root, criteriaBuilder, paramName, paramValue);
        }, pageable);
    }


}
