package com.zg.service;

import com.zg.model.FileMeta;

import java.util.List;

/**
 * 这个类主要是提供给 “用户” 在输入框中输入关键字之后，在数据库中进行查询操作；
 */
public class DBService {
    //1.模糊查询，根据文件名，进行查询(查询出来的是一个集合，因为是模糊查询)
    public List<FileMeta> queryByName(String name){
        return null;
    }
}
