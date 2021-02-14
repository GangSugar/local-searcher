package com.zg.util;

import com.zg.model.FileMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {
    /**
     * 这个方法实现的是前面的文件列表减去后面的文件列表，做差值
     * list1 - list2
     * @param list1
     * @param list2
     * @return
     */
    public static <E> List<E> differenceSet(List<E> list1, List<E> list2) {

        List<E> resultList = new ArrayList<>();

        //1.首先遍历list1
        for (E item : list1){
            //这里有一个小bug，就是contains是equals方法实现的，所以我们的FileMeta(E)应该能够支持equals方法，因此要去模型类里面去补充
            if (!list2.contains(item)){//如果list2中没有改元素，则1减去2，应该将1中的保存下来
                resultList.add(item);
            }
        }

        //2.最后返回的是一个差值文件列表
        return resultList;
    }

    //用来进行测试改该方法的
    public static void main(String[] args) {
        List<FileMeta> fileList1 = Arrays.asList(
                new FileMeta(new File("G:\\电路\\板书1.txt")),
                new FileMeta(new File("G:\\电路\\板书2.txt"))
        );

        List<FileMeta> fileList2 = Arrays.asList(
                //ew FileMeta(new File("G:\\电路\\板书1.txt")),
                new FileMeta(new File("G:\\电路\\板书2.txt")),
                new FileMeta(new File("G:\\电路\\板书3.txt"))
        );

        System.out.println(differenceSet(fileList1,fileList2));
        System.out.println("==============");
        System.out.println(differenceSet(fileList2,fileList1));
    }
}
