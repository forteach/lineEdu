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

    private Integer randomQuestionsNumber;

    private Integer videoTime;

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

    public Integer getRandomQuestionsNumber() {
        return randomQuestionsNumber;
    }

    public void setRandomQuestionsNumber(Integer randomQuestionsNumber) {
        this.randomQuestionsNumber = randomQuestionsNumber;
    }

    public Integer getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Integer videoTime) {
        this.videoTime = videoTime;
    }

    public CourseTreeResp(String id, String parent, String text, String icon, Integer level, State state, Integer randomQuestionsNumber, Integer videoTime) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
        this.level = level;
        this.state = state;
        this.randomQuestionsNumber = randomQuestionsNumber;
        this.videoTime = videoTime;
    }
}