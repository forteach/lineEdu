package com.project.portal.response;

import lombok.Data;

import java.util.List;

@Data
public class AbsPageResponse<T1> {

    /**  总页面**/
    private int totalPage;

    /**  当前页面**/
    private int currentPage;

    /**   每页显示的条数 **/
    private int pageSize;

    /**总记录数**/
    private long recordCount;

    private List<T1> resList;
}
