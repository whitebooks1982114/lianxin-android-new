package com.example.whitebooks.lianxin_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.LianKetangContent;
import com.example.whitebooks.lianxin_android.utilclass.media;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by whitebooks on 17/5/18.
 */

public class LianKetangAdapter extends RecyclerView.Adapter<LianKetangAdapter.ViewHolder> {
  private List<media> medias;
    private Context context;
    private LayoutInflater layoutInflater = null;

    public LianKetangAdapter(Context context,List<media> medias){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.medias = medias;
    }
    public LianKetangAdapter(List<media> medias){
        this.medias = medias;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.lianketang_item_layout,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.ketang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                media media = medias.get(position);
                Intent intent = new Intent(context, LianKetangContent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",media.getName());
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        media media = medias.get(position);
        holder.simpleDraweeView.setImageURI(media.getImage().getFileUrl());
        holder.textView.setText(media.getName());
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
       SimpleDraweeView simpleDraweeView;
        TextView textView;
        View ketang;
        public ViewHolder(View itemView) {
            super(itemView);
            ketang = itemView;
            simpleDraweeView = (SimpleDraweeView)itemView.findViewById(R.id.lian_ketang_sv);
            textView = (TextView)itemView.findViewById(R.id.lian_ketang_tv);
        }
    }

}
