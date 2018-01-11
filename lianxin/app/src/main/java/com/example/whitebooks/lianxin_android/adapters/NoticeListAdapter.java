package com.example.whitebooks.lianxin_android.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.notice;

import java.util.ArrayList;

/**
 * Created by whitebooks on 17/3/10.
 */

public class NoticeListAdapter extends BaseAdapter {
    private ArrayList<notice> notices;

    public NoticeListAdapter(ArrayList<notice> notices) {
        this.notices = notices;
    }

    @Override
    public int getCount() {
        return notices.size();
    }

    @Override
    public Object getItem(int position) {
        return notices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.notice_list_item_layout,null);
        NoticeTextViewHolder noticeTextViewHolder = new NoticeTextViewHolder();
        noticeTextViewHolder.textViewText = (TextView)convertView.findViewById(R.id.notice_item_tv);
        noticeTextViewHolder.textViewText.setText(notices.get(position).getTitle());
        if (position < 3){
            noticeTextViewHolder.textViewText.setTextColor(Color.RED);
        }else {
            noticeTextViewHolder.textViewText.setTextColor(Color.BLACK);
        }
        return convertView;
    }
    public class NoticeTextViewHolder {
        TextView textViewText;

    }
}
