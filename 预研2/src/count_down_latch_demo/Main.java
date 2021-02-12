package count_down_latch_demo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int N = 100;

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);


        Thread[] workers = new Thread[N];
        for (int i = 0; i < N; i++) {
            Thread worker = new Thread(() -> {
                //一进来，先等着
                try {
                    //还没有收到开始信号，等待
                    startSignal.await();

                    Random random = new Random();
                    TimeUnit.SECONDS.sleep(random.nextInt(20) + 10);//休息10-29秒，以秒为单位
                    System.out.printf("%s:已经完成任务\n", Thread.currentThread().getName());//currentThread返回当前线程对象的引用

                    //任务执行完成，但是还没有汇报给上级
                    doneSignal.countDown();
                    //执行完 doneSignal.countDown();代表任务已经完成，并且已经汇报
                } catch (InterruptedException e) {
                    //表示有人让线程中断
                    e.printStackTrace();
                }
            }, String.format("工作线程-%02d", i));
            worker.start();
            workers[i] = worker;

        }

        //特工已经埋伏下去，没有给开始信号，特工不会开始任务
        //所以可以做一些准备工作
        startSignal.countDown();  //发出任务开始的指令，表示可以开始执行任务

        TimeUnit.SECONDS.sleep(10);//这个期间可以做其他工作

        //等待所有特工都完成任务之后，进行情况汇总
        doneSignal.await();  //一共N个信号，只有全部都完成，

        for (Thread worker : workers){
            //大概率的说，任务所有都汇报上来工作了
            System.out.println(worker.getState() == Thread.State.TERMINATED);
        }
    }

}