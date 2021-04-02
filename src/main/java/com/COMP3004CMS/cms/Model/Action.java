package com.COMP3004CMS.cms.Model;

public class Action {
    String action;
    String button;

    public Action() {
    }

    public Action(String action, String button) {
        this.action = action;
        this.button = button;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
