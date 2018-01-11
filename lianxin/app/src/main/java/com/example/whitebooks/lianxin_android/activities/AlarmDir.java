package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.AlarmListAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.alarm;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/3/9.
 */

public class AlarmDir extends Activity {
    private SwipeMenuListView alarmListView;
    private FloatingActionButton alarmFAB;
    private FloatingActionButton alarmAdd;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<alarm> alarms = new ArrayList<alarm>();
    private MyUser myUser;
    private AlarmListAdapter alarmListAdapter;
    private Boolean refreshFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshFlag = false;
        setContentView(R.layout.alarmdir_layout);
        alarmFAB = (FloatingActionButton) findViewById(R.id.alarmdir_floatback);
        alarmListView = (SwipeMenuListView) findViewById(R.id.alarmdir_listview);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.alarmdir_refresh);
        alarmAdd = (FloatingActionButton)findViewById(R.id.alarmdir_add_floatback);

        query();
        alarmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(),Alarm_Add.class);
            startActivity(intent);
            }
        });
        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmDir.this, Main.class);
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

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        alarmListView.setMenuCreator(swipeMenuCreator);
        alarmListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        alarmListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        alarm alarm = new alarm();
                        alarm.setObjectId(alarms.get(position).getObjectId());
                        alarm.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null){
                                    Toast.makeText(MyApplication.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MyApplication.getContext(),"删除成功",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        alarms.remove(position);
                        alarmListAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = alarms.get(position).getAlarmContent();
                String title = alarms.get(position).getAlarmTitle();
                BmobDate deadline = alarms.get(position).getDeadLine();
                String deadlineStr = deadline.getDate();
                Intent intent = new Intent(MyApplication.getContext(),Alarm_Content.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("deadline",deadlineStr);
                startActivity(intent);
            }
        });

    }

    public void query(){
        alarms.clear();
        myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<alarm> query = new BmobQuery<alarm>();
        query.addWhereEqualTo("author",myUser);
        query.order("-updatedAt");
        query.findObjects(new FindListener<alarm>() {
            @Override
            public void done(List<alarm> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();

                }else {
                    for (alarm alarm:list){
                        alarms.add(alarm);
                    }
                }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       alarmListAdapter = new AlarmListAdapter(alarms);
                       alarmListView.setAdapter(alarmListAdapter);
                       if (refreshFlag) {
                           swipeRefreshLayout.setRefreshing(false);
                       }
                   }
               });
            }

        });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
