package com.example.whitebooks.lianxin_android.utilclass;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by whitebooks on 17/2/8.
 */
//获取全局context
public class MyApplication extends Application {
    private static Context context;
    private static Boolean alarmArrayIsNotNull = false;
    private static Boolean noticArrayIsNotNull = false;

    public static Boolean getAlarmArrayIsNotNull() {
        return alarmArrayIsNotNull;
    }

    public static void setAlarmArrayIsNotNull(Boolean alarmArrayIsNotNull) {
        MyApplication.alarmArrayIsNotNull = alarmArrayIsNotNull;
    }

    public static Boolean getNoticArrayIsNotNull() {
        return noticArrayIsNotNull;
    }

    public static void setNoticArrayIsNotNull(Boolean noticArrayIsNotNull) {
        MyApplication.noticArrayIsNotNull = noticArrayIsNotNull;
    }

    @Override
    public void onCreate() {

        context = getApplicationContext();
        Fresco.initialize(this);
        MultiDex.install(this);
    }
    public static Context getContext(){
        return context;
    }

}
