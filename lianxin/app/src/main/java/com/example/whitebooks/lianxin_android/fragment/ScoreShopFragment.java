package com.example.whitebooks.lianxin_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.adapters.GiftAdapter;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.exchange;
import com.example.whitebooks.lianxin_android.utilclass.gifts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/5/15.
 */

public class ScoreShopFragment extends Fragment implements GiftAdapter.ListButtonClickDelegate {
    private RecyclerView recyclerView;
    private TextView textView;
    private Button button;
    private GiftAdapter giftAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<gifts> giftses = new ArrayList<gifts>();
    private Integer exScore;
    private MyUser myUser;
    //已选择的奖品
    private List<BmobObject> selected = new ArrayList<BmobObject>();
    //存放选择的兑换奖品
    private Map<Integer,exchange> map = new HashMap<Integer, exchange>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.scoreshop_layout,null);
       myUser = BmobUser.getCurrentUser(MyUser.class);

       if (myUser != null){
           exScore = myUser.getExscore();
           recyclerView = (RecyclerView)view.findViewById(R.id.shop_rv);
           textView = (TextView)view.findViewById(R.id.shop_tv);
           textView.setText("您剩余可兑换积分为" + String.valueOf(exScore));
           textView.setTextColor(Color.BLUE);
           button = (Button)view.findViewById(R.id.shop_exchange_btn);
        query();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exScore < 0) {
                    Toast.makeText(MyApplication.getContext(),"积分不足",Toast.LENGTH_SHORT).show();
                    return;
                }
                Iterator entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    exchange exchange = (com.example.whitebooks.lianxin_android.utilclass.exchange) entry.getValue();
                    selected.add(exchange);
                }
                new BmobBatch().insertBatch(selected).doBatch(new QueryListListener<BatchResult>() {

                    @Override
                    public void done(List<BatchResult> o, BmobException e) {
                        if(e==null){
                            for(int i=0;i<o.size();i++){
                                BatchResult result = o.get(i);
                                BmobException ex =result.getError();
                                if(ex==null){

                                }else{
                                   Log.d("TAG","第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                                }
                            }
                            myUser.setExscore(exScore);
                            myUser.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e != null){
                                        Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                        Log.d("TAG",e.toString());
                                    }
                                }
                            });
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });

                Toast.makeText(MyApplication.getContext(),"兑换成功",Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main,new NewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
       }else {
           Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
       }

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (myUser!=null) {
            giftAdapter.setMyDelegate(null);
        }
    }

    public void query(){
        BmobQuery<gifts> query = new BmobQuery<gifts>();
        query.findObjects(new FindListener<gifts>() {
            @Override
            public void done(List<gifts> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询错误",Toast.LENGTH_SHORT).show();
                }else {
                    for (gifts gifts : list) {
                        giftses.add(gifts);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
                            giftAdapter = new GiftAdapter(giftses);
                            giftAdapter.setMyDelegate(ScoreShopFragment.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(giftAdapter);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addButtonClick(int position) {
        exScore = exScore - giftses.get(position).getScore();
        textView.setText("您剩余可兑换积分为" + String.valueOf(exScore));
        if (exScore < 0){
            textView.setTextColor(Color.RED);
        }else {
            textView.setTextColor(Color.BLUE);
        }
    }

    @Override
    public void minusButtonClick(int position) {
        exScore = exScore + giftses.get(position).getScore();
        textView.setText("您剩余可兑换积分为" + String.valueOf(exScore));
        if (exScore < 0){
            textView.setTextColor(Color.RED);
        }else {
            textView.setTextColor(Color.BLUE);
        }
    }

    @Override
    public void getSelectedItems(int position,int exNum) {

        exchange exchange = new exchange();
        exchange.setUsername(myUser.getUsername());
        exchange.setGiftname(giftses.get(position).getName());
        exchange.setQuantity(String.valueOf(exNum));
        map.put(position,exchange);

    }
}
