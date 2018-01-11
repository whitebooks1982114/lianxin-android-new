package com.example.whitebooks.lianxin_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.Mail_Inbox;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.report;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by whitebooks on 17/2/22.
 */

public class Mail_Frag extends Fragment {

    private View view;
    private ImageButton sendboxbtn;
    private ImageButton recieveboxbtn;
    private Button cancel;
    private Button sendMail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mailfrag_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancel = (Button)view.findViewById(R.id.mail_cancel);
        sendMail = (Button)view.findViewById(R.id.mail_newmail);
        sendboxbtn = (ImageButton)view.findViewById(R.id.sendbox);
        recieveboxbtn = (ImageButton)view.findViewById(R.id.recievebox);
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        sendboxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mail_Inbox.class);
                intent.putExtra("Flag",false);
                startActivity(intent);
            }
        });
        recieveboxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mail_Inbox.class);
                intent.putExtra("Flag",true);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main,new NewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, new NewMail_Frag());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        BmobQuery<report> query1 = new BmobQuery<report>();
        query1.addWhereEqualTo("author",myUser);
        BmobQuery<report> query2 = new BmobQuery<report>();
        query2.addWhereEqualTo("readed",false);
        BmobQuery<report> query3 = new BmobQuery<report>();
        query3.addWhereEqualTo("adminsend",true);
        BmobQuery<report> mainQuery = new BmobQuery<report>();
        List<BmobQuery<report>> queries = new ArrayList<BmobQuery<report>>();
        queries.add(query1);
        queries.add(query2);
        queries.add(query3);
        mainQuery.and(queries);
        mainQuery.count(report.class, new CountListener() {
            @Override
            public void done(final Integer integer, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    recieveboxbtn.post(new Runnable() {
                        @Override
                        public void run() {
                            if (integer == 0){
                                recieveboxbtn.setImageResource(R.drawable.inbox);
                            }else {
                                recieveboxbtn.setImageResource(R.drawable.inboxnew);
                            }
                        }
                    });
                }
            }
        });

    }
}
