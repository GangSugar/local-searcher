package com.zg.dao;

import com.zg.model.FileMeta;
import com.zg.util.DBUtil;
import com.zg.util.LogUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 这个类是用来保存数据的，将扫描出来的数据文件保存到数据库当中(是一个文件；列表)
 */
public class SaveDAO {
    //1.
    public void save(List<FileMeta> fileList){
        try{
            //1.拿到数据库连接
            Connection c = DBUtil.getConnection();

            String sql = "INSERT INTO file_meta " +
                    "(name, path, is_directory, pinyin, pinyin_first, size, last_modified) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement s = c.prepareStatement(sql)){
                //插入的时候可以一个一个文件插入OR批量插入，这里采用for循环一个一个插入进去
                //自己选择原因，因为暂时插入语句不会成千上万条，这样俩者性能就差别不是很大，一个一个插入代码写起来方便，看起来也简洁明了
                for (FileMeta file : fileList){
                    s.setString(1,file.getName());
                    s.setString(2,file.getPath());
                    s.setBoolean(3,file.isDirectory());
                    s.setString(4,file.getPinyin());
                    s.setString(5,file.getPinyinFirst());
                    s.setLong(6,file.getLength());
                    s.setLong(7,file.getLastModifiedTimestamp());

                    int i = s.executeUpdate();//插入之后返回的结果
                    LogUtil.log("执行 SQL: %s, %s, 收到影响的行数是: %d", sql, file, i);//打印日志
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SaveDAO saveDAO = new SaveDAO();
        List<FileMeta> fileList = Arrays.asList(
                new FileMeta(new File("G:\\电路\\板书1.txt")),
                new FileMeta(new File("G:\\电路\\板书2.txt")),
                new FileMeta(new File("G:\\电路\\板书3.txt"))
        );
        saveDAO.save(fileList);
    }
}
