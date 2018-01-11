package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.tests;
import com.example.whitebooks.lianxin_android.utilclass.usertestscore;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/5/25.
 */

public class Result extends Activity {
    private TextView total_tv;
    private TextView right_tv;
    private TextView success_tv;
    private Button back_btn;
    private Integer rightnum;
    private Integer totalnum;
    private Integer passline;
    private String usertestid;
    private MyUser myUser;
    private Integer score;
    private Integer newScore;
    private Integer oldScore;
    private boolean success;
    private Integer testid;
    private List<tests> testses = new ArrayList<tests>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        oldScore = myUser.getExamscore();
        total_tv = (TextView)findViewById(R.id.test_result_totalquestions_tv);
        right_tv = (TextView)findViewById(R.id.test_result_rightquestions_tv);
        success_tv = (TextView)findViewById(R.id.test_result_success_tv);
        back_btn = (Button)findViewById(R.id.test_result_back_btn);
        Intent intent = getIntent();
        rightnum = intent.getIntExtra("right",0);
        testid = intent.getIntExtra("testid",0);
        usertestid = intent.getStringExtra("usertestid");
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usertestid != null){
                    usertestscore usertestscore = new usertestscore();
                    if (rightnum >= passline){
                        usertestscore.setSuccess(true);
                        newScore = oldScore + score;
                        myUser.setExamscore(newScore);
                        myUser.update(myUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null){
                                    Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        usertestscore.setSuccess(false);
                    }
                    usertestscore.setRight(rightnum);
                    usertestscore.setAnswerednum(totalnum);
                    usertestscore.update(usertestid, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e != null){
                                Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(MyApplication.getContext(),Main.class);
                                startActivity(intent);
                            }
                        }
                    });
                }//如果没有回答过题目
                else {
                    usertestscore usertestscore = new usertestscore();
                    if (rightnum >= passline){
                        usertestscore.setSuccess(true);
                        newScore = oldScore + score;
                        myUser.setExamscore(newScore);
                        myUser.update(myUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null){
                                    Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        usertestscore.setSuccess(false);
                    }
                    usertestscore.setRight(rightnum);
                    usertestscore.setAnswerednum(totalnum);
                    usertestscore.setName(myUser.getUsername());
                    usertestscore.setTestid(testid);
                    usertestscore.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e != null){
                                Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(MyApplication.getContext(),Main.class);
                                startActivity(intent);
                            }

                        }
                    });
                }

            }
        });

        query();
    }
    public void query(){
        BmobQuery<tests> query = new BmobQuery<tests>();
        query.addWhereEqualTo("testid",testid);
        query.findObjects(new FindListener<tests>() {
            @Override
            public void done(List<tests> list, BmobException e) {
                for (tests tests:list){
                    passline = tests.getPassline();
                    totalnum = tests.getQuestions();
                    score = tests.getScore();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        total_tv.setText("题目总数" + totalnum);
                        right_tv.setText("正确题目数" + rightnum);
                        if (rightnum >= passline){
                            success_tv.setText("恭喜您过关了");
                        }else {
                            success_tv.setText("很遗憾，您没有过关");
                        }
                    }
                });
            }
        });
    }
}
