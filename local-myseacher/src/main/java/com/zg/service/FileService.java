package com.zg.service;

import com.zg.dao.DeleteDAO;
import com.zg.dao.QueryDAO;
import com.zg.dao.SaveDAO;
import com.zg.model.FileMeta;
import com.zg.util.ListUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 这个类是用来扫描文件之后，对比扫描结果和数据库中的结果进行差值处理的
 */
public class FileService {
    private SaveDAO saveDAO = new SaveDAO();
    private DeleteDAO deleteDAO = new DeleteDAO();
    private QueryDAO queryDAO = new QueryDAO();

    //1.添加(应该添加到数据中的文件列表)
    public void saveFileList(List<FileMeta> fileList) {
        saveDAO.save(fileList);
    }

    //2.删除(从数据库中应该删除掉的文件列表)
    public void deleteFileList(List<FileMeta> fileList) {
        //1.因为是根据id做删除操作，所以应该先拿到id
        List<Integer> idList = fileList.stream().map(FileMeta::getId).collect(Collectors.toList());
        deleteDAO.delete(idList);
    }

    /**
     * 用来整合文件夹扫描出来的文件和数据库扫描出来的文件的
     *
     * @param scanResultList 这是扫描文件夹扫描出来的文件列表
     */
    //3.综合处理的
    /*public void service(List<FileMeta> queryResultList,List<FileMeta> scanResultList){
        //1.queryResultList - scanResultList
        List<FileMeta> ds1 = ListUtil.differenceSet(queryResultList,scanResultList);
        //2.对于ds1应该从数据库中删除掉
        deleteFileList(ds1);

        //3.scanResultList - scanResultList
        List<FileMeta> ds2 = ListUtil.differenceSet(scanResultList,queryResultList);
        //4.对于ds2应该添加到数据库中
        saveFileList(ds2);
    }*/
    public void service(String path, List<FileMeta> scanResultList) {
        List<FileMeta> queryResultList = queryDAO.queryByPath(path);

        // 1. queryResultList - scanResultList
        List<FileMeta> ds1 = ListUtil.differenceSet(queryResultList, scanResultList);
        deleteFileList(ds1);

        // 2. scanResultList - queryResultList
        List<FileMeta> ds2 = ListUtil.differenceSet(scanResultList, queryResultList);
        saveFileList(ds2);
    }

    public List<FileMeta> query(String keyword) {
        return queryDAO.query(keyword);
    }
}