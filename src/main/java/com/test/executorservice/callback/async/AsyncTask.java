package com.test.executorservice.callback.async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncTask implements Runnable{
  @Override
  public void run() {
    // 这里是业务逻辑处理
    System.out.println("子线任务执行:"+Thread.currentThread().getId());

    // 为了能看出效果 ，让当前线程阻塞5秒
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // 处理完业务逻辑，
    Map<String, Object> params = new ConcurrentHashMap<>();
    params.put("a1", "这是我返回的参数字符串...");
    new CallbackImpl().callback(params);
  }
}
