package com.example.whitebooks.lianxin_android.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by whitebooks on 17/2/27.
 */

public class NewsViewPagerAdapter extends PagerAdapter  {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> urls;

    public NewsViewPagerAdapter(Context context,ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View layout = layoutInflater.inflate(R.layout.news_viewpager_item_layout,null);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView)layout.findViewById(R.id.vp_item_sdv);
        if (urls != null && urls.size() > 0) {
            Uri uri = Uri.parse(urls.get(position % urls.size()));
            simpleDraweeView.setImageURI(uri);
        } else {
            Toast.makeText(MyApplication.getContext(),"网络欠佳",Toast.LENGTH_SHORT).show();
        }
        container.addView(layout);
        return layout;
    }
}
