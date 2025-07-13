package com.project;

public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal();
    public static void main(String[] args) {
        local.set("Main Message");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //上面 local.set("Main Message"); 不会对此线程做出改变， 因为set是线程独立的， 所有下面这一行会输出null
                System.out.println(Thread.currentThread().getName() + " :" + local.get());
            }
        }).start();

        System.out.println(Thread.currentThread().getName() + " :" + local.get());

    }
}
