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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.sign;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by whitebooks on 17/2/9.
 */

public class SignFrag extends Fragment {
    private View view;
    //正确的用户中文姓名
    private String rightChineseName;
    private Button cancel;
    private Button ok;
    private String signUsername;
    private String signPassword;
    private String signChinesename;
    private String signEmail;
    private Boolean isPartyMember;
    private String confirmPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.signfrag,null);
        return view;
    }
    public void queryPAccount(String account) {
        BmobQuery<sign> query = new BmobQuery<sign>();
        query.addWhereEqualTo("paccount",account);
        query.findObjects(new FindListener<sign>() {
            @Override
            public void done(List<sign> list, BmobException e) {
                 if (list != null){
                  for (sign signs:list){
                      rightChineseName = signs.getChinesename();
                  }
                 }else {
                  Toast.makeText(MyApplication.getContext(),"错误",Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cancel = (Button)view.findViewById(R.id.signcancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main,new NewsFragment());
                           fragmentTransaction.addToBackStack(null);
                           fragmentTransaction.commit();
            }
        });



        EditText username = (EditText)view.findViewById(R.id.signusername);
        EditText password = (EditText)view.findViewById(R.id.signpassword);
        EditText confirmpassword = (EditText)view.findViewById(R.id.confirmpassword);
        EditText email = (EditText)view.findViewById(R.id.signemail);
        EditText chinesename = (EditText)view.findViewById(R.id.signchinesename);
        Switch partymember = (Switch)view.findViewById(R.id.partyswitch);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               signUsername = s.toString();
                queryPAccount(signUsername);
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               signPassword = s.toString();
            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               confirmPassword = s.toString();
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              signEmail = s.toString();
            }
        });
        chinesename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              signChinesename = s.toString();
            }
        });
        partymember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPartyMember = isChecked;
            }
        });
        ok = (Button)view.findViewById(R.id.signok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! signPassword.equals(confirmPassword)){
                    Toast.makeText(MyApplication.getContext(), "密码输入错误", Toast.LENGTH_SHORT).show();
                }else if (!signChinesename.equals(rightChineseName)){
                    Toast.makeText(MyApplication.getContext(), "账号与姓名不匹配", Toast.LENGTH_SHORT).show();

                }else {
                    MyUser signUser = new MyUser();
                    signUser.setPassword(signPassword);
                    signUser.setUsername(signUsername);
                    signUser.setChinesename(signChinesename);
                    signUser.setEmail(signEmail);
                    signUser.setParty(isPartyMember);
                    signUser.setExscore(0);
                    signUser.setScore(0);
                    signUser.setPartyscore(0);
                    signUser.setExamscore(0);
                    signUser.setSigntimes(0);
                    signUser.setContsigntimes(0);
                    signUser.signUp(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(MyApplication.getContext(), "注册成功，正在跳转页面请稍后", Toast.LENGTH_SHORT).show();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.main, new NewsFragment());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            } else {
                                Log.e("tag", e.toString());
                                Toast.makeText(MyApplication.getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
