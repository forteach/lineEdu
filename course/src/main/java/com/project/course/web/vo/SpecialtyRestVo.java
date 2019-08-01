package com.project.course.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-24 21:52
 * @version: 1.0
 * @description:
 */
@Data
public class SpecialtyRestVo implements Serializable {

    public interface Update {
    }

    public interface Add {
    }

    public String id;

    private String name;
}