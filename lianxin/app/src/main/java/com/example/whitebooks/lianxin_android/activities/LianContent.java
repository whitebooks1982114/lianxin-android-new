package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.bake;
import com.example.whitebooks.lianxin_android.utilclass.userscore;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/5/15.
 */

public class LianContent extends Activity{
    private Intent intent;
    private String title;
    private String content;
    private MyUser author;
    private Switch checkSwitch;
    private Button userScoreBtn;
    private Button backBtn;
    private TextView titleTV;
    private TextView contentTV;
    private TextView authorTV;
    private boolean checked;
    private String objectID;
    private MyUser myuser;
    private Integer score;
    private String scoreObjectID;
    //判断是否加分按钮
    private boolean addScoreFlag;
    private Integer exScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lian_content);
        intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        checked = intent.getBooleanExtra("check",false);
        objectID = intent.getStringExtra("id");
        titleTV = (TextView)findViewById(R.id.lian_title_tv);
        contentTV = (TextView)findViewById(R.id.lian_content_content_tv);
        authorTV = (TextView)findViewById(R.id.lian_content_author_tv);
        checkSwitch = (Switch)findViewById(R.id.lian_content_check_switch);
        backBtn = (Button)findViewById(R.id.lian_content_back_btn);
        userScoreBtn = (Button)findViewById(R.id.lian_content_check_btn);
        myuser = BmobUser.getCurrentUser(MyUser.class);
        if (myuser.getIsadmin() == true){
            checkSwitch.setVisibility(View.VISIBLE);
            userScoreBtn.setVisibility(View.VISIBLE);
        }
        userScoreBtn.setClickable(true);
        titleTV.setText(title);
        contentTV.setText(content);
        query();
        checkSwitch.setChecked(checked);
        checkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               bake updateBake = new bake();
                currentScore();
                exScore = author.getExscore();
                Log.d("tag",exScore.toString());
                if (isChecked) {
                    checked = true;
                    updateBake.setCheck(checked);
                    if (score != null){
                        addScoreFlag = true;
                    }else {
                        addScoreFlag = true;
                        score = 0;
                    }
                }else {
                    checked = false;
                    updateBake.setCheck(checked);
                    if (score != null && score >= 5){
                        addScoreFlag = false;
                    }else {
                        score = 0;
                    }
                }
         updateBake.update(objectID, new UpdateListener() {
             @Override
             public void done(BmobException e) {
                 if (e != null){
                     Toast.makeText(MyApplication.getContext(),"更新失败",Toast.LENGTH_SHORT).show();

                 }else {
                     Toast.makeText(MyApplication.getContext(),"更新成功",Toast.LENGTH_SHORT).show();
                 }
             }
         });
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyApplication.getContext(),Main.class);
                startActivity(intent1);
            }
        });
        userScoreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userScoreBtn.setClickable(false);
                userscore userscore = new userscore();
                if (addScoreFlag == true){
                    score = score + 5;
                    exScore = exScore + 5;
                }else {
                    score = score - 5;
                    exScore = exScore - 5;
                }
                if (score <= 0){
                    score = 0;
                    exScore = 0;
                }
                myuser.setExscore(exScore);
                myuser.update(myuser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                userscore.setScore(score);
                userscore.update(scoreObjectID, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null){
                            Toast.makeText(MyApplication.getContext(),"更新失败",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MyApplication.getContext(),"积分已更新",Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(MyApplication.getContext(),Main.class);
                            startActivity(intent2);
                        }
                    }
                });
            }
        });
    }
    public void currentScore() {
        BmobQuery<userscore> query = new BmobQuery<userscore>();
        query.addWhereEqualTo("author",author);
        query.findObjects(new FindListener<userscore>() {
            @Override
            public void done(List<userscore> list, BmobException e) {
                 if (e != null) {
                     Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                 }else {
                     for (userscore us:list){
                         score = us.getScore();
                         scoreObjectID = us.getObjectId();

                     }
                 }
            }
        });
    }
    public void query(){
        BmobQuery<bake> bakeBmobQuery = new BmobQuery<bake>();
        bakeBmobQuery.include("author");
        bakeBmobQuery.getObject(objectID, new QueryListener<bake>() {
            @Override
            public void done(bake bake, BmobException e) {
                if (e == null) {
                    author = bake.getAuthor();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        authorTV.setText(author.getUsername());
                    }
                });
            }

        });

    }



}
