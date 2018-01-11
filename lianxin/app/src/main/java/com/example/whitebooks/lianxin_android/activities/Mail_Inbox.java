package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.report;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by whitebooks on 17/2/23.
 */
@ContentView(R.layout.mail_inbox_layout)
public class Mail_Inbox extends Activity {
    @ViewInject(R.id.mail_inbox_floatback)
    private FloatingActionButton actionButton;
    @ViewInject(R.id.mail_inbox_listview)
    private ListView listView;
    @ViewInject(R.id.mail_inbox_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    //判断是发件箱点击进入，还是收件箱点击进入
    private boolean flag;
    private boolean refreshflag;

    private ArrayList<String> inboxData;
    private ArrayList<String> outboxData;

    private ArrayAdapter<String> arrayAdapter;

    private MyUser myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        refreshflag = false;
        myUser = BmobUser.getCurrentUser(MyUser.class);
         inboxData = new ArrayList<String>();
         outboxData = new ArrayList<String>();
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("Flag",true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (flag) {
                   String title = inboxData.get(position);
                   Intent intent = new Intent(Mail_Inbox.this,Mail_Content.class);
                   intent.putExtra("mail",true);
                   intent.putExtra("mailtitle",title);
                   startActivity(intent);

               }else {
                   String title = outboxData.get(position);
                   Intent intent = new Intent(Mail_Inbox.this,Mail_Content.class);
                   intent.putExtra("mail",false);
                   intent.putExtra("mailtitle",title);

                   startActivity(intent);

               }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(flag) {
                    refreshflag = true;
                    refreshInData();
                }else {
                    refreshflag = true;
                    refreshOutData();
                }
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mail_Inbox.this, Main.class);
                startActivity(intent);
            }
        });

        if(flag){
            refreshInData();
        }else {
            refreshOutData();
        }


    }
    public  void refreshInData(){
          inboxData.clear();

                BmobQuery<report> query = new BmobQuery<report>();
                query.order("-updatedAt");
                query.addWhereEqualTo("author",myUser);
                query.findObjects(new FindListener<report>() {
                    @Override
                    public void done(List<report> list, BmobException e) {
                        if (e != null){
                            Toast.makeText(MyApplication.getContext(),"查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }else {
                            for (report report1:list){
                                if (report1.getReplytitle() != null) {
                                    inboxData.add(report1.getReplytitle());
                                }
                            }

                            arrayAdapter = new ArrayAdapter<String>(Mail_Inbox.this,android.R.layout.simple_expandable_list_item_1,inboxData);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                });
             if(refreshflag) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         swipeRefreshLayout.setRefreshing(false);
                     }
                 });
             }

    }
    public  void refreshOutData(){
       outboxData.clear();

                BmobQuery<report> query = new BmobQuery<report>();
                query.order("-updatedAt");
                query.addWhereEqualTo("author",myUser);
                query.findObjects(new FindListener<report>() {
                    @Override
                    public void done(List<report> list, BmobException e) {
                        if (e != null){
                            Toast.makeText(MyApplication.getContext(),"查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }else if(!list.isEmpty()){
                            for (report report1:list){
                                outboxData.add(report1.getTitle());
                            }
                            arrayAdapter = new ArrayAdapter<String>(Mail_Inbox.this,android.R.layout.simple_expandable_list_item_1,outboxData);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                });

             if (refreshflag) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {

                         swipeRefreshLayout.setRefreshing(false);
                     }
                 });

             }
    }
}
