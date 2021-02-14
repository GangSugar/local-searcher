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

/**
 * 这个类的目的：
 *   1.根据关键字进行查询文件，主要有根据文件名查询，根据拼音查询，根据首字母查询
 */
public class QueryDAO {
    public List<FileMeta> query(String keyword) {
        try {
            String sql = "SELECT id, name,pinyin,pinyin_first, path, is_directory, size, last_modified FROM file_meta WHERE name LIKE ? OR pinyin LIKE ? OR pinyin_first LIKE ?";

            Connection c = DBUtil.getConnection();

            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, "%" + keyword + "%");
                s.setString(2, "%" + keyword + "%");
                s.setString(3, "%" + keyword + "%");

                LogUtil.log("执行 SQL: %s, %s", sql, keyword);

                List<FileMeta> result = new ArrayList<>();//用来存放查出来的结果，因为查询出来不可能是一个，所以用顺序表存放
                try (ResultSet resultSet = s.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String pinyin = resultSet.getString("pinyin");
                        String pinyinFirst = resultSet.getString("pinyin_first");
                        String path = resultSet.getString("path");
                        boolean directory = resultSet.getBoolean("is_directory");
                        long length = resultSet.getLong("size");
                        long lastModified = resultSet.getLong("last_modified");

                        FileMeta fileMeta = new FileMeta(id, name,pinyin,pinyinFirst, path, directory, length, lastModified);//设置到模型类里面
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

    /**
     * 这个是根据路径去查询
     * @param searchPath
     * @return
     */
    public List<FileMeta> queryByPath(String searchPath) {
        try {
            String sql = "SELECT id, name,pinyin,pinyin_first, path, is_directory, size, last_modified FROM file_meta WHERE path LIKE ?";

            Connection c = DBUtil.getConnection();

            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, searchPath + "%");

                LogUtil.log("执行 SQL: %s, %s", sql, searchPath);

                List<FileMeta> result = new ArrayList<>();//用来存放查出来的结果，因为查询出来不可能是一个，所以用顺序表存放
                try (ResultSet resultSet = s.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String pinyin = resultSet.getString("pinyin");
                        String pinyinFirst = resultSet.getString("pinyin_first");
                        String path = resultSet.getString("path");
                        boolean directory = resultSet.getBoolean("is_directory");
                        long length = resultSet.getLong("size");
                        long lastModified = resultSet.getLong("last_modified");

                        FileMeta fileMeta = new FileMeta(id, name,pinyin,pinyinFirst, path, directory, length, lastModified);//设置到模型类里面
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
        List<FileMeta> fileList = queryDAO.query("banshu");
        for (FileMeta file : fileList) {
            System.out.println(file);
        }
    }
}
