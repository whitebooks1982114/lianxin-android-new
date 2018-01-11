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
import com.example.whitebooks.lianxin_android.utilclass.report;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/2/24.
 */

public class Mail_Content extends Activity{
    //判断是收件内容还是发件内容
    private  Boolean frag;
    private TextView title;
    private TextView content;
    private Button backbtn;
    private report myreport;
    //发件箱传来的邮件标题
    private String sendtitle;
    //收件箱传来的邮件回复标题
    private String replytitle;
    private String mailcontent;
    private Intent intent;
    private String objectid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_content);
        myreport = new report();
        title  = (TextView)findViewById(R.id.mail_content_title);
        content = (TextView)findViewById(R.id.mail_content);
        backbtn = (Button)findViewById(R.id.mail_content_back);
        intent = getIntent();
        frag = intent.getBooleanExtra("mail",true);
        if(frag){
            replytitle = intent.getStringExtra("mailtitle");
            queryRecieveConent();
        }else {
            sendtitle = intent.getStringExtra("mailtitle");
            querySendConent();
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frag){
                    setReaded();
                    Intent intent = new Intent(Mail_Content.this, Main.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Mail_Content.this, Main.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void queryRecieveConent() {
        BmobQuery<report> query = new BmobQuery<report>();
        query.addWhereEqualTo("replytitle",replytitle);
        query.findObjects(new FindListener<report>() {
            @Override
            public void done(List<report> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    for (report report1: list){
                        mailcontent = report1.getReply();
                        objectid = report1.getObjectId();
                    }
                    content.setText(mailcontent);
                    title.setText(replytitle);
                }
            }
        });

    }
    public void querySendConent() {
        BmobQuery<report> query = new BmobQuery<report>();
        query.addWhereEqualTo("title",sendtitle);
        query.findObjects(new FindListener<report>() {
            @Override
            public void done(List<report> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    for(report report1:list){
                        mailcontent = report1.getContent();
                    }
                    content.setText(mailcontent);
                    title.setText(sendtitle);
                }
            }
        });
    }

    public void setReaded(){
        report updatereport = new report();
        updatereport.setReaded(true);
        updatereport.update(objectid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"更新失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
