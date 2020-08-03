package com.test.jsonformat;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.format.Formatter;

@EnableAutoConfiguration
public class FormatterBootstrap {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new
        SpringApplicationBuilder(FormatterBootstrap.class)
        .web(WebApplicationType.NONE)//非web应用
        .run(args);//运行
    //待格式化对象
    Map<String,Object> data = new HashMap<>();
    data.put("name","json mapper");
    //获取来自FormatterAutoConfiguration的Formatter
    Map<String, Formatter> beans = context.getBeansOfType(Formatter.class);
    if (beans.isEmpty()){
      throw new NoSuchBeanDefinitionException(Formatter.class);
    }
    beans.forEach((beanName,formatter) ->{
      System.out.printf("[Bean name : %s] %s.format(data) : %s\n",beanName,formatter.getClass().getSimpleName());
    });
    context.close();
  }
}
