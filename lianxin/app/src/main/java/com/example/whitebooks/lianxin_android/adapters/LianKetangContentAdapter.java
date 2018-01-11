package com.example.whitebooks.lianxin_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.LianKetangView;
import com.example.whitebooks.lianxin_android.utilclass.media;

import java.util.List;

/**
 * Created by whitebooks on 17/5/19.
 */

public class LianKetangContentAdapter extends RecyclerView.Adapter<LianKetangContentAdapter.ViewHolder> {

    private List<media> medias;
    private Context context;
    private LayoutInflater layoutInflater = null;

    public LianKetangContentAdapter(Context context, List<media> medias) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.medias = medias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.lianketangcontent_item_layout,parent,false);
        final LianKetangContentAdapter.ViewHolder viewHolder = new LianKetangContentAdapter.ViewHolder(view);
        viewHolder.ketang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                media media = medias.get(position);
                Intent intent = new Intent(context, LianKetangView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",media.getTitle());
                intent.putExtra("uri",media.getMedia().getFileUrl());
                intent.putExtra("id",media.getObjectId());
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        media media = medias.get(position);
        holder.textView.setText(media.getTitle());
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View ketang;

        public ViewHolder(View itemView) {
            super(itemView);
            ketang = itemView;
            textView = (TextView) itemView.findViewById(R.id.lianketang_content_tv);
        }
    }

}