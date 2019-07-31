package com.project.portal.controller;

import cn.hutool.core.bean.BeanUtil;
import com.project.portal.response.AbsPageResponse;
import org.springframework.data.domain.Page;

import static java.util.stream.Collectors.toList;

/**
 *
 * @param <T>   Page 分页对象返回的Domain
 * @param <T1>  response 相应输出的对象
 */
public abstract class BaseController<T,T1> {

    /**
     * 设置分页参数
     * 
     * @param result 分页查询的结果信息
     * @param dr 接收对象信息
     * @return AbsPageResponse　返回的分页信息和数据
     */
   public AbsPageResponse getPageResult(AbsPageResponse res, Page<T> result, T1 dr){
        res.setCurrentPage(result.getNumber()); //第几页
        res.setTotalPage(result.getTotalPages());  //总页数
        res.setRecordCount(result.getTotalElements());//总记录数
        res.setPageSize(result.getSize());//每页显示数量
        //设置查询的列表结果
        if(result.getContent().size()>0){
            res.setResList(result.getContent().stream()
                    .map(item-> {
                        BeanUtil.copyProperties(item, dr);
                        return dr;
                    }).collect(toList()));
        }
        return res;
    }
}
