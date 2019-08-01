package com.project.course.web.resp;

import com.project.course.web.req.State;
import lombok.Builder;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-28 11:49
 * @Version: 1.0
 * @Description: 返回前端目录树结构
 */
@Builder
public class CourseTreeResp implements Serializable {

    private String id;

    private String parent;

    private String text;

    private String icon;

    private Integer level;

    private State state;

    public State getState() {
        return state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public CourseTreeResp() {
    }

    public CourseTreeResp(String id, String text, String icon, Integer level, State state) {
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.level = level;
        this.state = state;
    }

    public CourseTreeResp(String id, String parent, String text, String icon, Integer level, State state) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
        this.level = level;
        this.state = state;
    }
}
