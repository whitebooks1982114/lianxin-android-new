package com.example.whitebooks.lianxin_android.utilclass;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by whitebooks on 17/2/8.
 */

public class MyUser extends BmobUser implements Serializable {
    private static final long serialVersionUID = 6465198351058235015L;
    //签到次数
    private Integer signtimes;
    //积分
    private Integer score;
    //是否是党员
    private Boolean party;
    //闯关数
    private Integer examscore;
    private Integer partyscore;
    private Boolean isadmin;
    private String department;
    //连续签到天数
    private Integer contsigntimes;
    private String chinesename;
    //可兑换积分
    private Integer exscore;




    public MyUser() {
        super();
    }

    public Integer getExscore() {
        return exscore;
    }

    public void setExscore(Integer exscore) {
        this.exscore = exscore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getParty() {
        return party;
    }

    public void setParty(Boolean party) {
        this.party = party;
    }

    public Boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getSigntimes() {
        return signtimes;
    }

    public void setSigntimes(Integer signtimes) {
        this.signtimes = signtimes;
    }

    public Integer getExamscore() {
        return examscore;
    }

    public void setExamscore(Integer examscore) {
        this.examscore = examscore;
    }

    public Integer getPartyscore() {
        return partyscore;
    }

    public void setPartyscore(Integer partyscore) {
        this.partyscore = partyscore;
    }

    public Integer getContsigntimes() {
        return contsigntimes;
    }

    public void setContsigntimes(Integer contsigntimes) {
        this.contsigntimes = contsigntimes;
    }



}
