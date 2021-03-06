package com.project.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.user.domain.Teacher;
import com.project.user.domain.TeacherFile;
import com.project.user.domain.TeacherVerify;
import com.project.user.repository.TeacherFileRepository;
import com.project.user.repository.TeacherRepository;
import com.project.user.repository.TeacherVerifyRepository;
import com.project.user.repository.dto.TeacherDto;
import com.project.user.service.TeacherService;
import com.project.user.service.UserService;
import com.project.user.web.vo.RegisterTeacherVo;
import com.project.user.web.vo.TeacherVerifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:55
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserService userService;
    private final TeacherFileRepository teacherFileRepository;
    private final TeacherVerifyRepository teacherVerifyRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherVerifyRepository teacherVerifyRepository,
                              UserService userService, TeacherFileRepository teacherFileRepository) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.teacherFileRepository = teacherFileRepository;
        this.teacherVerifyRepository = teacherVerifyRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherVerify save(TeacherVerify teacherVerify) {
        MyAssert.isTrue(teacherVerifyRepository.existsById(teacherVerify.getPhone()), DefineCode.ERR0010, "您添加的手机号码已经注册");
        teacherVerify.setTeacherId(teacherVerify.getPhone());
        teacherVerify.setVerifyStatus(VERIFY_STATUS_APPLY);
        return teacherVerifyRepository.save(teacherVerify);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherVerify update(TeacherVerify teacherVerify) {
        Optional<TeacherVerify> optionalTeacher = teacherVerifyRepository.findById(teacherVerify.getTeacherId());
        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "没有要修改的数据");
        TeacherVerify t = optionalTeacher.get();
        if (!t.getPhone().equals(teacherVerify.getPhone())){
            MyAssert.isNull(null, DefineCode.ERR0010, "手机号码不能修改");
            MyAssert.isTrue(teacherVerifyRepository.existsById(teacherVerify.getPhone()), DefineCode.ERR0010, "您添加的手机号码已经注册");
        }
        String centerId = t.getCenterAreaId();
        BeanUtil.copyProperties(teacherVerify, t);
        t.setTeacherId(teacherVerify.getPhone());
        t.setVerifyStatus(VERIFY_STATUS_APPLY);
        t.setCenterAreaId(centerId);
        return teacherVerifyRepository.save(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherVerify verifyTeacher(TeacherVerifyVo vo) {
        Optional<TeacherVerify> optionalTeacher = teacherVerifyRepository.findById(vo.getTeacherId());
        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "没有要修改的数据");
        TeacherVerify teacherVerify = optionalTeacher.get();
        if (VERIFY_STATUS_AGREE.equals(vo.getVerifyStatus())) {
            //同意修改的内容去覆盖原来的
            teacherVerify.setVerifyStatus(VERIFY_STATUS_AGREE);
            teacherVerify.setIsValidated(TAKE_EFFECT_OPEN);
            Teacher teacher = new Teacher();
            Optional<Teacher> teacherOptional = teacherRepository.findById(vo.getTeacherId());
            if (teacherOptional.isPresent()) {
                teacher = teacherOptional.get();
                if (!teacher.getPhone().equals(teacherVerify.getPhone())) {
                    userService.updateTeacher(teacher.getPhone(), teacherVerify.getPhone(), teacherVerify.getUpdateUser());
                }
            } else {
//              添加到用户保存用户
                RegisterTeacherVo registerTeacherVo = new RegisterTeacherVo();
                BeanUtil.copyProperties(teacherVerify, registerTeacherVo);
                userService.registerTeacher(registerTeacherVo);

            }
            BeanUtil.copyProperties(teacherVerify, teacher);
            teacher.setUpdateTime(DateUtil.now());
            teacherRepository.save(teacher);
        }
        if (StrUtil.isNotBlank(vo.getRemark())) {
            teacherVerify.setRemark(vo.getRemark());
        }
        teacherVerify.setVerifyStatus(vo.getVerifyStatus());
        teacherVerify.setUpdateTime(DateUtil.now());
        teacherVerifyRepository.save(teacherVerify);
        //修改教师信息对应的资料信息
        updateTeacherFileIsValidated(vo.getTeacherId(), vo.getVerifyStatus());

        return teacherVerify;
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    void updateTeacherFileIsValidated(String teacherId, String isValidated) {
        List<TeacherFile> list = teacherFileRepository.findAllByTeacherId(teacherId).stream().peek(t -> t.setIsValidated(isValidated)).collect(toList());
        teacherFileRepository.saveAll(list);
    }

//    @Override
//    public Page<Teacher> findAllPageByCenterAreaId(String centerAreaId, PageRequest pageRequest) {
//        return teacherRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, pageRequest);
//    }

    @Override
    public List<Teacher> findAllByCenterAreaId(String centerAreaId) {
        return teacherRepository.findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByTeacherId(String teacherId) {
        teacherRepository.findById(teacherId).ifPresent(t -> {
            t.setIsValidated(TAKE_EFFECT_CLOSE);
            teacherRepository.save(t);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTeacherId(String teacherId) {
        teacherVerifyRepository.deleteById(teacherId);
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Page<TeacherVerify> findAllPage(String teacherName, String centerAreaId, String verifyStatus, String phone, PageRequest pageRequest){
        StringBuilder dataSql = new StringBuilder(" select " +
                " t.teacher_id as teacher_id, " +
                " t.teacher_name as teacher_name, " +
                " t.teacher_code as teacher_code, " +
                " t.gender as gender, " +
                " t.birth_date as birth_date, " +
                " t.id_card as id_card, " +
                " t.professional_title as professional_title, " +
                " t.professional_title_date as professional_title_date, " +
                " t.position as position, " +
                " t.industry as industry, " +
                " t.email as email, " +
                " t.phone as phone, " +
                " t.specialty as specialty, " +
                " t.is_full_time as is_full_time, " +
                " t.academic_degree as academic_degree, " +
                " t.bank_card_account as bank_card_account, " +
                " t.bank_card_bank as bank_card_bank, " +
                " t.is_validated as is_validated,  " +
                " t.center_area_id as center_area_id, " +
                " lc.center_name as center_name, " +
                " t.remark as remark, " +
                " t.c_time as c_time, " +
                " t.u_time as u_time, " +
                " t.u_user as u_user, " +
                " t.c_user as c_user, " +
                " t.verify_status as verify_status " +
                " from teacher_verify as t left join learn_center as lc on lc.center_id = t.center_area_id ");
        StringBuilder whereSql = new StringBuilder(" where 1 = 1 ");
        StringBuilder countSql = new StringBuilder(" select count(1) from teacher_verify as t left join learn_center as lc on lc.center_id = t.center_area_id ");
        if (StrUtil.isNotBlank(centerAreaId)){
            whereSql.append(" and t.center_area_id = :centerAreaId");
        }
        if(StrUtil.isNotBlank(phone)){
            whereSql.append(" and t.phone = :phone");
        }
        if (StrUtil.isNotBlank(verifyStatus)){
            whereSql.append(" and t.verify_status = :verifyStatus");
        }
        if (StrUtil.isNotBlank(teacherName)){
            whereSql.append(" and t.teacher_name like '%").append(teacherName).append("%'");
        }
        dataSql.append(whereSql).append(" order by t.c_time desc ");
        countSql.append(whereSql);
        Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), TeacherVerify.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        if (StrUtil.isNotBlank(centerAreaId)) {
            dataQuery.setParameter("centerAreaId", centerAreaId);
            countQuery.setParameter("centerAreaId", centerAreaId);
        }
        if (StrUtil.isNotBlank(phone)) {
            dataQuery.setParameter("phone", phone);
            countQuery.setParameter("phone", phone);
        }
        if (StrUtil.isNotBlank(verifyStatus)){
            dataQuery.setParameter("verifyStatus", verifyStatus);
            countQuery.setParameter("verifyStatus", verifyStatus);
        }
        //设置分页
        dataQuery.setFirstResult((int) pageRequest.getOffset());
        dataQuery.setMaxResults(pageRequest.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<TeacherVerify> content = total > pageRequest.getOffset() ? dataQuery.getResultList() : Collections.emptyList();
        return new PageImpl<>(content, pageRequest, total);
    }

//    @Override
//    public TeacherVerify findByTeacherId(String teacherId){
//        Optional<TeacherVerify> optionalTeacher = teacherVerifyRepository.findById(teacherId);
//        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "不存在对应的教师信息");
//        return optionalTeacher.get();
//    }

//    @Override
//    public Teacher findById(String teacherId) {
//        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
//        MyAssert.isFalse(optionalTeacher.isPresent(), DefineCode.ERR0014, "不存在对应的教师信息");
//        return optionalTeacher.get();
//    }

//    /**
//     * 管理端通过状态查询的教师信息
//     *
//     * @param pageRequest
//     * @return
//     */
//    @Override
//    public Page<TeacherDto> findAllPageDto(PageRequest pageRequest) {
//        return teacherVerifyRepository.findAllByDto(pageRequest);
//    }
//
//    @Override
//    public Page<TeacherDto> findAllPageDtoByVerifyStatus(String verifyStatus, PageRequest pageRequest) {
//        return teacherVerifyRepository.findAllByVerifyStatusEqualsDto(verifyStatus, pageRequest);
//    }
//
//    @Override
//    public Page<TeacherDto> findAllPageDtoByVerifyStatusAndCenterAreaId(String verifyStatus, String centerAreaId, PageRequest pageRequest) {
//        return teacherVerifyRepository.findAllByVerifyStatusEqualsAndCenterAreaIdDto(verifyStatus, centerAreaId, pageRequest);
//    }
//
//    /**
//     * 学习中心查询的教师信息
//     *
//     * @param centerAreaId
//     * @param pageRequest
//     * @return
//     */
//    @Override
//    public Page<TeacherDto> findAllPageByCenterAreaIdDto(String centerAreaId, PageRequest pageRequest) {
//        return teacherVerifyRepository.findAllByCenterAreaIdDto(centerAreaId, pageRequest);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeacherFile saveFile(TeacherFile teacherFile) {
        teacherFile.setIsValidated(TAKE_EFFECT_CLOSE);
        return teacherFileRepository.save(teacherFile);
    }

    @Override
    public Map<String, List<TeacherFile>> findTeacherFile(String teacherId) {
        return teacherFileRepository.findAllByTeacherId(teacherId).stream()
                .collect(Collectors.groupingBy(TeacherFile::getType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeacherFile(String fileId) {
        teacherFileRepository.deleteById(fileId);
    }

    @Override
    public void updateStatus(String teacherId, String userId) {
        Optional<Teacher> optional = teacherRepository.findById(teacherId);
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0013, "不存在要修改的教师信息");
        optional.ifPresent(t -> {
            String status = t.getIsValidated();
            if (TAKE_EFFECT_CLOSE.equals(status)) {
                t.setIsValidated(TAKE_EFFECT_OPEN);
                //修改登陆状态
                userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_OPEN, userId);
                //修改教师审核表状态
                setTeacherVerifyStatus(teacherId, userId, TAKE_EFFECT_OPEN);
            } else {
                t.setIsValidated(TAKE_EFFECT_CLOSE);
                userService.updateStatus(t.getTeacherId(), TAKE_EFFECT_CLOSE, userId);
                setTeacherVerifyStatus(teacherId, userId, TAKE_EFFECT_CLOSE);
            }
            t.setUpdateUser(userId);
            teacherRepository.save(t);
        });
    }

    private void setTeacherVerifyStatus(String teacherId, String userId, String isValidated){
        teacherVerifyRepository.findById(teacherId).ifPresent(t -> {
            t.setIsValidated(isValidated);
            t.setUpdateUser(userId);
            t.setUpdateTime(DateUtil.now());
            teacherVerifyRepository.save(t);
        });
    }

    @Override
    public List<List<String>> exportTeachers() {
        List<List<String>> list = exportChange(teacherRepository.findAllDto());
        list.add(0, setTitle());
        return list;
    }

    @Override
    public List<List<String>> exportTeachers(String centerId) {
        List<List<String>> list = exportChange(teacherRepository.findAllByCenterAreaIdDto(centerId));
        list.add(0, setTitle());
        return list;
    }

    private List<List<String>> exportChange(List<TeacherDto> list){
        return list.parallelStream().filter(Objects::nonNull).map(this::setExport).collect(toList());
    }
    private List<String> setExport(TeacherDto dto){
        List<String> list = new ArrayList<>();
        list.add(dto.getTeacherName());
        list.add(dto.getGender());
        list.add(dto.getCenterName());
        list.add(dto.getPhone());
        list.add(dto.getEmail());
        list.add(dto.getBirthDate());
        list.add(dto.getIdCard());
        list.add(dto.getProfessionalTitle());
        list.add(dto.getProfessionalTitleDate());
        list.add((dto.getPosition()));
        list.add(dto.getIndustry());
        list.add(dto.getSpecialty());
        list.add(dto.getIsFullTime());
        list.add(dto.getAcademicDegree());
        list.add(dto.getBankCardAccount());
        list.add(dto.getBankCardBank());
        list.add(TAKE_EFFECT_OPEN.equals(dto.getIsValidated()) ? "是" : "否");
        return list;
    }
    private List<String> setTitle(){
        return CollUtil.newArrayList("姓名",
                "性别",
                "学习中心名称",
                "联系电话",
                "邮箱",
                "出生年月",
                "身份证号",
                "现任专业技术职务",
                "现任专业技术职务取得时间",
                "工作单位及职务",
                "所在行业",
                "专业",
                "是否全日制",
                "学位",
                "银行卡账户",
                "银行卡开户行",
                "是否有效");
    }
}