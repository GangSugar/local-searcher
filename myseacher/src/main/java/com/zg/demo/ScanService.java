package com.zg.demo;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScanService {
    private static DataSource dataSource;
    static {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();

        try{
            String classesPath = GetClassPath.class.getProtectionDomain()
                    .getCodeSource().getLocation().getFile();//拿到路径
            String decode = URLDecoder.decode(classesPath, "UTF-8");//文件解码
            File classesDir = new File(decode);//文件放在哪里，数据库就放在哪里
            String dbPath = classesDir.getParent() + "/test.db";//getParent()拿到它父亲的路径

            sqLiteDataSource.setUrl("jdbc:sqlite://"+dbPath);
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        dataSource = sqLiteDataSource;
    }

    public static void main(String[] args) throws SQLException{
        createTable();//创建表
        
        //上面创建好表之后，就是文件扫描
        scan();
    }


    private static List<File> scanResult = new ArrayList<>();//扫描完之后，是一堆文件名的集合
    private static List<File> queryResult = new ArrayList<>();//这个是从数据库中查询出来的结果
    //1.文件扫描
    private static void scan() throws SQLException {
        File root = new File("G:\\判断日期");
        scanResult.clear();//x先提前将里面的东西清理一下，这句话可有可无
        scanDir(root);//开始扫描
        System.out.println(scanResult);//打印一下扫描到的文件集合

        //2.数据库扫描
        System.out.println("数据库中扫描出来的：");
        queryResult.clear();
        queryDir(root);
        System.out.println(root);

        /**
         * 3.扫描完成之后进行的就是关于统一数据库和文件夹扫描出来的数据
         * 首先将scanResult-queryResult的结果添加到数据库当中
         * 将queryResult-scanResult的结果从数据库中删除掉
         */


    }

    //数据库中扫描文件
    private static void queryDir(File root) throws SQLException {
        String sql = "select name,path from file_meta where path like ?";
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement s = connection.prepareStatement(sql)) {
                s.setString(1,root.getAbsolutePath()+"%");

               try(ResultSet rs = s.executeQuery()){
                   while (rs.next()){
                       String name = rs.getString(1);
                       String path = rs.getString(2);

                       //拼接成一个文件
                       File file = new File(path);
                       queryResult.add(file);
                   }
               }
            }
        }
    }

    //文件夹中扫描文件
    private static void scanDir(File file) {
        scanResult.add(file);

        if (!file.isDirectory()){//如果不是文件夹，意思也就是普通文件,普通文件下面肯定没有孩子节点，所以就不需要在向下遍历了
            return;
        }

        File[] files = file.listFiles();
        if (files == null){
            return;
        }
        for (File child : files){
            scanDir(child);//这样一直遍历下去，得到的就是文件夹
        }
    }


    //创建表的
    public static void createTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS file_meta (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(50) NOT NULL,\n" +
                "    path VARCHAR(1000) NOT NULL,\n" +
                "    is_directory BOOLEAN NOT NULL,\n" +
                "    pinyin VARCHAR(50) NOT NULL,\n" +
                "    pinyin_first VARCHAR(50) NOT NULL,\n" +
                "    size BIGINT NOT NULL,\n" +
                "    last_modified TIMESTAMP NOT NULL\n" +
                ");";

        try(Connection c = dataSource.getConnection()){
            try(PreparedStatement s = c.prepareStatement(sql)) {
                s.executeUpdate();
            }
        }
    }
}

