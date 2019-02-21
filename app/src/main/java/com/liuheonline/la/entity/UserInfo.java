package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * @author: aini
 * @date 2018/7/13 16:48
 * @description 用户信息实体类
 */
public class UserInfo implements Serializable{


    /**
     * id : 11095
     * username :
     * password : 69bf4d2b1e6d34266dc4e8e0a784924d
     * encrypt : 826572
     * nickname : 冰糖雪梨
     * head : img/20180728/5b5c44a799f1e.jpg
     * background : img/20180802/5b62ad2479bab.jpg
     * sex : 0
     * email :
     * mobile : 13648019314
     * signature : null
     * birthday : null
     * regdate : 1532161774
     * lastdate : 1533267432
     * regip : 222.212.185.133
     * lastip : 118.116.127.97
     * loginnum : 25
     * groupid : 1
     * areaid : 0
     * address : null
     * available_predeposit : 29482.00
     * freeze_predeposit : 100.00
     * point : 0
     * zc_moshi : 0
     * agent : 3
     * invite_one : 0
     * invite_two : 0
     * invite_three : 0
     * inviter_id : 0
     * high_frequency : 0
     * low_frequency : 0
     * message : 0
     * islock : 0
     * vip : 0
     * overduedate : 0
     * openid :
     * log_at : 2
     * token : E5F250AC-8D7D-B2F3-6B46-E265183CCB20
     * apptoken : F6E127C7-AE15-E6AF-D0F1-AD2A2E6A20C1
     * waptoken : null
     * spending : 197
     * income : 21307
     * head_link : http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg
     * background_link : http://120.79.189.244/images/img/20180802/5b62ad2479bab.jpg
     */

    private int id;
    private String username;
    private String password;
    private String encrypt;
    private String nickname;
    private String head;
    private String background;
    private int sex;
    private String email;
    private String mobile;
    private Object signature;
    private Object birthday;
    private int regdate;
    private int lastdate;
    private String regip;
    private String lastip;
    private int loginnum;
    private int groupid;
    private int areaid;
    private Object address;
    private String available_predeposit;
    private String freeze_predeposit;
    private int point;
    private Double zc_moshi;

    public int getRebates() {
        return rebates;
    }

    public void setRebates(int rebates) {
        this.rebates = rebates;
    }


    public String getNodd_rebates() {
        return nodd_rebates;
    }

    public void setNodd_rebates(String nodd_rebates) {
        this.nodd_rebates = nodd_rebates;
    }

    private String nodd_rebates;

    private int rebates;
    private int agent;
    private int invite_one;
    private int invite_two;
    private int invite_three;
    private int inviter_id;
    private int high_frequency;
    private int low_frequency;
    private int message;
    private int islock;
    private int vip;
    private int overduedate;
    private String openid;
    private int log_at;
    private String token;
    private String apptoken;
    private Object waptoken;
    private double spending;
    private double income;
    private String head_link;
    private String background_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) {
        this.signature = signature;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public int getRegdate() {
        return regdate;
    }

    public void setRegdate(int regdate) {
        this.regdate = regdate;
    }

    public int getLastdate() {
        return lastdate;
    }

    public void setLastdate(int lastdate) {
        this.lastdate = lastdate;
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public int getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(int loginnum) {
        this.loginnum = loginnum;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(String available_predeposit) {
        this.available_predeposit = available_predeposit;
    }

    public String getFreeze_predeposit() {
        return freeze_predeposit;
    }

    public void setFreeze_predeposit(String freeze_predeposit) {
        this.freeze_predeposit = freeze_predeposit;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Double getZc_moshi() {
        return zc_moshi;
    }

    public void setZc_moshi(Double zc_moshi) {
        this.zc_moshi = zc_moshi;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public int getInvite_one() {
        return invite_one;
    }

    public void setInvite_one(int invite_one) {
        this.invite_one = invite_one;
    }

    public int getInvite_two() {
        return invite_two;
    }

    public void setInvite_two(int invite_two) {
        this.invite_two = invite_two;
    }

    public int getInvite_three() {
        return invite_three;
    }

    public void setInvite_three(int invite_three) {
        this.invite_three = invite_three;
    }

    public int getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(int inviter_id) {
        this.inviter_id = inviter_id;
    }

    public int getHigh_frequency() {
        return high_frequency;
    }

    public void setHigh_frequency(int high_frequency) {
        this.high_frequency = high_frequency;
    }

    public int getLow_frequency() {
        return low_frequency;
    }

    public void setLow_frequency(int low_frequency) {
        this.low_frequency = low_frequency;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getIslock() {
        return islock;
    }

    public void setIslock(int islock) {
        this.islock = islock;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getOverduedate() {
        return overduedate;
    }

    public void setOverduedate(int overduedate) {
        this.overduedate = overduedate;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getLog_at() {
        return log_at;
    }

    public void setLog_at(int log_at) {
        this.log_at = log_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public Object getWaptoken() {
        return waptoken;
    }

    public void setWaptoken(Object waptoken) {
        this.waptoken = waptoken;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }

    public String getBackground_link() {
        return background_link;
    }

    public void setBackground_link(String background_link) {
        this.background_link = background_link;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head='" + head + '\'' +
                ", background='" + background + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", signature=" + signature +
                ", birthday=" + birthday +
                ", regdate=" + regdate +
                ", lastdate=" + lastdate +
                ", regip='" + regip + '\'' +
                ", lastip='" + lastip + '\'' +
                ", loginnum=" + loginnum +
                ", groupid=" + groupid +
                ", areaid=" + areaid +
                ", address=" + address +
                ", available_predeposit='" + available_predeposit + '\'' +
                ", freeze_predeposit='" + freeze_predeposit + '\'' +
                ", point=" + point +
                ", zc_moshi=" + zc_moshi +
                ", nodd_rebates='" + nodd_rebates + '\'' +
                ", rebates=" + rebates +
                ", agent=" + agent +
                ", invite_one=" + invite_one +
                ", invite_two=" + invite_two +
                ", invite_three=" + invite_three +
                ", inviter_id=" + inviter_id +
                ", high_frequency=" + high_frequency +
                ", low_frequency=" + low_frequency +
                ", message=" + message +
                ", islock=" + islock +
                ", vip=" + vip +
                ", overduedate=" + overduedate +
                ", openid='" + openid + '\'' +
                ", log_at=" + log_at +
                ", token='" + token + '\'' +
                ", apptoken='" + apptoken + '\'' +
                ", waptoken=" + waptoken +
                ", spending=" + spending +
                ", income=" + income +
                ", head_link='" + head_link + '\'' +
                ", background_link='" + background_link + '\'' +
                '}';
    }
}
