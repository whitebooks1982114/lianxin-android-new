package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/3/29.
 */

public class sign extends BmobObject {

    private String paccount;
    private String chinesename;

    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }
}
