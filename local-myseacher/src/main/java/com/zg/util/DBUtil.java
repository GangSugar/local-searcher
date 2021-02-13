package com.zg.util;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 这里的实现方式使用的是单例 —— 懒汉模式
 */
public class DBUtil {
    private static volatile DataSource dataSource;

    //4.
    private static String getDatabasePath(){
        try {
            String classesPath = DBUtil.class.getProtectionDomain()
                    .getCodeSource().getLocation().getFile();
            File classesDir = new File(URLDecoder.decode(classesPath, "UTF-8"));
            String path = classesDir.getParent() + File.separator + "searcher.db";
            LogUtil.log("数据库文件路径为: %s", path);
            return path;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    //2.
    public static Connection initConnection(){
        if (dataSource == null){
            synchronized ((DBUtil.class)){
                if (dataSource == null){
                    //进行初始化
                    //SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
                    //dataSource = sqLiteDataSource;
                    //将这些初始化都封装在一个方法当中
                    initDataSource();
                }
            }
        }

        // Connection 对象是线程不安全 —— 每个线程都必须有自己的 Connection 对象
        // 一个线程只有一个 Connection 对象没有问题
        // 使用 ThreadLocal 实现，每个线程有自己的 Connection 对象
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    //使用ThreadLocal，使每一个线程都有自己的connection对象
    private static final ThreadLocal<Connection> connectionThreadLocal = ThreadLocal.withInitial(DBUtil::initConnection);

    //3.
    private static void initDataSource() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite://"+getDatabasePath();

        sqLiteDataSource.setUrl(url);
        dataSource = sqLiteDataSource;
    }

    //1.
    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }
}
