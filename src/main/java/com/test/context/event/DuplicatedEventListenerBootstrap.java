package com.test.context.event;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class DuplicatedEventListenerBootstrap {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DuplicatedEventListenerBootstrap.class)
        .web(WebApplicationType.NONE)
        .run(args)
        .close();
  }
}
