package com.example.whitebooks.lianxin_android.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.AlarmDir;
import com.example.whitebooks.lianxin_android.service.LongRunTimeServie;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;

/**
 * Created by whitebooks on 17/3/10.
 */

public class AlarmRecier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //设置通知内容并在onReceive()这个函数执行时开启
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent alarmIntent = new Intent(MyApplication.getContext(),AlarmDir.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getContext(),0,alarmIntent,0);
        Notification notification = new NotificationCompat.Builder(MyApplication.getContext()).setContentTitle("通知")
                .setContentText("您有新的到期提醒事项，请点击查看")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        manager.notify(1, notification);


        //再次开启LongRunningService这个服务，从而可以
        Intent i = new Intent(context, LongRunTimeServie.class);
        context.startService(i);
    }
}
