package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by whitebooks on 17/3/7.
 */

public class works extends BmobObject {
    private BmobFile works;
    private String title;
    private Integer index;
    private String author;
    private String activity;

    public works() {
    }

    public BmobFile getWorks() {
        return works;
    }

    public void setWorks(BmobFile works) {
        this.works = works;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
