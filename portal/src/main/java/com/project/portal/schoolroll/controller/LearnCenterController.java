package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.LearnCenterFileSaveUpdateRequest;
import com.project.portal.schoolroll.request.LearnCenterSaveUpdateRequest;
import com.project.schoolroll.domain.CenterFile;
import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.repository.LearnCenterRepository;
import com.project.schoolroll.service.LearnCenterService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.project.base.common.keyword.Dic.*;
import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "学习中心", tags = {"学习中心管理"})
@RequestMapping(path = "/learnCenter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LearnCenterController {
    private final LearnCenterService learnCenterService;
    private final LearnCenterRepository learnCenterRepository;
    private final UserService userService;
    private final TokenService tokenService;

    public LearnCenterController(LearnCenterService learnCenterService, UserService userService, TokenService tokenService,
                                 LearnCenterRepository learnCenterRepository) {
        this.learnCenterService = learnCenterService;
        this.learnCenterRepository = learnCenterRepository;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "添加修改学习中心信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerName", value = "学习中心名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "address", value = "学习中心地址", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "principal", value = "负责人", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "负责人联系电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankName", value = "银行名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankingAccount", value = "银行账户", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "accountHolder", value = "开户人", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "accountHolderPhone", value = "开户人电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "bankingAccountAddress", value = "开户行地址", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "companyAddress", value = "公司地址", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "schoolAdmin", value = "校内联系人", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "schoolPhone", value = "学校联系人电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody LearnCenterSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        if (StrUtil.isNotBlank(request.getCenterId())) {
            learnCenterRepository.findById(request.getCenterId()).ifPresent(learnCenter -> {
                if (StrUtil.isNotBlank(request.getCenterName()) && !learnCenter.getCenterName().equals(request.getCenterName())) {
                    List<LearnCenter> learnCenters = learnCenterRepository.findByCenterName(request.getCenterName());
                    MyAssert.isFalse(learnCenters.isEmpty(), DefineCode.ERR0011, "已经存在同名学习中心");
                }
                Integer roleId = learnCenter.getRoleId();
                BeanUtils.copyProperties(request, learnCenter);
                learnCenter.setUpdateUser(userId);
                learnCenter.setRoleId(roleId);
                learnCenterRepository.save(learnCenter);
                if (StrUtil.isNotBlank(request.getCenterName()) && !learnCenter.getCenterName().equals(request.getCenterName())) {
                    //修改学习中心名称并且名字不同
                    userService.updateCenter(learnCenter.getCenterName(), request.getCenterName(), userId);
                }
                if (StrUtil.isNotBlank(request.getPhone()) && !learnCenter.getPhone().equals(request.getPhone())){
                    MyAssert.isFalse(Validator.isMobile(request.getPhone()), DefineCode.ERR0002, "联系电话不是手机号码");
                    //修改注册手机号码
                    userService.updateCenterPhone(learnCenter.getCenterName(), request.getPhone(), userId);
                }
            });
        } else {
            MyAssert.isNull(request.getCenterName(), DefineCode.ERR0010, "学习中心名称不为空");
            MyAssert.isNull(request.getPhone(), DefineCode.ERR0010, "学习中心联系人电话不为空");
            MyAssert.isNull(request.getPrincipal(), DefineCode.ERR0010, "学习中心负责人不为空");
            MyAssert.isNull(request.getAddress(), DefineCode.ERR0010, "学习中心地址不为空");
            MyAssert.isFalse(Validator.isMobile(request.getPhone()), DefineCode.ERR0002, "联系电话不是手机号码");

            List<LearnCenter> learnCenters = learnCenterRepository.findByCenterName(request.getCenterName());
            MyAssert.isFalse(learnCenters.isEmpty(), DefineCode.ERR0011, "已经存在同名学习中心");
            MyAssert.isTrue(learnCenterRepository.existsByPhone(request.getPhone()), DefineCode.ERR0010, "已经存在相同负责人电话");

            LearnCenter learnCenter = new LearnCenter();
            BeanUtils.copyProperties(request, learnCenter);
            String centerAreaId = IdUtil.fastSimpleUUID();
            learnCenter.setCenterId(centerAreaId);
            learnCenter.setCenterAreaId(centerAreaId);
            learnCenter.setUpdateUser(userId);
            learnCenter.setUpdateUser(userId);
            learnCenterRepository.save(learnCenter);
            userService.registerCenter(request.getCenterName(), request.getPhone(), centerAreaId, userId);
        }
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询中心信息", notes = "查询简介信息学习中心")
    @GetMapping(path = "/select")
    public WebResult findAllDto() {
        return WebResult.okResult(learnCenterService.findAllDto());
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询学习中心")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", required = true, example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", required = true, example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody SortVo sortVo) {
        valideSort(sortVo.getPage(), sortVo.getSize());
        return WebResult.okResult(learnCenterRepository.findAllByRoleId(CENTER_ROLE_ID, PageRequest.of(sortVo.getPage(), sortVo.getSize())));
    }

    @UserLoginToken
    @ApiOperation(value = "移除学习中心信息")
    @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/removeById")
    public WebResult removeById(@RequestBody String centerId) {
        MyAssert.isNull(centerId, DefineCode.ERR0010, "学习中心id");
        learnCenterService.removeById(JSONObject.parseObject(centerId).getString("centerId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "保存学习中心资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "fileUrl", value = "文件url", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "资料类型，1 企业资质，2 法人信息，3 其它", dataType = "string", paramType = "form")
    })
    @PostMapping("/saveFiles")
    public WebResult saveFile(@RequestBody LearnCenterFileSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getFileUrl(), DefineCode.ERR0010, "文件url不能为空");
        MyAssert.isNull(request.getCenterId(), DefineCode.ERR0010, "学习中心不能为空");
        MyAssert.isNull(request.getType(), DefineCode.ERR0010, "资料类型不能为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        CenterFile centerFile = new CenterFile();
        BeanUtil.copyProperties(request, centerFile);
        centerFile.setCenterAreaId(centerAreaId);
        centerFile.setUpdateUser(userId);
        centerFile.setCreateUser(userId);
        learnCenterService.saveFile(centerFile);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "通过资料Id删除学习中心资料")
    @DeleteMapping("/file/{fileId}")
    @ApiImplicitParam(name = "fileId", value = "文件id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteFile(@PathVariable String fileId) {
        MyAssert.isNull(fileId, DefineCode.ERR0010, "文件id不能为空");
        learnCenterService.deleteByFileId(fileId);
        return WebResult.okResult();
    }

//    @UserLoginToken
//    @ApiOperation(value = "通过资料Id集合删除学习中心资料")
//    @DeleteMapping("/deleteAllFilesByFileIds")
//    @ApiImplicitParam(name = "ids", value = "文件id", dataType = "string", required = true, paramType = "form")
//    public WebResult deleteAllFilesByFileIds(@PathVariable BaseIdsReq req) {
//        MyAssert.isTrue(req.getIds().isEmpty(), DefineCode.ERR0010, "文件id集合不能为空");
//        learnCenterService.deleteAllFilesByFileIds(req.getIds());
//        return WebResult.okResult();
//    }

    @UserLoginToken
    @GetMapping(path = "/file/{centerId}")
    @ApiOperation(value = "查询学习中心对应的资料信息")
    @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", required = true, paramType = "query")
    public WebResult findAllFiles(@PathVariable String centerId) {
        MyAssert.isNull(centerId, DefineCode.ERR0010, "学习中心id不能为空");
        return WebResult.okResult(learnCenterService.findAll(centerId));
    }

    @UserLoginToken
    @ApiOperation(value = "更新学习中心状态")
    @PutMapping(path = "/status/{centerId}")
    @ApiImplicitParam(name = "centerId", value = "学习中心id", dataType = "string", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String centerId, HttpServletRequest httpServletRequest){
        MyAssert.isNull(centerId, DefineCode.ERR0010, "学习中心id不能为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        learnCenterRepository.findById(centerId).ifPresent(c -> {
            String status = c.getIsValidated();
            String centerName = c.getCenterName();
            if (TAKE_EFFECT_CLOSE.equals(status)){
                c.setIsValidated(TAKE_EFFECT_OPEN);
                //更新用户表状态
                userService.updateStatus(centerName, TAKE_EFFECT_OPEN, userId);
                //更新学习中心资料状态
                learnCenterService.updateFileStatus(centerId, TAKE_EFFECT_OPEN, userId);
            }else {
                c.setIsValidated(TAKE_EFFECT_CLOSE);
                userService.updateStatus(centerName, TAKE_EFFECT_CLOSE, userId);
                learnCenterService.updateFileStatus(centerId, TAKE_EFFECT_CLOSE, userId);
            }
            c.setUpdateUser(userId);
            learnCenterRepository.save(c);
        });
        return WebResult.okResult();
    }
}