package atomic_demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int COUNT = 1_0000_0000;
    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        Thread adder = new Thread(() -> {
           for (int i = 0;i < COUNT;i++){
               ai.getAndIncrement();//就是加加之后获取
           }
        });
        adder.start();

        Thread suber = new Thread(() -> {
           for (int i = 0;i < COUNT;i++){
               ai.getAndDecrement();
           }
        });
        suber.start();

        adder.join();//等待这个线程结束
        suber.join();

        System.out.println(ai.get());
        long e = System.currentTimeMillis();
        System.out.println("AtomicInteger不加锁的时间 = "+(e-b));
    }

}
