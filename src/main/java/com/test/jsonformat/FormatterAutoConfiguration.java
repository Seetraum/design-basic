package com.test.jsonformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

@Configuration
public class FormatterAutoConfiguration {

  //与下面的Bean互斥声明
  @Bean
  @ConditionalOnClass(name = "com.fasterxml.jackson.databind.ObjectMapper")
  @ConditionalOnMissingBean(type = "com.fasterxml.jackson.databind.ObjectMapper")
  public Formatter jsonFormatter(){
    return new JsonFormatter();
  }


  @Bean
  @ConditionalOnBean(ObjectMapper.class)
  public Formatter objectMapperFormatter(ObjectMapper objectMapper){
    return new JsonFormatter(objectMapper);
  }
}
