package file_scan;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

//实现Runable接口
public class ScanTask implements Runnable {
    private final File directory;

    //线程池属性
    private final ExecutorService threadPool;

    //打卡器属性
    //private final 打卡器 counter;
    private final AtomicInteger counter;

    private final CountDownLatch doneSignal;

    public ScanTask(File directory, ExecutorService threadPool, AtomicInteger counter, CountDownLatch doneSignal) {
        this.directory = directory;
        this.threadPool = threadPool;
        this.counter = counter;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        //线程中进来任务打卡 加加操作
        //counter.add();
        //counter.incrementAndGet();//先加在获取
        System.out.println("文件夹是：" + directory);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file);
                if (file.isDirectory()) {
                    ScanTask task = new ScanTask(file, threadPool, counter, doneSignal);

                    //打卡
                    counter.incrementAndGet();//先加在获取

                    //提交给线程池
                    threadPool.execute(task);
                }
            }
        }


        //销卡，减减任务
//        counter.sub();
//        if (counter.get() == 0){
//            //代表我就是最后一个任务
        //       }

        if (counter.decrementAndGet() == 0) {//先减在获取
            //代表我就是最后一个任务
            doneSignal.countDown();//发送结束信号
        }
    }
}
