package com.test.thread;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class ThreadTest {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(10);
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    List<Future> futures = new ArrayList<>();
    Workbench w = new Workbench();
    Consumer c = new Consumer(w);
    Product p = new Product(w);
    workQueue.add(c);
    workQueue.add(p);
    workQueue.forEach(wq -> {
      futures.add(service.submit(wq));
    });
    TimeUnit.SECONDS.sleep(20);
    futures.forEach(future -> {
      boolean isCancel = future.cancel(true);
      System.out.println("结束状态：" + isCancel);
    });
    service.shutdown();
  }

  static class Consumer implements Runnable{
    private Workbench w;
    Consumer(Workbench w){
      this.w = w;
    }
    @Override
    public void run() {
      while (true) {
        w.take();
        try {
          sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
  static class Product extends Thread{
    private Workbench w;
    Product(Workbench w){
      this.w = w;
    }
    @Override
    public void run() {
      while (true) {
        w.put();
        try {
          sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class Workbench{
    private static final int MAX_VALUE = 10;
    private int num;

    public synchronized void take(){
      if (num <= 0){
        try {
          this.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      num--;
      System.out.println("消费后剩余：" + num);
      this.notifyAll();
    }

    public synchronized void put(){
      if (num >= MAX_VALUE){
        try {
          this.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      num++;
      System.out.println("生产后总量：" + num);
      this.notifyAll();
    }
  }
}
