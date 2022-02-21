package com.zharker.threads;

public class MainTest {
    public static void main(String[] args) throws InterruptedException {

        var t1 = new Thread("t1");
        System.out.println("t1 state:"+t1.getState());

        var t2 = new Thread(()-> {
            sleep(1000L);
            System.out.println("t2 state:"+Thread.currentThread().getState());
        },"t2");
        t2.start();
        sleep(500L);
        System.out.println("t2 state:"+t2.getState());

        var lock = new Object();
        Runnable locking = ()->{
            synchronized (lock) {
                sleep(2000L);
                System.out.println(Thread.currentThread().getName()+" state:" + Thread.currentThread().getState());
            }
        };
        var t3 = new Thread(locking,"t3");
        var t4 = new Thread(locking,"t4");
        t3.start();
        t4.start();
        sleep(500L);
        System.out.println("t3 state:"+t3.getState());
        System.out.println("t4 state:"+t4.getState());

        sleep(10000L);

        var t5 = new Thread(()->{
            synchronized (lock){
                sleep(500L);
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+" state:" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t5");
        var t6 = new Thread(()->{
            synchronized (lock){
                sleep(2000L);
                lock.notifyAll();
            }
        },"t6");
        t5.start();
        t6.start();
        System.out.println("t5 state:"+t5.getState());
        System.out.println("t6 state:"+t6.getState());
        sleep(1000L);
        System.out.println("t5 state:"+t5.getState());
        System.out.println("t6 state:"+t6.getState());
        sleep(2000L);
        System.out.println("t5 state:"+t5.getState());
        System.out.println("t6 state:"+t6.getState());

//        Thread.currentThread().join();
        System.out.println(Thread.currentThread().getName()+" state:" + Thread.currentThread().getState());

    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
