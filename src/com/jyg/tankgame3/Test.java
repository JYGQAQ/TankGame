package com.jyg.tankgame3;

public class Test implements Runnable{
    //共享资源变量
    static int count = 0;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                System.out.println(Thread.currentThread().getName()+":"+count++);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test syncTest1 = new Test();
        Test syncTest2 = new Test();
        Thread thread1 = new Thread(syncTest1,"thread1");
        Thread thread2 = new Thread(syncTest2, "thread2");
        thread1.start();
        thread2.start();
    }
    /**
     * 输出结果
     thread1:0
     thread2:0
     thread1:1
     thread2:1
     thread1:2
     thread2:2
     thread1:3
     thread2:3
     thread1:4
     thread2:4
     */
}