package com.example.whitebooks.lianxin_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.LianKetang;
import com.example.whitebooks.lianxin_android.activities.LianKownledge;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by whitebooks on 17/2/6.
 */

public class WikiFragment extends Fragment {
    private ImageButton lzzs_btn;
    private ImageButton xdgw_btn;
    private ImageButton alfx_btn;
    private ImageButton zxkt_btn;
    private MyUser myUser;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wikifrag_layout,null);
        lzzs_btn = (ImageButton)view.findViewById(R.id.lianzhenzhishi_btn);
        zxkt_btn = (ImageButton)view.findViewById(R.id.zaixianketang_btn);
        xdgw_btn = (ImageButton)view.findViewById(R.id.xinde_btn);
        alfx_btn = (ImageButton)view.findViewById(R.id.anli_btn);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        intent = new Intent(getActivity(), LianKownledge.class);
        lzzs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser != null){

                    intent.putExtra("Category","1");
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();

                }
            }
        });
        zxkt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser != null){
                    Intent intent = new Intent(getActivity(), LianKetang.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();

                }
            }
        });
        xdgw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser != null){
                    intent.putExtra("Category","3");
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();

                }
            }
        });
        alfx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser != null){
                    intent.putExtra("Category","2");
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }
}
