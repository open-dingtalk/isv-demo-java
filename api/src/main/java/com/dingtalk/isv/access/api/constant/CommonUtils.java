package com.dingtalk.isv.access.api.constant;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/19.
 */
public class CommonUtils {

    public static String stringList2String(List<String> list, String splitter){
        String result = "";
        if(CollectionUtils.isEmpty(list)){
            return result;
        }

        for(Object s:list){
            result = result.toString()+s+splitter;
        }

        return result.substring(0,result.length()-splitter.length());
    }

    public static String integerList2String(List<Integer> list,String splitter){
        String result = "";
        if(CollectionUtils.isEmpty(list)){
            return result;
        }

        for(Integer s:list){
            result = result+s+splitter;
        }
        return result.substring(0,result.length()-splitter.length());
    }

    public static String longList2String(List<Long> list,String splitter){
        String result = "";
        if(CollectionUtils.isEmpty(list)){
            return result;
        }

        for(Long s:list){
            result = result+s+splitter;
        }
        return result.substring(0,result.length()-splitter.length());
    }

    public static void main(String []args){
        String s = stringList2String(Arrays.asList("ss", "sss"), ",");
        System.err.print(s);
    }
}
