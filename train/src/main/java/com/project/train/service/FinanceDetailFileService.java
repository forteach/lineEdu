package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.FinanceDetail;
import com.project.train.domain.FinanceDetailFile;
import com.project.train.repository.FinanceDetailFileRepository;
import com.project.train.repository.FinanceDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class FinanceDetailFileService extends BaseMySqlService {

    @Resource
    private FinanceDetailFileRepository financeDetailFileRepository;


    /**
     * 文件明细添加
     */
    public FinanceDetailFile save(FinanceDetailFile financeDetailFile){
        financeDetailFile.setFileId(IdUtil.fastSimpleUUID());
        return financeDetailFileRepository.save(financeDetailFile);
    }

    /**
     *文件修改
     */
    public FinanceDetailFile update(FinanceDetailFile financeDetailFile){
        FinanceDetailFile obj= findId(financeDetailFile.getFileId());
        BeanUtil.copyProperties(financeDetailFile,obj);
        return financeDetailFileRepository.save(obj);
    }


    /**
     * 文件BYID
     * @param planId
     * @return
     */
    public FinanceDetailFile findId(String planId){
        Optional<FinanceDetailFile> obj=financeDetailFileRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }

    /**
     *
     * @param centerAreaId  获取文件列表
     * @param pageable
     * @return
     */
    public Page<FinanceDetailFile> findPlanPage(String centerAreaId,String planId, Pageable pageable) {

        return financeDetailFileRepository.findAllByCenterAreaIdAndPlanIdOrderByCreateTimeDesc(centerAreaId,planId,pageable);
    }


    /**
     *
     * @param centerAreaId  获取文件列表
     * @param pageable
     * @return
     */
    public Page<FinanceDetailFile> findAllPage(String centerAreaId, Pageable pageable) {

        return financeDetailFileRepository.findAllByCenterAreaIdOrderByCreateTimeDesc(centerAreaId,pageable);
    }


}
