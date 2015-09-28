package com.juhuifu.quartz.utils;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil {
    private static final int LENGTH_OF_RANDOMPASSWORD = 8;

    /**
     * 输入的任意字符串是否在给定的字符串中
     * 
     * @param input
     *            指定的字符串
     * @param ss
     *            包含的字符串
     * @return boolean boolean
     */
    public static boolean containsAny(String input, String... ss) {
        for (String s : ss) {
            if (StringUtils.containsAny(input, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串数组转换为长整型数组
     * 
     * @param strings
     *            字符串数组
     * @return 长整型数组
     */
    public static long[] convert(String[] strings) {
        if (strings == null) {
            return null;
        }
        long[] array = new long[strings.length];
        int get = 0;
        if (strings != null) {
            for (int i = 0, length = strings.length; i < length; i++) {
                String s = strings[i];
                if (StringUtils.isNumeric(s)) { // !!!过滤非纯数字导致数组长度改变
                    array[get++] = NumberUtils.toLong(s);
                }
            }
        }
        return Arrays.copyOf(array, get);
    }

    /**
     * 随机生成8位密码，包括字母和数字
     * 
     * @return 随机生成的密码
     */
    public static String randomPassword() {
        return RandomStringUtils.randomAlphanumeric(LENGTH_OF_RANDOMPASSWORD);
    }

    /**
     * 将object转换成String
     * 
     * @param object
     *            输入参数
     * @return String
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static String objectToString(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * String的空值处理
     * 
     * @param str
     *            参数字符串
     * @param defaultValue
     *            默认值
     * @return String 返回字符串结果
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static String nvl(String str, String defaultValue) {
        return isNvl(str) ? defaultValue : str;
    }

    /**
     * isNvl(是否为空)
     * 
     * @param cs
     *            输入字符串
     * @return Boolean
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static boolean isNvl(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
      * isNumber(判断是否是数字)
      *
      * @Title: isNumber
      * @param str 字符串
      * @return boolean    返回类型
      */
    public static boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    /**
     * toUpperCaseFirstOne(首字母转换成大写)
      * @Title: isNumber
      * @param str 字符串
      * @return string 
     */
    public static String toUpperCaseFirstOne(String str){
        if(Character.isUpperCase(str.charAt(0))){
            return str;
        }else{
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0)))
                    .append(str.substring(1)).toString();
        }
    }
    
    /**
      * replaceCardNo(用以模糊银行卡号)
      *
      * @Title: replaceCardNo
      * @param str 正常卡号
      * @param head 前几位正常显示
      * @param foot 后几位正常显示
      * @return
      * @return String    返回类型
      * @throws
      */
    public static String replaceCardNo(String str,int head,int foot){
        char[] arrays = str.trim().toCharArray();
        StringBuilder sb = new StringBuilder();   
        int length = arrays.length;
        for(int i=0;i<length;i++){
            char temp = arrays[i];
            if(i>head-1 && i<length-foot){
                temp = '*';
            }
            sb.append(temp);
        }
        return sb.toString();
    }
}
