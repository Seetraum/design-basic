package com.test.executorservice.callback;

import com.test.executorservice.callback.async.AsyncTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskTest {
  private static ExecutorService executorService = Executors.newFixedThreadPool(4);

  public static void main(String[] args) {
    CompletableFuture.runAsync(new AsyncTask(),executorService);
    executorService.shutdown();
  }
}
