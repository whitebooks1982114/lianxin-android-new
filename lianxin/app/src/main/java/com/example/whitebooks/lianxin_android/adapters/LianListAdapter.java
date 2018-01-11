package com.example.whitebooks.lianxin_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.bake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whitebooks on 17/5/15.
 */

public class LianListAdapter extends BaseAdapter{
    private List<bake> arrayList;
    public LianListAdapter(ArrayList<bake> bakes) {

        this.arrayList = bakes;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.liankownledge_list_item,null);
        LianTextViewHolder lianTextViewHolder = new LianTextViewHolder();
        lianTextViewHolder.textViewText = (TextView)convertView.findViewById(R.id.lian_item_tv);
        lianTextViewHolder.textViewText.setText(arrayList.get(position).getLiantitle());
        return convertView;
    }


    public class LianTextViewHolder {
        TextView textViewText;

    }
}
