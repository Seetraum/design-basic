package test.executorservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 线程池使用Demo
 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 * */
public class ExecutorDemo {
    private static ArrayBlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(30,true);
    private static ThreadPoolExecutor pool =
            new ThreadPoolExecutor(30, 30, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(30));

    public static void main(String[] args) {
        for (var i = 0; i < 25;i++){
            var task = new Task(i);
            taskQueue.offer(task);
        }
        taskQueue.forEach(task -> pool.execute(task));
    }
}
