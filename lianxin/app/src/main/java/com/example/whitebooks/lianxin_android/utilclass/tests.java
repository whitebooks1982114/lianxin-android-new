package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/5/15.
 */

public class tests extends BmobObject {
    private Integer passline;
    private String title;
    private Integer questions;
    private Integer testid;
    private boolean isparty;
    private Integer score;
    public tests() {

    }

    public Integer getScore() {
        return score;
    }

    public Integer getPassline() {
        return passline;
    }

    public String getTitle() {
        return title;
    }

    public Integer getQuestions() {
        return questions;
    }

    public Integer getTestid() {
        return testid;
    }

    public boolean isparty() {
        return isparty;
    }

    public void setPassline(Integer passline) {
        this.passline = passline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public void setTestid(Integer testid) {
        this.testid = testid;
    }

    public void setIsparty(boolean isparty) {
        this.isparty = isparty;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
