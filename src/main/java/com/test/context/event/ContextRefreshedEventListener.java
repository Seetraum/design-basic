package com.test.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

  private final long id;

  public ContextRefreshedEventListener() {
    this.id = System.nanoTime();
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    System.out.printf("ContextRefreshedEventListener[id : %d]  接收到事件：%s\n",
        id,event.getClass().getSimpleName());
  }

  //返回纳秒时间
  public int hashCode(){
    return (int) System.nanoTime();
  }
}
