package com.gta.chapter2.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by kui.lv on 2017/11/15.
 */
public final  class CastUtil {


    /**
     * 转为String型
     * @param object
     * @return
     */
    public static String castString(Object object){
        return CastUtil.casetString(object,"");
    }

    /**
     * 转为String型(提供默认值)
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String casetString(Object obj,String defaultValue){
        return obj != null ? String.valueOf(obj) :defaultValue;
    }


    /**
     * 转为double
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){
        return  CastUtil.castDouble(obj,0);
    }

    /**
     * 转为double(提供默认值)
     * @param obj
     * @return
     */
    public  static  double castDouble(Object obj,double defaultValue ){
        double doubleValue = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                   doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为int型
     * @param object
     * @return
     */
    public static int castInt(Object object){
        return CastUtil.castInt(object,0);
    }

    /**
     * 转为int型(提供默认值)
     * @param object
     * @param defaultValues
     * @return
     */
    public  static  int castInt(Object object,int defaultValues){
        int intValue = defaultValues;
        if(object != null){
            String strValue = castString(object);
            if(StringUtils.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValues;
                }
            }
        }
        return intValue;

    }

    public static boolean castBoolean(Object obj){
        return  CastUtil.castBoolean(obj,false);
    }

    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booleanValue  = defaultValue;
        if(obj != null){
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
