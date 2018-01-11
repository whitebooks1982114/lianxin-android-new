package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/2/23.
 */

public class report extends BmobObject {
    private String title;
    private String replytitle;
    private String content;
    private String reply;
    private Boolean adminsend;
    private Boolean readed;
    private MyUser author;

    public void report(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplytitle() {
        return replytitle;
    }

    public void setReplytitle(String replytitle) {
        this.replytitle = replytitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Boolean getAdminsend() {
        return adminsend;
    }

    public void setAdminsend(Boolean adminsend) {
        this.adminsend = adminsend;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }



}
