package com.zg.model;

import java.io.File;

public class FileMeta {
    private final Integer id;
    private final String name;
    private final String path;
    //private String pinyin;
    //private String pinyinFirst;
    private final boolean directory;
    private final Long length;
    private final Long lastModifiedTimestamp;

    // 提供给扫描后使用（因为扫描出来的是一个一个文件）
    public FileMeta(File file) {
        this.id = null;
        this.name = file.getName();
        //this.pinyin = PinYinUtil.getPinYin(name);
        //this.pinyinFirst = PinYinUtil.getPinYinFirst(name);
        this.path = file.getAbsolutePath();
        this.directory = file.isDirectory();
        this.length = file.length();
        this.lastModifiedTimestamp = file.lastModified();
    }

    // 提供给DB查询后使用(数据库扫描出来是一条一条信息)
    public FileMeta(Integer id, String name, String path,boolean directory, Long length, Long lastModifiedTimestamp) {
        this.id = id;
        this.name = name;
        this.path = path;
        //this.pinyin = PinYinUtil.getPinYin(name);
        //this.pinyinFirst = PinYinUtil.getPinYinFirst(name);
        this.directory = directory;
        this.length = length;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public String getLength() {
        return String.valueOf(length);
    }

    public String getLastModified() {
        return String.valueOf(lastModifiedTimestamp);
    }
}
