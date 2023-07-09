package com.github.jabadurai.tinylink.utils;

public class StringUtils {

    public static boolean isEmpty(String str){
        if(str == null || str == ""){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
