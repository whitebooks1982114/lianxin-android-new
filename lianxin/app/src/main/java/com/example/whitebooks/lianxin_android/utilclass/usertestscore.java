package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/5/15.
 */

public class usertestscore extends BmobObject {
    private Integer testid;
    private boolean success;
    private Integer right;
    private String name;
    private Integer answerednum;
    public usertestscore() {

    }

    public Integer getTestid() {
        return testid;
    }

    public void setTestid(Integer testid) {
        this.testid = testid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  Integer getAnswerednum() {
        return answerednum;
    }

    public void setAnswerednum(Integer answerednum) {
        this.answerednum = answerednum;
    }
}
