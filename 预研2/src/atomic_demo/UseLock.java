package atomic_demo;

public class UseLock {
    private static final int COUNT = 1_0000_0000;
    private static int ai = 0;

    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        Thread adder = new Thread(() -> {
            for (int i = 0;i < COUNT;i++){
                //加锁
                synchronized (UseLock.class){
                    ai++;
                }
            }
        });
        adder.start();

        Thread suber = new Thread(() -> {
            for (int i = 0;i < COUNT;i++){
                //加锁
                synchronized (UseLock.class){
                    ai--;
                }
            }
        });
        suber.start();

        adder.join();//等待这个线程结束
        suber.join();

        System.out.println(ai);
        long e = System.currentTimeMillis();
        System.out.println("加锁的时间 = "+(e-b));
    }

}
