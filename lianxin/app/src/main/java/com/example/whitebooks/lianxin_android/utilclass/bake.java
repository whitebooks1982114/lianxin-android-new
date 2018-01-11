package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/2/27.
 */

public class bake extends BmobObject {
    private String liantitle;
    private String kind;
    private String content;
    private Boolean check;
    private MyUser author;

    public String getLiantitle() {
        return liantitle;
    }

    public void setLiantitle(String liantitle) {
        this.liantitle = liantitle;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public void bake() {

    }
}
