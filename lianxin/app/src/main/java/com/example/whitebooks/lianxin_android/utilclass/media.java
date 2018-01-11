package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by whitebooks on 17/5/18.
 */

public class media extends BmobObject {
    private String title;
    private BmobRelation users;
    private String name;
    private BmobFile media;
    private Integer index;
    private BmobFile image;
    public media() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobRelation getUsers() {
        return users;
    }

    public void setUsers(BmobRelation users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getMedia() {
        return media;
    }

    public void setMedia(BmobFile media) {
        this.media = media;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
