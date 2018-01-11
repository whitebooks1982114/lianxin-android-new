package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by whitebooks on 17/3/14.
 */

public class Alarm_Add extends Activity {
    private EditText titleEt;
    private EditText contentEt;
    private TextView alarmTimeTv;
    private Button okbtn;
    private Button cancelbtn;
    private int year;
    private int month;
    private int day;
    private int alarmYear;
    private int alarmMonth;
    private int alarmDay;
    private Calendar calendar;
    private String title;
    private String content;
    private Date alarmDate;
    private DatePickerDialog datePickerDialog;
    private MyUser myUser;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_add_layout);
        titleEt = (EditText)findViewById(R.id.alarm_add_title_et);
        contentEt = (EditText)findViewById(R.id.alarm_add_content_et);
        alarmTimeTv = (TextView)findViewById(R.id.alarm_add_time_tv);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        okbtn = (Button)findViewById(R.id.alarm_add_ok_btn);
        cancelbtn = (Button)findViewById(R.id.alarm_add_cancel_btn);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  title = s.toString();
            }
        });

        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 content = s.toString();
            }
        });

        alarmTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 datePickerDialog = new DatePickerDialog(Alarm_Add.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         alarmYear = year;
                         alarmMonth = month;
                         alarmDay = dayOfMonth;
                         String alarmDateStr = String.valueOf(alarmYear)+ "-" +String.valueOf(alarmMonth)+"-"+String.valueOf(alarmDay);
                         alarmTimeTv.setText(alarmDateStr);
                         try {
                             alarmDate = sdf.parse(alarmDateStr);

                         } catch (ParseException e) {
                             e.printStackTrace();
                         }

                     }
                 }, year, month, day);
                 datePickerDialog.show();

            }

        });

       okbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alarm alarm = new alarm();
               alarm.setAuthor(myUser);
               alarm.setAlarmTitle(title);
               alarm.setAlarmContent(content);
               alarm.setDeadLine(new BmobDate(alarmDate));
               alarm.save(new SaveListener<String>() {
                   @Override
                   public void done(String s, BmobException e) {
                       if (e != null){
                           Toast.makeText(MyApplication.getContext(),"保存失败",Toast.LENGTH_SHORT).show();

                       }
                   }
               });
               Intent i = new Intent(MyApplication.getContext(), Main.class);
               startActivity(i);
           }

       });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApplication.getContext(), Main.class);
                startActivity(i);
            }
        });



    }

    //隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
