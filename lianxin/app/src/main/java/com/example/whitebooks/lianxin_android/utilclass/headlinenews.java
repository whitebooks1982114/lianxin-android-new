package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by whitebooks on 17/2/28.
 */

public class headlinenews extends BmobObject {
    private String kind;
    private BmobFile image;
    private String headline;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public void headlinenews(){

    }
}
