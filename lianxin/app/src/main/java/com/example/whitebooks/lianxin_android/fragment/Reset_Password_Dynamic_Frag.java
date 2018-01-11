package com.example.whitebooks.lianxin_android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/2/10.
 */

public class Reset_Password_Dynamic_Frag extends Fragment {
    private View view;
    private String emailAddress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.resetpassword_frag,null);
        EditText emailaddress = (EditText) view.findViewById(R.id.emailaddress);
        emailaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              emailAddress = s.toString();
            }
        });
        Button send = (Button)view.findViewById(R.id.sendemail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.resetPasswordByEmail(emailAddress, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(MyApplication.getContext(),"邮件发送成功，请至邮箱重置密码",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MyApplication.getContext(),"邮件发送失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
