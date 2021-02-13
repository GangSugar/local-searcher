package com.zg.demo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class GetClassPath {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String classesPath = GetClassPath.class.getProtectionDomain()
                .getCodeSource().getLocation().getFile();//拿到路径
        System.out.println(classesPath);//拿到类所在文件跟路径
        String decode = URLDecoder.decode(classesPath, "UTF-8");//文件解码
        System.out.println(decode);
        File classesDir = new File(decode);//文件放在哪里，数据库就放在哪里
        System.out.println(classesDir.getAbsoluteFile());
    }
}
