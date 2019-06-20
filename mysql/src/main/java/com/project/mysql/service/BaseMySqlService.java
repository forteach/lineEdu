package com.project.mysql.service;

import cn.hutool.core.util.StrUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.mysql.key.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMySqlService {

    /**
     *动态查询EQUALS通用方法
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public Predicate setSpecificationEqu(Root<?> root, CriteriaBuilder criteriaBuilder, List<String> paramName, Map<String,String> paramValue) {
        List<Predicate> predicatesList= paramName.stream()
                .filter(name-> StrUtil.isNotBlank(paramValue.get(name)))
                .map(name-> criteriaBuilder.equal(root.get(name), paramValue.get(name)))
                .collect(Collectors.toList());
        predicatesList.add(criteriaBuilder.equal(root.get("isValidated"), MyKey.VAL_OPEN));
        return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    }

}
