package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.LianKetangContentAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.media;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by whitebooks on 17/5/19.
 */

public class LianKetangContent extends Activity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean refreshFlag;
    private String name;
    private List<media> medias = new ArrayList<media>();
    private LianKetangContentAdapter lianKetangContentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lianketangcontent_layout);
        recyclerView = (RecyclerView)findViewById(R.id.lian_ketang_content_rv);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.lianketangcontent_floatback);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.lianketangcontent_refresh);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        query();
        refreshFlag = false;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFlag = true;
                query();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LianKetangContent.this, Main.class);
                startActivity(intent1);
            }
        });

    }
    public void query(){
        medias.clear();
        BmobQuery<media> query = new BmobQuery<media>();
        query.addWhereEqualTo("name",name);
        query.findObjects(new FindListener<media>() {
            @Override
            public void done(List<media> list, BmobException e) {
                for (media media:list){
                    medias.add(media);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       lianKetangContentAdapter = new LianKetangContentAdapter(MyApplication.getContext(),medias);
                       recyclerView.setAdapter(lianKetangContentAdapter);
                       if (refreshFlag){
                           swipeRefreshLayout.setRefreshing(false);
                       }
                    }
                });
            }

        });
    }
}
