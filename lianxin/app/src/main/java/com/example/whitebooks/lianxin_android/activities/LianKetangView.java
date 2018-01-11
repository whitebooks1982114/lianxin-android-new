package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.media;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/5/19.
 */

public class LianKetangView extends Activity {
    private FloatingActionButton floatingActionButton;
    private ProgressDialog progressDialog1;
    private Intent intent;
    private VideoView videoView;
    private MyUser myUser;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lianketangview_layout);
        videoView = (VideoView)findViewById(R.id.lian_ketangview_vv);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        progressDialog1 = new ProgressDialog(LianKetangView.this);
        progressDialog1.setTitle("提示");
        progressDialog1.setMessage("请稍后");
        progressDialog1.setCancelable(true);
        progressDialog1.show();
        intent = getIntent();
        String uri = intent.getStringExtra("uri");
        id = intent.getStringExtra("id");
        final Uri uri1 = Uri.parse(uri);
        videoView.setMediaController(new MediaController(LianKetangView.this));
        videoView.setVideoURI(uri1);
        videoView.start();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.lianketang_vedio_content_floatback);
        progressDialog1.dismiss();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.pause();
                videoView.suspend();
                media media = new media();
                BmobRelation relation = new BmobRelation();
                media.setObjectId(id);
                relation.add(myUser);
                media.setUsers(relation);
                media.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null){
                            Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent intent = new Intent(LianKetangView.this,Main.class);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                videoView.suspend();
                Intent intent = new Intent(LianKetangView.this,Main.class);
                startActivity(intent);
            }
        });
    }
}
