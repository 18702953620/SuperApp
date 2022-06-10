package com.shenyuan.superstudent.bean;

/**
 * @author ch
 * @date 2021/5/20 14:39
 * @
 */
public class SortMenuBean {


    private int fristPosition;
    private int parentPosition;
    private int position;
    private MenuBean menuBean;

    public SortMenuBean(int fristPosition, int parentPosition, int position, MenuBean menuBean) {
        this.fristPosition = fristPosition;
        this.parentPosition = parentPosition;
        this.position = position;
        this.menuBean = menuBean;
    }

    public int getFristPosition() {
        return fristPosition;
    }

    public void setFristPosition(int fristPosition) {
        this.fristPosition = fristPosition;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }
}
