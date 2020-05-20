package com.test.executorservice;

public class Task  implements Runnable{
    private int counter;
    public Task(int counter){
        this.counter = counter;
    }
    @Override
    public void run() {
        System.out.println("加入线程队列运行任务...." + counter);
    }
}
