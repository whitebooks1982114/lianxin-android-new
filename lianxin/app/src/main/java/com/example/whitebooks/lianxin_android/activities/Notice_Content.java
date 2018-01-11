package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;

/**
 * Created by whitebooks on 17/3/10.
 */

public class Notice_Content extends Activity {
    private TextView titleTV;
    private TextView contentTV;
    private TextView deadlineTV;
    private Button backbtn;
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_detail_layout);
        titleTV = (TextView)findViewById(R.id.notice_detail_title_tv);
        contentTV = (TextView)findViewById(R.id.notice_detail_content_tv);
        deadlineTV = (TextView)findViewById(R.id.notice_detail_deadline_tv);
        backbtn = (Button)findViewById(R.id.notice_detail_back_btn);
        final Intent intent = getIntent();
        titleTV.setText(intent.getStringExtra("title"));
        contentTV.setText(intent.getStringExtra("content"));
        deadlineTV.setText(intent.getStringExtra("deadline"));
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyApplication.getContext(),Main.class);
                startActivity(intent1);
            }
        });
    }
}
