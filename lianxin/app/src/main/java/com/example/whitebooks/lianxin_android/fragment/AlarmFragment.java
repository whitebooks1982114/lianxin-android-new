package com.example.whitebooks.lianxin_android.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.AlarmDir;
import com.example.whitebooks.lianxin_android.activities.NoticeDir;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by whitebooks on 17/2/6.
 */

public class AlarmFragment extends Fragment {

    private ImageButton alarmbtn;
    private ImageButton noticebtn;
    private TextView alarmTV;
    private TextView noticeTV;
    private MyUser myUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarmfrag_layout,null);
        alarmbtn = (ImageButton)view.findViewById(R.id.alarm_alarm_btn);
        noticebtn = (ImageButton)view.findViewById(R.id.alarm_notice_btn);
        alarmTV =(TextView)view.findViewById(R.id.alarm_alarm_tv);
        noticeTV = (TextView)view.findViewById(R.id.alarm_notice_tv);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        if  (MyApplication.getAlarmArrayIsNotNull()){
            alarmTV.setTextColor(Color.RED);
            alarmbtn.post(new Runnable() {
                @Override
                public void run() {
                    alarmbtn.setImageResource(R.drawable.newalarm);
                }
            });
        }else {
            alarmTV.setTextColor(Color.BLACK);
            alarmbtn.post(new Runnable() {
                @Override
                public void run() {
                    alarmbtn.setImageResource(R.drawable.alarm1);
                }
            });
        }
        if (MyApplication.getNoticArrayIsNotNull()){
            noticeTV.setTextColor(Color.RED);
            noticebtn.post(new Runnable() {
                @Override
                public void run() {
                    noticebtn.setImageResource(R.drawable.newnotice);
                }
            });
        }else {
            noticeTV.setTextColor(Color.BLACK);
            noticebtn.post(new Runnable() {
                @Override
                public void run() {
                    noticebtn.setImageResource(R.drawable.notice);
                }
            });
        }

        alarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    Intent intent = new Intent(getActivity(), AlarmDir.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    Intent intent = new Intent(getActivity(), NoticeDir.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

}
