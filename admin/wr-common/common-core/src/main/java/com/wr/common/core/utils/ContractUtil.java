package com.wr.common.core.utils;

import com.wr.common.core.utils.chinese.ChineseCharToEn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: SJiang
 * @Date: 2023-01-04 16:00:24
 * @Desc: 租房合同工具类
 */
public class ContractUtil {

    /**
     * 自动生成合同编码（物业首字母全拼+年、月、日、时（时精确到分）+五位随机数据）
     * @param contractNo 物业或小区名称
     * @return
     */
    public static String contractNoCreate(String contractNo){
        //物业名称转换为首字母（后台添加是物业名称，移动端添加是小区）
        String allFirstLetter = ChineseCharToEn.getAllFirstLetter(contractNo);
        //讲当前时间年、月、日、时、分 获取后，转换为字符串
        String date=null;
        try {
            Date totay = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            date = sdf.format(totay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //随机5个数据
        String str = "";
        Random rand = new Random();
        char[] letters=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                'R','S','T','U','V','W','X','Y','Z',
                '0','1','2','3','4','5','6','7','8','9'};
        int index;
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<5;i++){
            do{
                index = rand.nextInt(letters.length);
            }while(flags[index]==true);
            char c = letters[index];
            str += c;
            flags[index]=true;
        }
        return allFirstLetter+"-"+date+"-"+str;
    }
}
