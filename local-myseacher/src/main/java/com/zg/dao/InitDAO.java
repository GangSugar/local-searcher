package com.zg.dao;

import com.zg.util.DBUtil;
import com.zg.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

public class InitDAO {
    //找到init.sql文件，并且读取内容，得到SQL语句
    //inti.sql是以分号作为一组语句

    //1.得到SQL语句(建表语句)
    private String[] getSQLs() {
        try (InputStream is = InitDAO.class.getClassLoader().getResourceAsStream("init.sql")) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(is, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line);
            }

            return sb.toString().split(";");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        try {
            Connection c = DBUtil.getConnection();

            String[] sqls = getSQLs();
            for (String sql : sqls) {
                LogUtil.log("执行 SQL: " + sql);
                try (PreparedStatement s = c.prepareStatement(sql)) {
                    if (sql.toUpperCase().startsWith("SELECT ")) {
                        try (ResultSet resultSet = s.executeQuery()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            int rowCount = 0;
                            while (resultSet.next()) {
                                StringBuilder message = new StringBuilder("|");
                                for (int i = 1; i <= columnCount; i++) {
                                    String value = resultSet.getString(i);
                                    message.append(value).append(" | ");
                                }
                                LogUtil.log(message.toString());
                                rowCount++;
                            }
                            LogUtil.log("一共查询出 %d 行", rowCount);
                        }
                    } else {
                        int i = s.executeUpdate();
                        LogUtil.log("收到影响的一共有 %d 行", i);

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        InitDAO initDAO = new InitDAO();
        initDAO.init();
    }
}
