package com.example.whitebooks.lianxin_android.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.activities.News_Image_Content;
import com.example.whitebooks.lianxin_android.activities.News_Text_Content;
import com.example.whitebooks.lianxin_android.activities.News_Vedio_Content;
import com.example.whitebooks.lianxin_android.adapters.NewsListAdapter;
import com.example.whitebooks.lianxin_android.utilclass.CircleTextView;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.headlinenews;
import com.example.whitebooks.lianxin_android.utilclass.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by whitebooks on 17/2/6.
 */

public class NewsFragment extends Fragment {
    View view;
    private Button zyChannelBtn;
    private Button sjChannelBtn;
    private Button jcChannelBtn;
    private CircleTextView headNewsTv;
    private ProgressDialog progressDialog;
    private ListView newsListView;
    public NewsListAdapter newsListAdapter;
    private Timer  timer;
    private TimerTask timerTask;
    private int currentPage;
    private MyUser myUser;
    private static final String CENTRY_CH = "中央";
    private static final String SHAGNJ_CH = "上级";
    private static final String JICEHG_CH = "基层";
    //头条滚动新闻
    private String headNews = "";
   //头条新闻图片轮播数组
    private ArrayList<String> imageUrls = new ArrayList<String>();

    //新闻数组
    private ArrayList<news> newsArrayList = new ArrayList<news>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.newsfrag_layout,null);
        currentPage = imageUrls.size()*1000;

        return  view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zyChannelBtn = (Button)view.findViewById(R.id.newsfrag_zhongyang_button);
        jcChannelBtn = (Button)view.findViewById(R.id.newsfrag_jiceng_button);
        sjChannelBtn = (Button)view.findViewById(R.id.newsfrag_shagnji_button);
        headNewsTv = (CircleTextView) view.findViewById(R.id.newsfrag_headtitlenews_textview);
        zyChannelBtn.setSelected(true);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        newsListView = (ListView)view.findViewById(R.id.news_listview);
        if (myUser != null) {
            queryImage(CENTRY_CH);
            queryHeadNews();
        }else {
            Toast.makeText(MyApplication.getContext(),"前先登录",Toast.LENGTH_SHORT).show();
        }
        zyChannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    queryImage(CENTRY_CH);
                } else {
                    Toast.makeText(MyApplication.getContext(),"前先登录",Toast.LENGTH_SHORT).show();

                }
                zyChannelBtn.setSelected(true);
                sjChannelBtn.setSelected(false);
                jcChannelBtn.setSelected(false);
            }
        });
        sjChannelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    queryImage(SHAGNJ_CH);
                }else  {
                    Toast.makeText(MyApplication.getContext(),"前先登录",Toast.LENGTH_SHORT).show();

                }
                zyChannelBtn.setSelected(false);
                sjChannelBtn.setSelected(true);
                jcChannelBtn.setSelected(false);

            }
        });
        jcChannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    queryImage(JICEHG_CH);
                }else {
                    Toast.makeText(MyApplication.getContext(),"前先登录",Toast.LENGTH_SHORT).show();

                }
                zyChannelBtn.setSelected(false);
                sjChannelBtn.setSelected(false);
                jcChannelBtn.setSelected(true);
            }
        });

         newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       String newskind =  newsArrayList.get(position - 1).getCellkind();
                       String newstitle = newsArrayList.get(position - 1).getTitle();
                       String newscontent = newsArrayList.get(position - 1).getContent();
                       BmobFile newsImage = newsArrayList.get(position - 1).getImage();
                       BmobFile newsVedio = newsArrayList.get(position - 1).getVedio();

                       switch (newskind){
                           case "t":
                               Intent intentt = new Intent(getActivity(), News_Text_Content.class);
                               intentt.putExtra("title",newstitle);
                               intentt.putExtra("content",newscontent);
                               startActivity(intentt);
                               break;
                           case "i":
                               Intent intenti = new Intent(getActivity(), News_Image_Content.class);
                               intenti.putExtra("title",newstitle);
                               intenti.putExtra("content",newscontent);
                               intenti.putExtra("image",newsImage.getFileUrl());
                               startActivity(intenti);

                               break;
                           default:

                               Intent intentv = new Intent(getActivity(), News_Vedio_Content.class);
                               intentv.putExtra("vedio",newsVedio.getFileUrl());
                               startActivity(intentv);

                               break;

                       }
             }
         });
    }
    //查询新闻列表
    public void  queryNewsList(final String level){
        newsArrayList.clear();
        BmobQuery<news> query = new BmobQuery<news>();
        query.addWhereEqualTo("level",level);
        query.findObjects(new FindListener<news>() {
            @Override
            public void done(List<news> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                }else {
                    for(news newsitem:list){
                        newsArrayList.add(newsitem);
                    }
                }
               handler.sendEmptyMessage(3);

            }


        });
    }
    //查询头条新闻
    public void queryHeadNews() {
        BmobQuery<headlinenews> query = new BmobQuery<headlinenews>();
        query.getObject("CyM35558", new QueryListener<headlinenews>() {
            @Override
            public void done(headlinenews headlinenews, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                }else {
                    headNews = headlinenews.getHeadline();
                    handler.sendEmptyMessage(2);
                }
            }

        });
    }
   //查询头条图片
    public void queryImage(final String kind){
        zyChannelBtn.setEnabled(false);
        sjChannelBtn.setEnabled(false);
        jcChannelBtn.setEnabled(false);
        imageUrls.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍后");
        progressDialog.setCancelable(true);
        progressDialog.show();
        BmobQuery<headlinenews> query = new BmobQuery<headlinenews>();
        query.addWhereEqualTo("kind",kind);
        query.findObjects(new FindListener<headlinenews>() {
            @Override
            public void done(List<headlinenews> list, BmobException e) {
                if (e != null){
                    Toast.makeText(MyApplication.getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                }else {
                    for (headlinenews headlinenew : list) {
                        BmobFile bmobFile = headlinenew.getImage();
                        String imageUrl = bmobFile.getFileUrl();
                        imageUrls.add(imageUrl);
                    }
                }
                switch (kind){
                    case CENTRY_CH:
                        handler.sendEmptyMessage(4);
                        break;
                    case SHAGNJ_CH:
                        handler.sendEmptyMessage(0);
                        break;
                    case JICEHG_CH:
                        handler.sendEmptyMessage(1);
                        break;
                }

            }

        });
    }

    //主线程更新轮播
      Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 0:
                   queryNewsList(SHAGNJ_CH);

                    break;
                case 1:
                    queryNewsList(JICEHG_CH);
                    break;

                case 2:
                    headNewsTv.setText(headNews);
                    headNewsTv.setSelected(true);

                    break;

                case 3:


                    newsListAdapter = new NewsListAdapter(imageUrls,newsArrayList,MyApplication.getContext());
                    newsListView.setAdapter(newsListAdapter);
                    progressDialog.dismiss();
                    zyChannelBtn.setEnabled(true);
                    sjChannelBtn.setEnabled(true);
                    jcChannelBtn.setEnabled(true);
                    currentPage = imageUrls.size() * 1000;
                    if (timer != null){
                        timer.cancel();
                    }
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            currentPage += 1;

                            handler.sendEmptyMessage(5);
                        }
                    };

                    timer.schedule(timerTask,1000,3000);

                    break;
                case 4:
                    queryNewsList(CENTRY_CH);
                    break;
                case 5:

                    newsListAdapter.updateView(newsListView,currentPage);
                    break;
            }
        }
    };

}
