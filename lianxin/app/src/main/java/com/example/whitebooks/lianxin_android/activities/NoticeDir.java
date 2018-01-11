package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.NoticeListAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.notice;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by whitebooks on 17/3/9.
 */

public class NoticeDir extends Activity {
    private ListView noticeListView;
    private FloatingActionButton noticeFAB;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<notice> notices = new ArrayList<notice>();
    private MyUser myUser;
    private NoticeListAdapter noticeListAdapter;
    private Boolean refreshFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshFlag = false;
        setContentView(R.layout.noticedir_layout);
        noticeFAB = (FloatingActionButton) findViewById(R.id.noticedir_floatback);
        noticeListView = (ListView)findViewById(R.id.noticedir_listview);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.noticedir_refresh);
        query();
        noticeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeDir.this, Main.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFlag = true;
                query();
            }
        });
        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = notices.get(position).getContent();
                String title = notices.get(position).getTitle();
                BmobDate deadline = notices.get(position).getDeadline();
                String deadlineStr = deadline.getDate();
               Intent intent = new Intent(MyApplication.getContext(),Notice_Content.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("deadline",deadlineStr);
                startActivity(intent);
            }
        });


    }

    public void query() {
        notices.clear();
        myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<notice> query = new BmobQuery<notice>();
        BmobQuery<MyUser> userquery = new BmobQuery<MyUser>();
        userquery.addWhereEqualTo("username",myUser.getUsername());
        query.addWhereMatchesQuery("relation","_User",userquery);
        query.order("-updatedAt");
        query.findObjects(new FindListener<notice>() {
            @Override
            public void done(List<notice> list, BmobException e) {
                if (e != null) {
                    Toast.makeText(MyApplication.getContext(), "查询失败", Toast.LENGTH_SHORT).show();

                } else {
                    for (notice notice : list) {
                        notices.add(notice);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        noticeListAdapter = new NoticeListAdapter(notices);
                        noticeListView.setAdapter(noticeListAdapter);
                        if (refreshFlag) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }

        });
    }
}
