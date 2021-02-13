package com.zg.dao;

import com.zg.model.FileMeta;
import com.zg.util.DBUtil;
import com.zg.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAO {
    public List<FileMeta> query(String keyword) {
        try {
            String sql = "SELECT id,name,path,is_directory,size,last_modified FROM file_meta WHERE name LIKE ? OR pinyin LIKE ? OR pinyin_first LIKE ?";

            Connection c = DBUtil.getConnection();

            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, "%" + keyword + "%");
                s.setString(2, "%" + keyword + "%");
                s.setString(3, "%" + keyword + "%");

                LogUtil.log("执行 SQL: %s, %s", sql, keyword);

                List<FileMeta> result = new ArrayList<>();
                try (ResultSet resultSet = s.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        String path = resultSet.getString(3);
                        boolean directory = resultSet.getBoolean(4);
                        long length = resultSet.getLong(5);
                        long lastModified = resultSet.getLong(6);

                        FileMeta fileMeta = new FileMeta(id, name, path, directory, length, lastModified);
                        result.add(fileMeta);
                    }

                    LogUtil.log("一共查询出 %d 个文件", result.size());

                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        QueryDAO queryDAO = new QueryDAO();
        System.out.println(queryDAO.query("ll"));
    }
}

