package com.example.whitebooks.lianxin_android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.bake;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by whitebooks on 17/2/27.
 */

public class User_Achievment_Fragment extends Fragment {
    private View view;
    private TextView signTimes;
    private TextView baikeScore;
    private TextView testScore;
    private TextView exchangeScore;
    private TextView partyScore;
    private MyUser myUser;
    private Button backbtn;

    private Integer exScoreCount;
    private Integer signTimesCount;
    private Integer baikeScoreCount;
    private Integer testScoreCount;
    private Integer partyScoreCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_achievment_frag_layout,null);
        signTimes = (TextView)view.findViewById(R.id.userachievment_signtimes_textview);
        baikeScore = (TextView)view.findViewById(R.id.userachievment_baike_textview);
        testScore = (TextView)view.findViewById(R.id.userachievment_test_textview);
        exchangeScore = (TextView)view.findViewById(R.id.userachievment_score_textview);
        partyScore = (TextView)view.findViewById(R.id.userachievment_party_textview);
        backbtn = (Button)view.findViewById(R.id.userachievment_back_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myUser = BmobUser.getCurrentUser(MyUser.class);

        if (myUser != null) {
            signTimesCount = myUser.getSigntimes();
            if (signTimesCount == null){
                signTimesCount = 0;
            }
            signTimes.setText(signTimesCount + "");
            exScoreCount = myUser.getExscore();
            if (exScoreCount == null){
                exScoreCount =0;
            }

            exchangeScore.setText(exScoreCount + "");
            testScoreCount = myUser.getExamscore();
            if (testScoreCount == null){
                testScoreCount = 0;
            }
            testScore.setText(testScoreCount + "");
            partyScoreCount = myUser.getPartyscore();
            if (partyScoreCount == null){
                partyScoreCount = 0;
            }
            partyScore.setText(partyScoreCount + "");

            BmobQuery<bake> query = new BmobQuery<bake>();
            query.addWhereEqualTo("author", myUser);
            query.include("author");
            query.count(bake.class, new CountListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e != null) {
                        Toast.makeText(MyApplication.getContext(), "查询失败", Toast.LENGTH_SHORT).show();
                    } else {
                        baikeScoreCount = integer;
                        baikeScore.setText(baikeScoreCount + "");
                    }
                }
            });

        }else {
            exchangeScore.setText("请您登陆");
            signTimes.setText("请您登陆");
            baikeScore.setText("请您登陆");
            testScore.setText("请您登陆");
        }
       backbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.main,new NewsFragment());
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
           }
       });

    }
}
