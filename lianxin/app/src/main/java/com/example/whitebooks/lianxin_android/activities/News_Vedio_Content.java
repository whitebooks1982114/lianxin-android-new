package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;

/**
 * Created by whitebooks on 17/3/6.
 */

public class News_Vedio_Content extends Activity {
  private FloatingActionButton floatingActionButton;
    private VideoView videoView;
    private Intent intent;
    private ProgressDialog progressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog1 = new ProgressDialog(News_Vedio_Content.this);
        progressDialog1.setTitle("提示");
        progressDialog1.setMessage("请稍后");
        progressDialog1.setCancelable(true);
        progressDialog1.show();
        setContentView(R.layout.news_vedio_content);
        videoView = (VideoView)findViewById(R.id.news_vedioview);

        intent = getIntent();
        String vediouri = intent.getStringExtra("vedio");
        Uri uri = Uri.parse(vediouri);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
       floatingActionButton = (FloatingActionButton)findViewById(R.id.news_vedio_content_floatback);
        progressDialog1.dismiss();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               videoView.pause();
                videoView.suspend();
                Intent intent = new Intent(News_Vedio_Content.this,Main.class);
                startActivity(intent);
            }
        });

    }
}
