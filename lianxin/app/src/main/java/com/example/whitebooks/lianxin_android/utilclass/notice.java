package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by whitebooks on 17/3/7.
 */

public class notice extends BmobObject {

    private String title;
    private BmobRelation relation;
    private BmobDate deadline;
    private String content;
    private Boolean check;



    public notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public BmobDate getDeadline() {
        return deadline;
    }

    public void setDeadline(BmobDate deadline) {
        this.deadline = deadline;
    }
}
