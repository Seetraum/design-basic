package com.test.context.event;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringEvenListenerBootstrap {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Object.class)
        .listeners(event -> System.out.println("SpringApplication 事件监听器 ："
            + event.getClass().getSimpleName()))
        .web(WebApplicationType.NONE)
        .run(args)
        .close();
  }

}
