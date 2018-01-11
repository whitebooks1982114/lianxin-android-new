package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by whitebooks on 17/3/6.
 */

public class News_Image_Content extends Activity {
    private SimpleDraweeView simpleDraweeView;
    private TextView titleTV;
    private TextView contentTV;
    private FloatingActionButton floatingActionButton;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_image_content);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.image_image);
        titleTV = (TextView)findViewById(R.id.image_title);
        contentTV = (TextView)findViewById(R.id.image_content);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.news_image_content_floatback);
        intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String imageStr = intent.getStringExtra("image");
        titleTV.setText(title);
        contentTV.setText(content);
        Uri uri = Uri.parse(imageStr);
        simpleDraweeView.setImageURI(uri);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(News_Image_Content.this,Main.class);
                startActivity(intent);
            }
        });

    }
}
