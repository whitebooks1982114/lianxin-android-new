package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by whitebooks on 17/3/1.
 */

public class news extends BmobObject {
    private BmobFile vedio;
    private String title;
    private String level;
    private BmobFile image;
    private String content;
    private String cellkind;
    public void news(){

    }

    public BmobFile getVedio() {
        return vedio;
    }

    public void setVedio(BmobFile vedio) {
        this.vedio = vedio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCellkind() {
        return cellkind;
    }

    public void setCellkind(String cellkind) {
        this.cellkind = cellkind;
    }
}
