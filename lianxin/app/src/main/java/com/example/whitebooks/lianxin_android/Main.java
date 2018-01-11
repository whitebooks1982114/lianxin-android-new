package com.example.whitebooks.lianxin_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.activities.User_Setting;
import com.example.whitebooks.lianxin_android.fragment.AlarmFragment;
import com.example.whitebooks.lianxin_android.fragment.Mail_Frag;
import com.example.whitebooks.lianxin_android.fragment.NewsFragment;
import com.example.whitebooks.lianxin_android.fragment.ScoreShopFragment;
import com.example.whitebooks.lianxin_android.fragment.Sign_Login_Frag;
import com.example.whitebooks.lianxin_android.fragment.TestFragment;
import com.example.whitebooks.lianxin_android.fragment.Update_Userinfo_Frag;
import com.example.whitebooks.lianxin_android.fragment.User_Achievment_Fragment;
import com.example.whitebooks.lianxin_android.fragment.WikiFragment;
import com.example.whitebooks.lianxin_android.service.LongRunTimeServie;
import com.example.whitebooks.lianxin_android.service.NoticeRunTimeService;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.alarm;
import com.example.whitebooks.lianxin_android.utilclass.notice;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.whitebooks.lianxin_android.R.id.avator;
import static com.example.whitebooks.lianxin_android.R.id.sign;

/**
 * Created by whitebooks on 17/2/6.
 */


@ContentView(R.layout.main_layout)

public class Main extends FragmentActivity {
    private FragmentManager fragmentManager;
    @ViewInject(R.id.tabs)
    private RadioGroup radioGroup;
    @ViewInject(R.id.news)
    private RadioButton newsButton;

    private TextView signScoreTextview;
     //通知、提醒开关及提醒时间
    private Boolean alarmOn;
    private Boolean noticeOn;
    private Integer alarmtime;
    private ImageButton signButton;

    private android.support.v7.widget.Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;
    //用户名和中文名
    private TextView usernameTextView;
    private String username;
    private TextView chinesenameTextView;
    private String chinesename;
    private CircleImageView avator1;
    private ImageButton userSettingIB;

    private MyUser myUser;
    //签到用变量
    private Integer signTimes;
    private Integer contsignTims;
    private Boolean dateFlag;
    private Integer totalScore;
    private Integer exchangScore;
    private String date1;
    private Date date2;

    private Date date;

    private ArrayList<notice> notices = new ArrayList<notice>();
    private ArrayList<alarm>  alarms = new ArrayList<alarm>();
     //intent--通知和提醒 --周期性任务
    private Intent intent;
    private Intent Nintent;

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //初始化Bmob
        Bmob.initialize(this,"e1f6b4e604e2fc50b6ef6de799d8bbc3");


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        newsButton.setChecked(true);


        changeFragment(new NewsFragment(), false);
        toolbar.setTitle("廉兴连心");
        //打开侧拉菜单
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
               View navHeadView = navigationView.getHeaderView(0);
               usernameTextView = (TextView)navHeadView.findViewById(R.id.username);
               chinesenameTextView = (TextView)navHeadView.findViewById(R.id.chinesename);


               if(myUser != null) {
                   username = (String)BmobUser.getObjectByKey("username");
                   usernameTextView.setText(username);
                   chinesename = (String )BmobUser.getObjectByKey("chinesename");
                   chinesenameTextView.setText(chinesename);

               }else {
                   usernameTextView.setText("用户名");
                   chinesenameTextView.setText("中文姓名");
               }
               drawerLayout.openDrawer(GravityCompat.START);
           }
       });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                switch (item.getItemId()){
                    case R.id.sign_and_login:
                        drawerLayout.closeDrawers();
                        Sign_Login_Frag sign_login_frag = new Sign_Login_Frag();
                        changeFragment(sign_login_frag,true);
                        break;
                    case R.id.userchange:

                        if (myUser != null){
                        drawerLayout.closeDrawers();
                        Update_Userinfo_Frag update_userinfo_frag = new Update_Userinfo_Frag();
                        changeFragment(update_userinfo_frag,true);
                        } else {
                            Toast.makeText(MyApplication.getContext(),"您还没登录呢",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.userachievment:
                        if (myUser != null){
                            drawerLayout.closeDrawers();
                            User_Achievment_Fragment user_achievment_fragment = new User_Achievment_Fragment();
                            changeFragment(user_achievment_fragment,true);
                        } else {
                            Toast.makeText(MyApplication.getContext(),"您还没登录呢",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.logout:
                        BmobUser.logOut();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.mail:
                        if (myUser != null){
                            drawerLayout.closeDrawers();
                            Mail_Frag mail_frag = new Mail_Frag();
                            changeFragment(mail_frag,true);
                        } else {
                            Toast.makeText(MyApplication.getContext(),"您还没登录呢",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        navigationView.setItemIconTintList(null);

        View navHeadView = navigationView.getHeaderView(0);
        //用户菜单签到按钮
         signButton = (ImageButton) navHeadView.findViewById(sign);
        signScoreTextview = (TextView)navHeadView.findViewById(R.id.signscore_textview);

        //用户菜单设置按钮
        userSettingIB =(ImageButton) navHeadView.findViewById(R.id.usersetting);

        signButton.setBackgroundColor(Color.TRANSPARENT);
        userSettingIB.setBackgroundColor(Color.TRANSPARENT);
        //用户菜单头像
        avator1 = (CircleImageView) navHeadView.findViewById(avator);
        //获取头像
        SharedPreferences pref = getSharedPreferences("image",MODE_PRIVATE);
        String path = pref.getString("path","");
        if (path != null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            avator1.setImageBitmap(bitmap);
        }else{
            Toast.makeText(MyApplication.getContext(),"头像加载失败",Toast.LENGTH_SHORT).show();
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.news:
                    changeFragment(new NewsFragment(), true);
                    break;
                case R.id.alarm:
                    changeFragment(new AlarmFragment(), true);
                    break;
                case R.id.wiki:
                    changeFragment(new WikiFragment(), true);
                    break;
                case R.id.test:
                    changeFragment(new TestFragment(), true);
                    break;
                case R.id.shop:
                    changeFragment(new ScoreShopFragment(), true);
                    break;
                default:
                    break;
        }
    }
    });
   }

    public void changeFragment(Fragment fragment, boolean isFirst) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main, fragment);
        if (!isFirst) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

   //隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myUser = BmobUser.getCurrentUser(MyUser.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        date1 = preferences.getString("signeddate","");
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        date2 = calendar.getTime();

        calendar.setTime(date2);
        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        String strDate = format.format(date).toString();


        //判断date1是不是date2的前一天

        if (strDate .equals(date1)){
            dateFlag = true;
        }else {
            dateFlag = false;
        }
        String strDate2 = format.format(date2);

       // 判断date1与date2是不是同一天
        if(myUser != null) {
            if (strDate2.equals(date1)) {
                signButton.setClickable(false);
                signButton.setImageResource(R.drawable.yiqiandao);
            } else {
                signButton.setClickable(true);
                signButton.setImageResource(R.drawable.qiandao);
            }
            Integer currentSignTimes = myUser.getSigntimes();
            Integer continuousSignTimes = myUser.getContsigntimes();

            totalScore = myUser.getScore();
            exchangScore = myUser.getExscore();
            if (currentSignTimes != null) {
                signTimes = currentSignTimes;
            } else {
                signTimes = 0;
            }
            if (continuousSignTimes != null) {
                contsignTims = continuousSignTimes;
            } else {
                contsignTims = 0;
            }
        }else {
            signButton.setClickable(false);
            signButton.setImageResource(R.drawable.yiqiandao);
        }

        //签到功能
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myUser == null){
                    Toast.makeText(MyApplication.getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }else {

                    if (signTimes == null) {
                        signTimes = 1;
                    }else {
                        signTimes += 1;
                    }
                    if (contsignTims == null){
                        contsignTims = 1;
                    }else {
                        if(dateFlag){
                            contsignTims += 1;
                        }else {
                            contsignTims = 1;
                        }
                    }
                    if (totalScore == null){
                        totalScore = 1;
                    }else {
                        if(contsignTims <= 7){
                            totalScore += contsignTims;
                            signScoreTextview.setText(contsignTims + "");
                        }else {
                            totalScore += 7;
                            signScoreTextview.setText("7");
                        }
                    }

                    if (exchangScore == null){
                        exchangScore = 1;
                    }else {
                        if(contsignTims <= 7){
                            exchangScore += contsignTims;
                        }else {
                            exchangScore += 7;
                        }
                    }

                    myUser.setSigntimes(signTimes);
                    myUser.setContsigntimes(contsignTims);
                    myUser.setScore(totalScore);
                    myUser.setExscore(exchangScore);
                    myUser.update(myUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e != null){
                                Log.d("TAG",e.getMessage());
                                Toast.makeText(MyApplication.getContext(),"签到失败" ,Toast.LENGTH_SHORT).show();
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        signButton.setImageResource(R.drawable.yiqiandao);
                                        signButton.setClickable(false);
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = new Date(System.currentTimeMillis());
                                        date1 = simpleDateFormat.format(date);
                                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                        editor.putString("signeddate",date1);
                                        editor.apply();

                                        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
                                        final AlphaAnimation animation1 = new AlphaAnimation(1.0f,0.0f);

                                        animation.setDuration(2000);
                                        animation1.setDuration(2000);
                                        animation.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                signScoreTextview.startAnimation(animation1);
                                                signScoreTextview.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                        signScoreTextview.startAnimation(animation);

                                    }
                                });

                            }
                        }
                    });


                }
            }
        });

        userSettingIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                if (myUser != null){
                    drawerLayout.closeDrawers();
                    Intent intent = new Intent(Main.this, User_Setting.class);
                    startActivity(intent);


                }
            }
        });
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        alarmOn = pref.getBoolean("isAlarmOn",true);
        noticeOn = pref.getBoolean("isNoticeOn",true);
        alarmtime = pref.getInt("settime",1);


        calendar.setTime(date2);

        calendar.add(calendar.DATE,alarmtime);
        Date nextDay = calendar.getTime();

        if(myUser != null){
            String nowUserName = myUser.getUsername();
            BmobQuery<notice> noticequery = new BmobQuery<notice>();
            BmobQuery<MyUser> userquery = new BmobQuery<MyUser>();
            userquery.addWhereEqualTo("username",nowUserName);
            noticequery.addWhereMatchesQuery("relation","_User",userquery);
            BmobQuery<notice> noticequery1 = new BmobQuery<notice>();
            BmobQuery<notice> noticequery2 = new BmobQuery<notice>();
            BmobQuery<notice> noticequerymain = new BmobQuery<notice>();

            noticequery1.addWhereGreaterThan("deadline",new BmobDate((date2)));
            noticequery2.addWhereLessThan("deadline",new BmobDate(nextDay));
            List<BmobQuery<notice>> queries = new ArrayList<BmobQuery<notice>>();
            queries.add(noticequery1);
            queries.add(noticequery2);
            queries.add(noticequery);
            noticequerymain.and(queries);
            noticequerymain.findObjects(new FindListener<notice>() {
                @Override
                public void done(List<notice> list, BmobException e) {
                    if (e != null) {
                        Toast.makeText(MyApplication.getContext(), "查询错误", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.getMessage() + "notice");
                    } else {
                        if (list.size() > 0) {
                            MyApplication.setNoticArrayIsNotNull(true);
                        } else {
                            MyApplication.setNoticArrayIsNotNull(false);

                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (noticeOn){
                               if (MyApplication.getNoticArrayIsNotNull()) {
                                   Nintent = new Intent(MyApplication.getContext(),NoticeRunTimeService.class);
                                   startService(Nintent);

                               }
                            }else {
                              stopService(Nintent);
                            }
                        }
                    });
                }

            });

            BmobQuery<alarm> alarmquery = new BmobQuery<alarm>();
            alarmquery.addWhereEqualTo("author",myUser);
            BmobQuery<alarm> alarmquery1 = new BmobQuery<alarm>();
            BmobQuery<alarm> alarmquery2 = new BmobQuery<alarm>();
            BmobQuery<alarm> alarmquerymain = new BmobQuery<alarm>();

            alarmquery1.addWhereGreaterThan("deadLine",new BmobDate((date2)));
            alarmquery2.addWhereLessThan("deadLine",new BmobDate(nextDay));
            List<BmobQuery<alarm>> alarmqueries = new ArrayList<BmobQuery<alarm>>();
            alarmqueries.add(alarmquery1);
            alarmqueries.add(alarmquery2);
            alarmqueries.add(alarmquery);
            alarmquerymain.and(alarmqueries);
            alarmquerymain.findObjects(new FindListener<alarm>() {
                @Override
                public void done(List<alarm> list, BmobException e) {
                    if (e != null){
                        Toast.makeText(MyApplication.getContext(),"查询错误",Toast.LENGTH_SHORT).show();

                    }else{
                        Log.d("TAG", list.size() + "");
                        if(list.size() > 0){

                            MyApplication.setAlarmArrayIsNotNull(true);
                         }else {
                            MyApplication.setAlarmArrayIsNotNull(false);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (alarmOn){

                                 if (MyApplication.getAlarmArrayIsNotNull()) {
                                   intent = new Intent(MyApplication.getContext(), LongRunTimeServie.class);

                                     startService(intent);
                                 }
                            }else {
                               stopService(intent);
                            }
                        }
                    });
                }

            });

        }
    }


}
