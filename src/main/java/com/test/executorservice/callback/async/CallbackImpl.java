package com.test.executorservice.callback.async;

import java.util.Map;

public class CallbackImpl implements ICallback {

  @Override
  public void callback(Map<String, Object> params) {
    params.forEach((k,v) -> {
      System.out.printf("Callback params key = %s, Value = %s ",k,v);
    });
  }
}
