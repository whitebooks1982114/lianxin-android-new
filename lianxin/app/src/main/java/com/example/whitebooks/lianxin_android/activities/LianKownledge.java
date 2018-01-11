package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.LianListAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.bake;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by whitebooks on 17/5/15.
 */

public class LianKownledge extends Activity {
    //判断是廉政知识还是案例分析还是心得体会
    private String cate;
    private android.support.v7.widget.SearchView searchView;
    private SwipeMenuListView lianListView;
    private FloatingActionButton lianFAB;
    private FloatingActionButton lianAdd;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<bake> bakes = new ArrayList<bake>();
    private MyUser myUser;
    private LianListAdapter lianListAdapter;
    private Boolean refreshFlag;
    private Intent intent_add;
    private String queryKind;
    private boolean isAdim;
    private boolean checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liankownledge_layout);
        Intent intent = getIntent();
        cate = intent.getStringExtra("Category");
        if (cate.equals("1")){
            queryKind = "zhishi";
        }else if(cate.equals("2")){
            queryKind = "anli";
        }else {
            queryKind = "xinde";
        }
        myUser = BmobUser.getCurrentUser(MyUser.class);
        isAdim = myUser.getIsadmin();
        refreshFlag = false;
        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.lianzhishisv);
        lianListView = (SwipeMenuListView)findViewById(R.id.lianzhishi_listview);
        lianFAB = (FloatingActionButton)findViewById(R.id.lianzhishi_floatback);
        lianAdd = (FloatingActionButton)findViewById(R.id.lianzhishi_add_floatback);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lianzhishi_refresh);
        query();
        lianFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LianKownledge.this, Main.class);
                startActivity(intent);
            }
        });

        lianAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_add = new Intent(LianKownledge.this,LianAdd.class);
                if (cate.equals("1")){
                    intent_add.putExtra("Add_Cat","zhishi");
                }else if(cate.equals("2")){
                    intent_add.putExtra("Add_Cat","anli");
                }else {
                    intent_add.putExtra("Add_Cat","xinde");
                }
                startActivity(intent_add);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFlag = true;
                query();
            }
        });
        lianListView.setTextFilterEnabled(true);
        lianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = bakes.get(position).getContent();
                String title = bakes.get(position).getLiantitle();
                checked = bakes.get(position).getCheck();
                String objectId = bakes.get(position).getObjectId();
                Intent intent = new Intent(LianKownledge.this,LianContent.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("check",checked);
                intent.putExtra("id",objectId);
                startActivity(intent);
            }
        });
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null) {
                    // Clear the text filter.

                } else {
                    // Sets the initial value for the text filter.
                    ArrayList<bake> obj = searchItem(newText);
                    updateLayout(obj);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
    }
    public ArrayList<bake> searchItem(String name) {
        ArrayList<bake> mSearchList = new ArrayList<bake>();
        for (int i = 0; i < bakes.size(); i++) {
            int index = bakes.get(i).getLiantitle().indexOf(name);
            // 存在匹配的数据
            if (index != -1) {
                mSearchList.add(bakes.get(i));
            }
        }
        return mSearchList;
    }

    // 更新数据
    public void updateLayout(ArrayList<bake> obj) {
        lianListView.setAdapter(new LianListAdapter(obj));
    }
    public void query() {
        bakes.clear();
        BmobQuery<bake> query1 = new BmobQuery<bake>();
        BmobQuery<bake> query = new BmobQuery<bake>();
        List<BmobQuery<bake>> queries = new ArrayList<BmobQuery<bake>>();
        BmobQuery<bake> querymain = new BmobQuery<bake>();
        if (isAdim != true) {
            query.addWhereEqualTo("check",true);
        }
        query1.addWhereEqualTo("kind",queryKind);
        queries.add(query);
        queries.add(query1);
        query1.order("-updatedAt");
        query1.include("author");
        querymain.and(queries);
        querymain.findObjects(new FindListener<bake>() {
            @Override
            public void done(List<bake> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();

                }else {
                    for (bake bake:list){
                        bakes.add(bake);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lianListAdapter = new LianListAdapter(bakes);
                        lianListView.setAdapter(lianListAdapter);
                        if (refreshFlag) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }

        });
    }

}
