package com.example.whitebooks.lianxin_android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by whitebooks on 17/2/9.
 */

public class Sign_Login_Frag extends Fragment {

    private View view;
    private Button signInB;

    private Button loginB;

    private EditText usrname;

    private EditText passwordEdit;
    private TextView resetPassword;

    private String username;
    private String password;
    //判断忘记密码点击，决定相关动态fragment是否显示
    private boolean clickFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_login_frag,null);
        clickFlag = false;


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signInB = (Button)view.findViewById(R.id.sign_in_button);
        loginB = (Button)view.findViewById(R.id.login_button);
        passwordEdit = (EditText)view.findViewById(R.id.password_edittext);
        usrname = (EditText)view.findViewById(R.id.login_edittext);
        resetPassword = (TextView)view.findViewById(R.id.resetpassword);


        usrname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              password = s.toString();
            }
        });

        //注册模块
        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                if (myUser != null){
                    Toast.makeText(MyApplication.getContext(),"您已登录",Toast.LENGTH_SHORT).show();
                }else {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main, new SignFrag());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });



        //登录模块

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyUser loginUser = new MyUser();
                loginUser.setUsername(username);
                loginUser.setPassword(password);
                loginUser.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MyApplication.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main,new NewsFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else {
                            Log.e("TAG",e.toString());
                            Toast.makeText(MyApplication.getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        //动态加载发送重置密码的Frag
        final Fragment reset = new Reset_Password_Dynamic_Frag();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickFlag){
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.remove(reset);
                    fragmentTransaction.commit();
                    clickFlag = false;
                }else {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.resetfragment, reset);
                    fragmentTransaction.commit();
                    clickFlag = true;
                }
            }
        });


    }

}
