package com.example.whitebooks.lianxin_android.utilclass;

import cn.bmob.v3.BmobObject;

/**
 * Created by whitebooks on 17/5/15.
 */

public class question extends BmobObject {
    private Integer testid;
    private String rightanswer;
    private String questionlabel;
    private Integer index;
    private String answerd;
    private String answerc;
    private String answerb;
    private String answera;
    public question(){

    }

    public Integer getTestid() {
        return testid;
    }

    public String getRightanswer() {
        return rightanswer;
    }

    public String getQuestionlabel() {
        return questionlabel;
    }

    public Integer getIndex() {
        return index;
    }

    public String getAnswerd() {
        return answerd;
    }

    public String getAnswerc() {
        return answerc;
    }

    public String getAnswerb() {
        return answerb;
    }

    public String getAnswera() {
        return answera;
    }

    public void setTestid(Integer testid) {
        this.testid = testid;
    }

    public void setAnswera(String answera) {
        this.answera = answera;
    }

    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setQuestionlabel(String questionlabel) {
        this.questionlabel = questionlabel;
    }

    public void setRightanswer(String rightanswer) {
        this.rightanswer = rightanswer;
    }
}
