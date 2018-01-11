package com.example.whitebooks.lianxin_android.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.TestAdapter;
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

/**
 * Created by whitebooks on 17/2/6.
 */

public class TestFragment extends Fragment {
    private RecyclerView recyclerView;
    private TestAdapter testAdapter;
    private List<tests> testses = new ArrayList<tests>();
    private MyUser myUser;
    private ProgressDialog progressDialog;
    private List<usertestscore> answerednums = new ArrayList<usertestscore>();
    private GridLayoutManager gridLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.testfrag_layout,null);
        recyclerView = (RecyclerView)view.findViewById(R.id.test_rv);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("正在刷新");
        progressDialog.setMessage("请稍后");
        progressDialog.setCancelable(true);
        progressDialog.show();
        query();
        }else {
            Toast.makeText(MyApplication.getContext(),"您还没登录呢",Toast.LENGTH_SHORT).show();

        }
        return view;
    }
    public void queryUserScore(){
        answerednums.clear();
        BmobQuery<usertestscore> query1 = new BmobQuery<usertestscore>();
        query1.order("testid");
        query1.addWhereEqualTo("name",myUser.getUsername());
        query1.findObjects(new FindListener<usertestscore>() {
            @Override
            public void done(List<usertestscore> list, BmobException e) {
               for (int i = 0; i< testses.size();i++) {
                   //用于判断用户是否完成过试卷，如完成则将其添加到answerednums数组
                   boolean flag = false;
                   for (usertestscore usertestscore : list) {
                       if (testses.get(i).getTestid() == usertestscore.getTestid()){
                           answerednums.add(usertestscore);
                           flag = true;
                           break;
                       }
                   }
                   if (flag == true){
                       continue;
                   }else {
                       usertestscore usertestscore1 = new usertestscore();
                       usertestscore1.setAnswerednum(0);
                       answerednums.add(usertestscore1);
                   }
               }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        gridLayoutManager = new GridLayoutManager(MyApplication.getContext(),3);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        testAdapter = new TestAdapter(testses, answerednums);
                        recyclerView.setAdapter(testAdapter);
                        progressDialog.dismiss();
                    }
                });
            }

        });
    }

   Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case 0:
                   queryUserScore();
           }

       }
   };

    public void query(){
        testses.clear();
        BmobQuery<tests> query = new BmobQuery<tests>();
        query.order("testid");
        query.findObjects(new FindListener<tests>() {
            @Override
            public void done(List<tests> list, BmobException e) {
                if (e == null) {

                         for(tests tests:list){
                             testses.add(tests);
                         }
                    handler.sendEmptyMessage(0);

                }
            }
        });

    }
}
