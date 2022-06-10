package com.shenyuan.superapp.common.bean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/1/28 11:46
 * desc
 */
public class NewBean implements Serializable {
    private String title;
    private String des;
    private String pusher;
    private String time;
    private int click;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPusher() {
        return pusher;
    }

    public void setPusher(String pusher) {
        this.pusher = pusher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}
