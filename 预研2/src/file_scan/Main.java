package file_scan;

import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static void scanDirectory(File root) throws InterruptedException {
        ExecutorService threadPool = new ThreadPoolExecutor(
                10,10,
                0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );

        //打卡器 counter = new 打卡器();
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch doneSingle = new CountDownLatch(1);
        ScanTask task = new ScanTask(root,threadPool,counter, doneSingle);

        //打卡
        counter.incrementAndGet();//先加在获取

        threadPool.execute(task);//将Runable放到线程池当中

        // TODO:想办法在在这里等，直到root整棵树被扫描完成
        /**
         * 1.怎么知道被扫描完成，所有任务都完成了？
         * 2.采用wait(),但是谁去通知它！  —— 最后完成的一个扫描任务(ScanTask)在完成之后，去通知主线程任务完成了
         * 3.如何知道自己是不是最后一个任务？
         *        一种可能解法：构造任务的时候--传入待扫描的文件夹(dir)
         *                    (1)dir扫描出来 -- 只需要扫描dir的孩子节点即可
         *                    (2)扫描出来的孩子还是文件夹，封装成任务传给线程池
         *
         */
        doneSingle.await();

        //关闭一下线程池
        threadPool.shutdown();
    }


    public static void main(String[] args) throws InterruptedException {
        File root = new File("G:\\C语言平时代码");
        long b = System.currentTimeMillis();
        scanDirectory(root);//扫描文件夹

        System.out.println("root下面所有文件全面扫描完成");
        long e = System.currentTimeMillis();
        System.out.println(e-b);
    }
}
