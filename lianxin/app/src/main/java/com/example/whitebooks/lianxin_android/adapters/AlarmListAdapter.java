package com.example.whitebooks.lianxin_android.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.alarm;

import java.util.ArrayList;

/**
 * Created by whitebooks on 17/3/10.
 */

public class AlarmListAdapter extends BaseAdapter {
    private ArrayList<alarm> alarms;

    public AlarmListAdapter(ArrayList<alarm> alarms) {
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.alarm_list_item_layout,null);
        AlarmTextViewHolder alarmTextViewHolder = new AlarmTextViewHolder();
        alarmTextViewHolder.textViewText = (TextView)convertView.findViewById(R.id.alarm_item_tv);
        alarmTextViewHolder.textViewText.setText(alarms.get(position).getAlarmTitle());
        if (position < 3){
            alarmTextViewHolder.textViewText.setTextColor(Color.RED);
        }else {
            alarmTextViewHolder.textViewText.setTextColor(Color.BLACK);
        }
        return convertView;
    }
    public class AlarmTextViewHolder {
        TextView textViewText;

    }
}
