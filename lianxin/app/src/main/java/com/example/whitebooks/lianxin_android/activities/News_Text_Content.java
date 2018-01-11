package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;

/**
 * Created by whitebooks on 17/3/6.
 */

public class News_Text_Content extends Activity {
    private FloatingActionButton floatingActionButton;
    private TextView titleTV;
    private TextView contentTV;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_text_content);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.news_text_content_floatback);
        titleTV = (TextView)findViewById(R.id.text_title);
        contentTV = (TextView)findViewById(R.id.text_content);
        intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        titleTV.setText(title);
        contentTV.setText(content);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(News_Text_Content.this,Main.class);
                startActivity(intent);
            }
        });
    }
}
