package com.liuheonline.la.utils;


import android.util.Log;

import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.entity.LotteryOtherDetailsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: aini
 * @date 2018/7/30 11:08
 * @description 计算注数
 */
public class NumberUtil {

    private  List<BetNumberEntity> tmpArr;

    private  List<LotteryOtherDetailsEntity> allArr;

    public NumberUtil(){
        tmpArr = new ArrayList<>();
        allArr = new ArrayList<>();
    }

    public  List<LotteryOtherDetailsEntity> combine(int index, int k, List<BetNumberEntity> arr, int money) {
        if(k == 1){
            for (int i = index; i < arr.size(); i++) {
                tmpArr.add(arr.get(i));
                StringBuffer stringBuffer = new StringBuffer();
                for(int j = 0; j < tmpArr.size(); j++){
                    stringBuffer.append(tmpArr.get(j).getCode());
                    if(j != tmpArr.size()-1){
                        stringBuffer.append(",");
                    }
                }
                LotteryOtherDetailsEntity lotteryOtherDetailsEntity1 = new LotteryOtherDetailsEntity();
                lotteryOtherDetailsEntity1.setLottery_price(money);
                lotteryOtherDetailsEntity1.setOdds(arr.get(i).getPrice());
                lotteryOtherDetailsEntity1.setLottery_id(arr.get(i).getId());
                lotteryOtherDetailsEntity1.setLottery_child_name(arr.get(i).getAttr());
                lotteryOtherDetailsEntity1.setLottery_name(stringBuffer.toString());
                allArr.add(lotteryOtherDetailsEntity1);
                tmpArr.remove(arr.get(i));
            }
        }else if(k > 1){
            for (int i = index; i <= arr.size() - k; i++) {
                tmpArr.add(arr.get(i));
                combine(i + 1,k - 1, arr, money);
                tmpArr.remove(arr.get(i));
            }
        }

        return allArr;
    }

}
