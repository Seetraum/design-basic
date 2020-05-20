package com.test.executorservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 线程池使用Demo
 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 *
 * private final BlockingQueue<Runnable> workQueue;              //任务缓存队列，用来存放等待执行的任务
 * private final ReentrantLock mainLock = new ReentrantLock();   //线程池的主要状态锁，对线程池状态（比如线程池大小
 *                                                               //、runState等）的改变都要使用这个锁
 * private final HashSet<Worker> workers = new HashSet<Worker>();  //用来存放工作集
 *
 * private volatile long  keepAliveTime;    //线程存货时间
 * private volatile boolean allowCoreThreadTimeOut;   //是否允许为核心线程设置存活时间
 * private volatile int   corePoolSize;     //核心池的大小（即线程池中的线程数目大于这个参数时，提交的任务会被放进任务缓存队列）
 * private volatile int   maximumPoolSize;   //线程池最大能容忍的线程数
 *
 * private volatile int   poolSize;       //线程池中当前的线程数
 *
 * private volatile RejectedExecutionHandler handler; //任务拒绝策略
 *
 * private volatile ThreadFactory threadFactory;   //线程工厂，用来创建线程
 *
 * private int largestPoolSize;   //用来记录线程池中曾经出现过的最大线程数
 *
 * private long completedTaskCount;   //用来记录已经执行完毕的任务个数
 * */
public class ExecutorDemo {
    private static ArrayBlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(30,true);
    private static ThreadPoolExecutor pool =
            new ThreadPoolExecutor(30, 30, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(30));

    public static void main(String[] args) {
        for (var i = 0; i < 25; i++){
            var task = new Task(i);
            taskQueue.offer(task);
        }
        taskQueue.forEach(task -> pool.execute(task));
    }
}
