package com.zg.service;

import com.zg.dao.InitDAO;

/**
 * 这个类主要是提供给 “用户” 创建数据库用的业务逻辑层；
 */
public class DBService {
    private final InitDAO initDAO = new InitDAO();

    public void init() {
        initDAO.init();
    }
}
