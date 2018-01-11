package com.example.whitebooks.lianxin_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.news;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static com.example.whitebooks.lianxin_android.R.id.news_imageitem_image;

/**
 * Created by whitebooks on 17/3/3.
 */

public class NewsListAdapter extends BaseAdapter {
    private ArrayList<String > imageurls;
    private ArrayList<news> newses;
    private String cellIKind;
    private Context context;
    private static final int HEAD_VIEWPAGE = 0;
    private static final int IMAGE_CELL = 1;
    private static final int TEXT_CELL = 2;
    private static final int VEDIO_CELL = 3;
    private NewsViewPagerAdapter newsViewPagerAdapter;
    private int currentIndex;


    public NewsListAdapter(ArrayList<String> imageurls, ArrayList<news> newses, Context context) {
        this.imageurls = imageurls;
        this.newses = newses;
        this.context = context;
    }

    @Override
    public int getCount() {

        return newses.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0){
            return imageurls;
        }else {
            return newses.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD_VIEWPAGE;
        }else {
            cellIKind = newses.get(position - 1).getCellkind();
            if (cellIKind.equals("t")) {
                return TEXT_CELL;
            } else if (cellIKind.equals("i")) {
                return IMAGE_CELL;
            } else {
                return VEDIO_CELL;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }



    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewPagerHolder viewPagerHolder;
        ImageViewHolder imageViewHolder = null;
        TextViewHolder textViewHolder = null;
        VedioViewHolder vedioViewHolder = null;
        switch (getItemViewType(position)){
            case HEAD_VIEWPAGE:

                   convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.news_recycleview_header_layout,null);
                    viewPagerHolder = new ViewPagerHolder();
                    viewPagerHolder.myViewPager = (ViewPager)convertView.findViewById(R.id.newsfrag_header_viewpager);
                    newsViewPagerAdapter = new NewsViewPagerAdapter(MyApplication.getContext(),imageurls);
                    viewPagerHolder.myViewPager.setAdapter(newsViewPagerAdapter);

                    currentIndex = imageurls.size() * 1000;
                    viewPagerHolder.myViewPager.setCurrentItem(imageurls.size() * 1000);

                     convertView.setTag(viewPagerHolder);
                break;
            case IMAGE_CELL:

                    convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.news_recylceview_imageitem_layout,null);
                    imageViewHolder = new ImageViewHolder();
                    imageViewHolder.imageViewImage = (SimpleDraweeView)convertView.findViewById(news_imageitem_image);
                    imageViewHolder.imageViewText = (TextView)convertView.findViewById(R.id.news_imageitem_text);

                    Uri uri = Uri.parse(newses.get(position -1).getImage().getFileUrl());
                    imageViewHolder.imageViewImage.setImageURI(uri);

                    imageViewHolder.imageViewText.setText(newses.get(position -1).getTitle());
                    convertView.setTag(imageViewHolder);

                break;
            case TEXT_CELL:

                    convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.news_recycleview_textitem_layout,null);
                    textViewHolder = new TextViewHolder();
                    textViewHolder.textViewText = (TextView)convertView.findViewById(R.id.news_textitem_text);
                    convertView.setTag(textViewHolder);
                    textViewHolder.textViewText.setText(newses.get(position -1).getTitle());

                break;
            case VEDIO_CELL:

                    convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.news_recycleview_vedioitem_layout,null);
                    vedioViewHolder = new VedioViewHolder();

                    vedioViewHolder.vedioViewText = (TextView)convertView.findViewById(R.id.news_vedioitem_text);
                    convertView.setTag(vedioViewHolder);
                    vedioViewHolder.vedioViewText.setText(newses.get(position -1).getTitle());

                break;
            default:
                break;
        }
        return convertView;
    }

    public void updateView(ListView listview, int index) {
        int firstPosition = listview.getFirstVisiblePosition();
        Log.d("TAG",String.valueOf(firstPosition));
        Log.d("TAG",String.valueOf(listview.getChildCount()));
        //防止应为firstvisibleposition大于getchildcount(listview在屏幕中可见的单元格)
        //而造成listview崩溃
        if (firstPosition >= listview.getChildCount()){
            firstPosition = listview.getChildCount()-1;
        }
        View view = listview.getChildAt(firstPosition);
        if (view.getTag() != null) {
            if (view.getTag() instanceof ViewPagerHolder) {
                ViewPagerHolder holder = (ViewPagerHolder) view.getTag();
                holder.myViewPager = (ViewPager) view.findViewById(R.id.newsfrag_header_viewpager);
                holder.myViewPager.setCurrentItem(index);
            }
        } else {
            return;
        }
    }


    public class ImageViewHolder  {
        SimpleDraweeView imageViewImage;
        TextView imageViewText;

    }
    public class TextViewHolder {
        TextView textViewText;

    }

    public class VedioViewHolder {
        TextView vedioViewText;
    }
    public class ViewPagerHolder{
        ViewPager myViewPager;

    }
}
