package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by whitebooks on 17/3/7.
 */

public class alarm extends BmobObject {
     private MyUser author;
     private String alarmTitle;
     private String alarmContent;
     private BmobDate deadLine;

    public alarm() {
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public BmobDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(BmobDate deadLine) {
        this.deadLine = deadLine;
    }
}
