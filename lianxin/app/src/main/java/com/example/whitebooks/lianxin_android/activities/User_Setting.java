package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;

/**
 * Created by whitebooks on 17/3/8.
 */

public class User_Setting extends Activity {

    private Integer settime;
    private Boolean isNoticeOn = true;
    private Boolean isAlarmOn = true;
    private Switch noticeSwitch;
    private Switch alarmSwitch;
    private Spinner timeSpiner;
    private TextView timeTextView;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_setting_layout);

        noticeSwitch = (Switch)findViewById(R.id.setting_notice_swith);
        alarmSwitch = (Switch)findViewById(R.id.setting_alarm_swith);
        timeSpiner = (Spinner)findViewById(R.id.setting_time_spin);
        backBtn = (Button)findViewById(R.id.setting_back_btn);
        timeTextView = (TextView)findViewById(R.id.setting_time_tv);
        noticeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();

                isNoticeOn = isChecked;
                editor.putBoolean("isNoticeOn",isNoticeOn);
                editor.apply();
            }
        });
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();

                isAlarmOn = isChecked;
                 editor.putBoolean("isAlarmOn",isAlarmOn);
                editor.apply();
            }


        });
        timeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String settimeStr = (String) timeSpiner.getSelectedItem();
                switch (settimeStr){
                    case "一天":
                        settime = 1;
                        break;
                    case "二天":
                        settime = 2;
                        break;
                    case "三天":
                        settime = 3;
                        break;
                    case "四天":
                        settime = 4;
                        break;
                    case "五天":
                        settime = 5;
                        break;
                    case "六天":
                        settime = 6;
                        break;
                    case "七天":
                        settime = 7;
                        break;
                }
                timeTextView.setText(settimeStr);
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("settimestr",settimeStr);
                editor.putInt("settime",settime);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Setting.this,Main.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        isAlarmOn = pref.getBoolean("isAlarmOn",true);
        isNoticeOn = pref.getBoolean("isNoticeOn",true);
        settime = pref.getInt("settime",1);

        alarmSwitch.setChecked(isAlarmOn);
        noticeSwitch.setChecked(isNoticeOn);
    }
}
