package com.example.whitebooks.lianxin_android.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.news;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by whitebooks on 17/3/1.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<news> newses;




    private String cellIKind;

    private static final Integer IMAGE_CELL = 1;
    private static final Integer TEXT_CELL = 2;
    private static final Integer VEDIO_CELL = 3;
    public NewsRecyclerViewAdapter(ArrayList<news> newses) {
        this.newses = newses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == IMAGE_CELL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recylceview_imageitem_layout,parent,false);
            return new ImageViewHolder(view);
        }else if (viewType == TEXT_CELL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycleview_textitem_layout,parent,false);
            return new TextViewHolder(view);
        }else if(viewType == VEDIO_CELL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycleview_vedioitem_layout,parent,false);
            return new VedioViewHolder(view);
        }
         return null;
    }

    @Override
    public int getItemViewType(int position){
        cellIKind = newses.get(position).getCellkind();

            if (cellIKind.equals("t")) {
                return TEXT_CELL;
            } else if (cellIKind.equals("i")) {
                return IMAGE_CELL;
            } else {
                return VEDIO_CELL;
            }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof ImageViewHolder){
            Uri uri = Uri.parse(newses.get(position).getImage().getFileUrl());
           ((ImageViewHolder) holder).imageViewImage.setImageURI(uri);
           ((ImageViewHolder) holder).imageViewText.setText(newses.get(position).getTitle());
       }else if (holder instanceof TextViewHolder){
            ((TextViewHolder) holder).textViewText.setText(newses.get(position).getTitle());
       }else if(holder instanceof VedioViewHolder){
           ((VedioViewHolder) holder).vedioViewText.setText(newses.get(position).getTitle());
       }
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }





    public class ImageViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageViewImage;
        TextView  imageViewText;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageViewImage = (SimpleDraweeView) itemView.findViewById(R.id.news_imageitem_image);
            imageViewText = (TextView)itemView.findViewById(R.id.news_imageitem_text);
        }
    }
    public class TextViewHolder extends RecyclerView.ViewHolder{
        TextView textViewText;


        public TextViewHolder(View itemView) {
            super(itemView);
            textViewText = (TextView)itemView.findViewById(R.id.news_textitem_text);
        }
    }

      public class VedioViewHolder extends RecyclerView.ViewHolder {
        TextView vedioViewText;

        public VedioViewHolder(View itemView) {
            super(itemView);
            vedioViewText = (TextView)itemView.findViewById(R.id.news_vedioitem_text);
        }
    }


}
