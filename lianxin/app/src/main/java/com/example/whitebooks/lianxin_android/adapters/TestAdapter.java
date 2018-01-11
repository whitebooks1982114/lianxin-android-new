package com.example.whitebooks.lianxin_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.Question;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.tests;
import com.example.whitebooks.lianxin_android.utilclass.usertestscore;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by whitebooks on 17/5/22.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private Context mContext;
    private List<tests> testses = new ArrayList<tests>();
    private List<usertestscore> usertestscores = new ArrayList<usertestscore>();
    private MyUser myUser;
    public TestAdapter(Context context,List<tests> testses,List<usertestscore> usertestscores){
        this.testses = testses;
        this.usertestscores = usertestscores;
        this.mContext = context;
    }
    public TestAdapter(List<tests> testses,List<usertestscore> usertestscores){
        this.testses = testses;
        this.usertestscores = usertestscores;
    }

    public TestAdapter(List<tests> testses){
        this.testses = testses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        myUser = BmobUser.getCurrentUser(MyUser.class);
            View view = LayoutInflater.from(mContext).inflate(R.layout.test_item_layout,parent,false);
          final ViewHolder viewHolder = new ViewHolder(view);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final tests tests = testses.get(position);

         usertestscore usertestscore = usertestscores.get(position);
//          if (position < usertestscores.size()) {
//              usertestscore = usertestscores.get(position);
//          }else {
//              usertestscore = new usertestscore();
//              usertestscore.setAnswerednum(0);
//          }
         boolean partyFlag = tests.isparty();
         if (partyFlag == true) {
             holder.imageView.setImageResource(R.drawable.party);
         }else {
             holder.imageView.setImageResource(R.drawable.liantest);
         }
         holder.titletv.setText(tests.getTitle());
         holder.scoretv.setText("试题积分" + String.valueOf(tests.getScore()));
         holder.numbertv.setText("试题总数" + tests.getQuestions() + "已答题数" + usertestscore.getAnswerednum());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser == null){
                    Toast.makeText(MyApplication.getContext(),"您未登录",Toast.LENGTH_SHORT).show();

                } else if (myUser.getParty() == false && tests.isparty() == true){
                  Toast.makeText(MyApplication.getContext(),"您无法参加党员试题",Toast.LENGTH_SHORT).show();
              }else {
                  Intent intent = new Intent(mContext, Question.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  intent.putExtra("id",tests.getTestid());
                  intent.putExtra("questionNum",tests.getQuestions());
                  mContext.startActivity(intent);
              }

            }
        });

    }

    @Override
    public int getItemCount() {
        return testses.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView titletv;
        TextView numbertv;
        TextView scoretv;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            imageView = (ImageView) view.findViewById(R.id.test_surface_iv);
            titletv = (TextView)view.findViewById(R.id.test_title_tv);
            numbertv = (TextView)view.findViewById(R.id.test_questions_tv);
            scoretv = (TextView)view.findViewById(R.id.test_score_tv);

        }
    }

}
