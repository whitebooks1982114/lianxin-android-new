package com.example.whitebooks.lianxin_android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.report;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by whitebooks on 17/2/22.
 */

public class NewMail_Frag extends Fragment {
    private View view;
    private EditText title;
    private EditText content;
    private Button sendbtn;
    private String mailTitle;
    private String mailContent;

    private report myreport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myreport = new report();
        view = inflater.inflate(R.layout.newmailfrag_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title = (EditText)view.findViewById(R.id.newmail_title_edittext);
        content = (EditText)view.findViewById(R.id.newmail_content_edittext);
        sendbtn = (Button)view.findViewById(R.id.newmail_send_btn);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mailTitle = s.toString();
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               mailContent = s.toString();
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText() == null || content.getText() == null){
                    Toast.makeText(MyApplication.getContext(),"标题或内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                   MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                    myreport.setTitle(mailTitle);
                    myreport.setContent(mailContent);
                    myreport.setAuthor(myUser);
                    myreport.setReaded(false);
                    myreport.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e != null){
                                Toast.makeText(MyApplication.getContext(),"发送失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MyApplication.getContext(),"发送成功" ,Toast.LENGTH_SHORT).show();
                               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.main,new NewsFragment());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                    });

                }
            }
        });
    }
}
