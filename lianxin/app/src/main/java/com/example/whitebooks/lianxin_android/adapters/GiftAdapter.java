package com.example.whitebooks.lianxin_android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.gifts;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whitebooks on 17/5/21.
 */

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder>  {

    private List<gifts> giftses = new ArrayList<gifts>();
    private Integer exchangeNum = 0;
    public GiftAdapter(List<gifts> giftses){
        this.giftses = giftses;
    }

    private ListButtonClickDelegate myDelegate;
    public static interface ListButtonClickDelegate{
        public void addButtonClick(int position);
        public void minusButtonClick(int position);
        public void getSelectedItems(int position,int exNum);
    }

    public void setMyDelegate(ListButtonClickDelegate myDelegate) {
        this.myDelegate = myDelegate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_recycle_item_layout,parent,false);
        final ViewHolder viewHolder  = new ViewHolder(view);

        viewHolder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                exchangeNum = exchangeNum + 1;
                if ( exchangeNum > giftses.get(position).getTotal()) {
                    exchangeNum = giftses.get(position).getTotal();
                }
                viewHolder.exchange_tv.setText(String.valueOf(exchangeNum));
                if (myDelegate != null){
                   myDelegate.addButtonClick(position);
               }
                myDelegate.getSelectedItems(position,exchangeNum);
            }
        });
        viewHolder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                exchangeNum = exchangeNum - 1;
                if (myDelegate != null && exchangeNum >= 0){
                    myDelegate.minusButtonClick(position);
                }
                if (exchangeNum <= 0 ){
                    exchangeNum = 0;
                }
                viewHolder.exchange_tv.setText(String.valueOf(exchangeNum));
                myDelegate.getSelectedItems(position,exchangeNum);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           gifts gifts = giftses.get(position);
          holder.simpleDraweeView.setImageURI(gifts.getImage().getFileUrl());
          holder.name_tv.setText(gifts.getName());
         holder.quantity_tv.setText("剩余" + gifts.getTotal() + "需要积分为" + gifts.getScore());

    }

    @Override
    public int getItemCount() {
        return giftses.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView name_tv;
        TextView quantity_tv;
        TextView exchange_tv;
        ImageButton add_btn;
        ImageButton minus_btn;

        public ViewHolder(View itemView) {
            super(itemView);
           simpleDraweeView = (SimpleDraweeView)itemView.findViewById(R.id.shop_rv_item_sdv);
           name_tv = (TextView)itemView.findViewById(R.id.shop_item_name_tv);
           quantity_tv = (TextView)itemView.findViewById(R.id.shop_item_quantity_tv);
            exchange_tv = (TextView)itemView.findViewById(R.id.shop_item_exchange_tv);
            add_btn = (ImageButton)itemView.findViewById(R.id.shop_item_add_btn);
            minus_btn = (ImageButton)itemView.findViewById(R.id.shop_item_minus_btn);
        }
    }


}
