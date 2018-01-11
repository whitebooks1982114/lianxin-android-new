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
import com.example.whitebooks.lianxin_android.adapters.LianKetangAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.media;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by whitebooks on 17/5/18.
 */

public class LianKetang extends Activity {
    private FloatingActionButton lianFAB;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean refleshFlag;
    private RecyclerView recyclerView;
    private LianKetangAdapter lianKetangAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private List<media> medias = new ArrayList<media>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lianketang_layout);
        recyclerView = (RecyclerView)findViewById(R.id.lian_ketang_rv);
        query();
        lianFAB = (FloatingActionButton) findViewById(R.id.lianketang_floatback);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.lianketang_refresh);
        refleshFlag = false;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refleshFlag = true;
                query();
            }
        });
        lianFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LianKetang.this, Main.class);
                startActivity(intent);
            }
        });


    }
    public void query(){
        medias.clear();
        BmobQuery<media> query = new BmobQuery<media>();
        query.addWhereEqualTo("index",0);
        query.findObjects(new FindListener<media>() {
            @Override
            public void done(List<media> list, BmobException e) {
                 for(media media:list){
                     medias.add(media);
                 }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(staggeredGridLayoutManager);
                        lianKetangAdapter = new LianKetangAdapter(MyApplication.getContext(),medias);
                        recyclerView.setAdapter(lianKetangAdapter);
                        if (refleshFlag) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }
}
