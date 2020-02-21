package com.project.user.service.impl;

import com.project.base.util.UpdateUtil;
import com.project.user.domain.ActionColumn;
import com.project.user.repository.ActionColumnRepository;
import com.project.user.service.ActionColumnService;
import com.project.user.web.req.ActionColumnReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-16 09:43
 * @Version: 1.0
 * @Description: 系统栏目
 */
@Slf4j
@Service
public class ActionColumnServiceImpl implements ActionColumnService {

    private final ActionColumnRepository actionColumnRepository;

    @Autowired
    public ActionColumnServiceImpl(ActionColumnRepository actionColumnRepository) {
        this.actionColumnRepository = actionColumnRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionColumn editSaveActionColumn(ActionColumnReq actionColumnReq){
        Optional<ActionColumn> actionColumnOptional = actionColumnRepository.findById(actionColumnReq.getColId() != null ? actionColumnReq.getColId() : "");
        ActionColumn actionColumn;
        if (actionColumnOptional.isPresent()){
            actionColumn = actionColumnOptional.get();
        }else {
            ActionColumn andColName = actionColumnRepository.findByIsValidatedEqualsAndColName(TAKE_EFFECT_OPEN, actionColumnReq.getColName());
            if (andColName != null){
                throw new RuntimeException("存在相同栏目名");
            }
            actionColumn = new ActionColumn();
        }
        UpdateUtil.copyNullProperties(actionColumnReq, actionColumn);
        return actionColumnRepository.save(actionColumn);
    }
}