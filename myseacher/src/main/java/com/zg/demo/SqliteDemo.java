package com.zg.demo;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteDemo {
    public static void main(String[] args) throws SQLException {
        //1.创建DataSource
        //2.获取Connection
        //3.操作命令集

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite://D:/IDEAMysql/xiangmu1/test.db");//这里的url是随意的，主要是要有这个test.db文件

        DataSource dataSource = sqLiteDataSource;

        Connection connection = dataSource.getConnection();

        PreparedStatement s = connection.prepareStatement("CREATE TABLE file_meta (id int)");
        s.executeUpdate();
//        PreparedStatement show_tables = connection.prepareStatement("INSERT INTO file_meta (id) VALUES (1), (2)");
//        show_tables.executeUpdate();
//
//        PreparedStatement s = connection.prepareStatement("SELECT* FROM file_meta");
//        ResultSet resultSet = s.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(1));
//        }

        connection.close();
    }
}
