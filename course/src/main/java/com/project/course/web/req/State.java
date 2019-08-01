package com.project.course.web.req;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-28 14:40
 * @Version: 1.0
 * @Description:
 */
public class State implements Serializable {

    private final boolean opened = true;

    private boolean selected;

    public boolean isOpened() {
        return opened;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public State() {
    }

    public State(boolean selected) {
        this.selected = selected;
    }
}