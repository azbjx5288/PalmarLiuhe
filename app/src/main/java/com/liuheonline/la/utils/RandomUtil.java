package com.liuheonline.la.utils;

public class RandomUtil {

    public static int[] getRandomNumber(int maxnum,int lenth){
        int[] randomArray = new int[lenth];
        for(int i=0;i<randomArray.length;i++){
//产生1~lenth之间的随机整数
            int num = (int)(Math.random() * maxnum+1);
//如果数组中已经有该随机数num,则不对其位置进行赋值，让其停留在该位置继续产生随机数，直到产生不相同的数为止
            if(isContain(randomArray,num)){
                i--;
            }else{
                randomArray[i] = num;
            }
        }
        return randomArray;
    }

    /**
     * 判断一个数组中是否存在某一个指定的整数
     * @param array
     * @param num
     * @return
     */
    public static boolean isContain(int[] array,int num){
        boolean flag = false;
        for(int i=0;i<array.length;i++){
            if(array[i] == num){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
