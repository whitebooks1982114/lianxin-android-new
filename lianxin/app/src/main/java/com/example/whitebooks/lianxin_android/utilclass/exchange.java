package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/5/21.
 */

public class exchange extends BmobObject {
    private String username;
    private String quantity;
    private String giftname;
    public exchange() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGiftname() {
        return giftname;
    }

    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }
}
