package com.zg.dao;

import com.zg.util.DBUtil;
import com.zg.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteDAO {
    //1.数据库的删除操作
    public void delete(List<Integer> idList) throws SQLException {
        Connection c = DBUtil.getConnection();//因为利用了ThreadLocal实现了一个线程都有自己的connection对象，因此不需要关闭
            // JDK8 中支持的 stream 用法，把id映射成一个？占位符
            List<String> placeholderList = idList.stream()
                    .map(id -> "?")//采用stream流，因为idList是一个集合，但是下面根据id删除的时候，要把一个id看作一个占位符？
                    .collect(Collectors.toList());
//        List<String> placeholderList = new ArrayList<>();
//        for (Integer id : idList) {
//            placeholderList.add("?");
//        }

            String placeHolder = String.join(", ", placeholderList);//join就是用，将这些占位符连接起来

            String sql = String.format("DELETE FROM file_meta WHERE id IN (%s)", placeHolder);//format，就是类似于printf，可以用后面的变量替换掉前面的%s

            try(PreparedStatement s = c.prepareStatement(sql)){
                //替换占位符
                for (int i = 0;i < idList.size();i++){
                    int id = idList.get(i);
                    s.setInt(i+1,id);
                }
                //打印一下日志
                LogUtil.log("执行SQL语句：%s",s.toString());
                s.executeUpdate();
            }


    }

    public static void main(String[] args) throws SQLException {
        List<Integer> idList = Arrays.asList(1,2,3);
        DeleteDAO dao = new DeleteDAO();

        dao.delete(idList);

    }
}
