package com.liuheonline.la.utils;

import com.ysyy.aini.palmarliuhe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CashTypeUtil {
    public static String getAgent(int i) {
        switch (i) {
            case 1:
                return "成员";
            case 2:
                return "代理";
            case 3:
                return "总代理";
        }
        return "";
    }

    public static String getTopupDes(String name) {
        switch (name) {
            case "0":
                return "审核中";
            case "1":
                return "充值成功";
            case "2":
                return "充值失败";
        }
        return "";
    }

    public static String getWithdrawDes(String name) {
        switch (name) {
            case "0":
                return "审核中";
            case "1":
                return "提现成功";
            case "2":
                return "已被驳回";
            case "3":
                return "已被拒绝";
        }
        return "";
    }

    public static String getName(String name) {
        switch (name) {
            case "order_pay":
                return "下单支付预存款";
            case "order_freeze":
                return "下单冻结预存款";
            case "order_cancel":
                return "取消订单解冻预存款";
            case "order_comb_pay":
                return "下单支付被冻结的预存款";
            case "recharge":
                return "充值";
            case "cash_pay":
                return "提现成功";
            case "cash_apply":
                return "申请提现冻结预存款";
            case "cash_del":
                return "取消提现申请";
            case "refund":
                return "退款";
            case "order_invite":
                return "佣金";
            case "order_lucky":
                return "抽奖奖金";
            case "sys_add_money":
                return "系统增加金额";
            case "1":
                return "在线支付";
            case "2":
                return "微信支付";
            case "3":
                return "支付宝支付";
            case "member_reg":
                return "会员注册奖励";
            case "member_login":
                return "会员登录奖励";
            case "member_promote_reg":
                return "会员通过代理推广注册";
            case "member_promote":
                return "代理推广注册";
            case "lottery_earnings":
                return "代理返点";
            case "member_sign":
                return "会员签到增加";
            case "lottery_winning":
                return "投注中奖";
            case "lottery_rebates":
                return "投注返点";
        }
        return "";
    }

    public static int getTopupImg(String str) {
        switch (str) {
            case "0":
                return R.mipmap.shenhezhong;
            case "1":
                return R.mipmap.chenggong;
            case "2":
                return R.mipmap.quxiao;
        }
        return 0;
    }

    public static int getWithdrawImg(String str) {
        switch (str) {
            case "0":
                return R.mipmap.shenhezhong;
            case "1":
                return R.mipmap.chenggong;
            case "2":
                return R.mipmap.bohui;
            case "3":
                return R.mipmap.bohui;
        }
        return 0;
    }

    public static int getImg(String str) {
        switch (str) {
            case "recharge":
                return R.mipmap.chongzhi;
            case "order_pay":
                return R.mipmap.zhichu;
            case "order_freeze":
                return R.mipmap.zhichu;
            case "order_cancel":
                return R.mipmap.zhichu;
            case "order_comb_pay":
                return R.mipmap.zhichu;
            case "cash_apply":
                return R.mipmap.tixian;
            case "cash_pay":
                return R.mipmap.tixian;
            case "cash_del":
                return R.mipmap.tixian;
            case "refund":
                return R.mipmap.tixian;
            case "order_invite":
                return R.mipmap.tixian;
            case "order_lucky":
                return R.mipmap.tixian;
            default:
                return R.mipmap.tixian;

        }
    }

    public static String getTime(Long timestep) {

        String format = "HH:mm:ss";
        String temptime = DateUtil.timeStamp2Date(timestep + "", null);
        try {
            if (IsToday(temptime)) {
                return "今天 " + DateUtil.timeStamp2Date(timestep + "", format);
            } else if (IsYesterday(temptime)) {
                return "昨天 " + DateUtil.timeStamp2Date(timestep + "", format);
            } else {
                return DateUtil.timeStamp2Date(timestep + "", null).substring(5);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }


    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();
}
