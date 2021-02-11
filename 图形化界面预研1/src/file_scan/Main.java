package file_scan;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        //文件 = 元信息(Meta) + 内容(Content)
        File root = new File("D:\\图片");

        //深度搜索
        traversalDepth(root);

        //广度搜索
        traversalBroadcast(root);
//        String[] list = root.list();
//        System.out.println(Arrays.toString(list));
//
//
//        //方法2打印的是绝对路径
//        File[] files = root.listFiles();
//        System.out.println(Arrays.toString(files));
//
//        //Filter就是一个过滤器
//        File[] files1 = root.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                System.out.println(name);
//                return false;//只有返回true的时候才会返回值，要是false返回的就是空值；
//            }
//        });
    }

    //深度搜索
    //深度优先 - 栈 - 递归
    public static void traversalDepth(File root){
        File[] files = root.listFiles();
        if (files == null){
            return;
        }

        if (files.length == 0){
            return;
        }

        for (File file : files){
            if (!file.isDirectory()){//如果不是是目录,就表明是普通文件，那么就打印文件名字
                System.out.println("普通文件："+file);
                continue;
            }
            //否则是目录，就继续向下面递归调用。
            traversalDepth(file);
        }
    }


    //广度搜索(需要队列配合)
    public static void traversalBroadcast(File root){

        Queue<File> queue = new LinkedList<>();//队列
        queue.add(root);//首先将树的根放进去

        while (!queue.isEmpty()){
            File file = queue.poll();//弹出队头
            boolean directory = file.isDirectory();
            if (directory){
                System.out.println("文件夹："+file);
            }else{
                System.out.println("普通文件："+file);
            }


            //用来判断弹出来的队头有没有孩子，就是判断根有没有左右子树一样，如果有就将孩子入队
            File[] files = file.listFiles();
            if (files == null){
                continue;
            }
            if (files.length == 0){
                continue;
            }
            for (File child : files){
                //所有直接入队
                queue.offer(child);
            }
        }
    }
}
