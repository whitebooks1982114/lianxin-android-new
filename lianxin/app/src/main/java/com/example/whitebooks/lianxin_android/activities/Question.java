package com.example.whitebooks.lianxin_android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.Main;
import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;
import com.example.whitebooks.lianxin_android.utilclass.question;
import com.example.whitebooks.lianxin_android.utilclass.usertestscore;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by whitebooks on 17/5/22.
 */

public class Question extends Activity implements View.OnClickListener {
    private TextView time_tv;
    private TextView question_tv;
    private Button answera_btn;
    private Button answerb_btn;
    private Button answerc_btn;
    private Button answerd_btn;
    private Button back_btn;
    private Button next_btn;
    private Intent intent;
    //usertestscore 表的objectid
    private String usertestid;
    private Integer id;
    //当前用户回答问题情况
    private List<usertestscore> usertestscores = new ArrayList<usertestscore>();
    private MyUser myUser;
    private Integer questionIndex = 0;
    private String rightAnswer;
    private ProgressDialog progressDialog;
    //正确答案的数字
    private Integer rightAnswerNum;
    //回答的正确题目数
    private Integer rightNum = 0;
    //哪个按钮被点击，预设为0，以防止用户未点击选项按钮而直接点击下一题
    private Integer clickAnswerNum = 0;
    //题目总数
    private Integer totalQuestionNum;
    private List<question> questions = new ArrayList<question>();
    private CountDownTimer timer = new CountDownTimer(50000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished / 1000 >= 10) {
                time_tv.setText("00:" + millisUntilFinished / 1000  );

            } else {
              time_tv.setText("00:0" + millisUntilFinished / 1000  );
            }
        }

        @Override
        public void onFinish() {
           if (questionIndex <= totalQuestionNum-2){
            questionIndex += 1;
           queryQuestion(questionIndex);
           }else {
               //调整到结果界面
               timer.cancel();
               Intent intent = new Intent(MyApplication.getContext(),Result.class);
               intent.putExtra("right",rightNum);
               intent.putExtra("testid",id);
               intent.putExtra("usertestid",usertestid);
               startActivity(intent);
           }
            timer.start();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_question_layout);

        myUser = BmobUser.getCurrentUser(MyUser.class);
        time_tv = (TextView)findViewById(R.id.time_tv);
        timer.start();
        question_tv = (TextView)findViewById(R.id.question_tv);
        answera_btn = (Button)findViewById(R.id.answera_btn);
        answerb_btn = (Button)findViewById(R.id.answerb_btn);
        answerc_btn = (Button)findViewById(R.id.answerc_btn);
        answerd_btn = (Button)findViewById(R.id.answerd_btn);
        back_btn = (Button)findViewById(R.id.question_back_btn);
        intent = getIntent();
        id = intent.getIntExtra("id",0);
        totalQuestionNum = intent.getIntExtra("questionNum",0);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex += 1;
                //如果已经回答过题目了
                if (usertestid != null){
                    usertestscore usertestscore = new usertestscore();
                    usertestscore.setRight(rightNum);
                    usertestscore.setAnswerednum(questionIndex);
                    usertestscore.update(usertestid, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e != null){
                                Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(MyApplication.getContext(),Main.class);
                                startActivity(intent);
                            }
                        }
                    });
                }//如果没有回答过题目
                else {
                  usertestscore usertestscore = new usertestscore();
                   usertestscore.setSuccess(false);
                    usertestscore.setRight(rightNum);
                    usertestscore.setAnswerednum(questionIndex);
                    usertestscore.setName(myUser.getUsername());
                    usertestscore.setTestid(id);
                    usertestscore.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e != null){
                                Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(MyApplication.getContext(),Main.class);
                                startActivity(intent);
                            }

                        }
                    });
                }

            }
        });
        next_btn = (Button)findViewById(R.id.question_next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                if (questionIndex <= totalQuestionNum - 2){
                    questionIndex += 1;
                    queryQuestion(questionIndex);
                }else {
                    //调整到结果界面
                    timer.cancel();
                    Intent intent = new Intent(MyApplication.getContext(),Result.class);
                    intent.putExtra("right",rightNum);
                    intent.putExtra("testid",id);
                    intent.putExtra("usertestid",usertestid);
                    startActivity(intent);
                }
            }
        });

        answera_btn.setBackgroundColor(Color.LTGRAY);
        answerb_btn.setBackgroundColor(Color.LTGRAY);
        answerc_btn.setBackgroundColor(Color.LTGRAY);
        answerd_btn.setBackgroundColor(Color.LTGRAY);
        answera_btn.setOnClickListener(this);
        answerb_btn.setOnClickListener(this);
        answerc_btn.setOnClickListener(this);
        answerd_btn.setOnClickListener(this);
        queryQuestionIndex();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.answera_btn:
                clickAnswerNum = 1;
                break;
            case R.id.answerb_btn:
                clickAnswerNum = 2;
                break;
            case R.id.answerc_btn:
                clickAnswerNum = 3;
                break;
            case R.id.answerd_btn:
                clickAnswerNum = 4;
                break;
        }
        judge(clickAnswerNum,rightAnswerNum);
    }
    //判断回答是否正确
    public void judge(Integer select,Integer right){
        switch (right){
            case 1:
                answera_btn.setBackgroundColor(Color.RED);
                break;
            case 2:
                answerb_btn.setBackgroundColor(Color.RED);
                break;
            case 3:
                answerc_btn.setBackgroundColor(Color.RED);
                break;
            case 4:
                answerd_btn.setBackgroundColor(Color.RED);
                break;
        }
        if (select == right){
            rightNum += 1;
        }
        answera_btn.setClickable(false);
        answerb_btn.setClickable(false);
        answerc_btn.setClickable(false);
        answerd_btn.setClickable(false);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(totalQuestionNum <= questionIndex){
                        timer.cancel();
                        Toast.makeText(MyApplication.getContext(),"您已完成答题",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyApplication.getContext(), Main.class);
                        startActivity(intent);
                    }else {
                        queryQuestion(questionIndex);
                    }
            }
        }
    };
    //查询显示题目
    public void queryQuestion(final Integer currentindex) {
        progressDialog = new ProgressDialog(Question.this);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍后");
        progressDialog.setCancelable(true);
        progressDialog.show();
        questions.clear();
        answera_btn.setClickable(true);
        answerb_btn.setClickable(true);
        answerc_btn.setClickable(true);
        answerd_btn.setClickable(true);
        answera_btn.setBackgroundColor(Color.LTGRAY);
        answerb_btn.setBackgroundColor(Color.LTGRAY);
        answerc_btn.setBackgroundColor(Color.LTGRAY);
        answerd_btn.setBackgroundColor(Color.LTGRAY);
        BmobQuery<question> query = new BmobQuery<question>();
        query.order("index");
        query.addWhereEqualTo("testid",id);

        query.findObjects(new FindListener<question>() {
            @Override
            public void done(List<question> list, BmobException e) {
                for (question question:list){
                    questions.add(question);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        question_tv.setText(questions.get(currentindex).getQuestionlabel());
                        answera_btn.setText(questions.get(currentindex).getAnswera());
                        answerb_btn.setText(questions.get(currentindex).getAnswerb());
                        answerc_btn.setText(questions.get(currentindex).getAnswerc());
                        answerd_btn.setText(questions.get(currentindex).getAnswerd());
                        rightAnswer = questions.get(currentindex).getRightanswer();
                        switch (rightAnswer){
                            case "A":
                                rightAnswerNum = 1;
                                break;
                            case "B":
                                rightAnswerNum = 2;
                                break;
                            case "C":
                                rightAnswerNum = 3;
                                break;
                            default:
                                rightAnswerNum = 4;
                                break;
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    //查询用户已经回答的题目数，正确数
    public void queryQuestionIndex(){
        usertestscores.clear();
        BmobQuery<usertestscore> query = new BmobQuery<usertestscore>();
        BmobQuery<usertestscore> query1 = new BmobQuery<usertestscore>();
        BmobQuery<usertestscore> mainquery = new BmobQuery<usertestscore>();
        query.addWhereEqualTo("name",myUser.getUsername());
        query1.addWhereEqualTo("testid",id);
        List<BmobQuery<usertestscore>> queries = new ArrayList<BmobQuery<usertestscore>>();
        queries.add(query);
        queries.add(query1);
        mainquery.and(queries);
        mainquery.findObjects(new FindListener<usertestscore>() {
            @Override
            public void done(List<usertestscore> list, BmobException e) {
                 for(usertestscore usertestscore:list){
                     usertestscores.add(usertestscore);
                 }
                if (usertestscores.size() == 0) {
                   questionIndex = 0;
                    rightNum = 0;
                }else {
                    questionIndex = usertestscores.get(0).getAnswerednum();
                    rightNum = usertestscores.get(0).getRight();
                    usertestid = usertestscores.get(0).getObjectId();

                }
                handler.sendEmptyMessage(0);
            }
        });
    }
}
