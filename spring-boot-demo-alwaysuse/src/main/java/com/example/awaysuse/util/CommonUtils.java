package com.example.awaysuse.util;

import java.math.BigDecimal;

/**
 * Created by clt on 2020/3/12.
 * 数据的基本算法
 */
public class CommonUtils {
    public static Boolean isNull(Object str) {
        if(null==str||str.toString().trim().equals("")||str.toString().trim().equals("null")){
            return  true;
        }
        return false;
    }
    public static Double calcRate(Integer coActualNum, Integer coTdNum) {
        if (coActualNum != null && coTdNum != null && coTdNum != 0 ) {
            return 1.0 * coActualNum / coTdNum;
        } else if (coTdNum == null || coTdNum == 0) {
            return 0.0;
        } else {
            return 0.0;
        }
    }

    public static String isNullOrStr(Object c_siteopf_id) {
        if (c_siteopf_id==null){
            return "";
        }
        return c_siteopf_id.toString().trim();
    }
    public static Integer isNullOrInter(Object c_siteopf_id) {
        if (c_siteopf_id==null){
            return 0;
        }
        return Integer.valueOf(c_siteopf_id.toString().trim());
    }

    public static boolean isEquals(String c_snet_id,String s) {
        if(c_snet_id==null){
            return false;
        }
        if (c_snet_id.equals(s)){
            return true;
        }else {
            return false;
        }
    }
    public static double add(double v1, double v2) {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
}
