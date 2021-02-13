package com.zg.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    // 类型... 可变参数列表
    // Object... 代表，任意类型，任意长度都可以传入
    public static void log(String format, Object... args){
        //System.out.println(Arrays.toString(args));

        String message = String.format(format,args);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String now  = dateFormat.format(date);
        System.out.printf("%s: %s\n",now,message);
    }

    public static void main(String[] args) {
        log("%d,%s",12,"hello");//这是三个参数"%d,%s"是一个参数，后面的俩个都是传递给了Object，因为是任意长度和任意类型的
        log("123",1234);
        log("hello word");
    }
}
