package com.test.jsonformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

public class JsonFormatter implements Formatter {

  private final ObjectMapper objectMapper;

  public JsonFormatter(){
    this(new ObjectMapper());
  }
  public JsonFormatter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }


  @Override
  public Object parse(String s, Locale locale) throws ParseException {
    return null;
  }

  @Override
  public String print(Object o, Locale locale) {
    return null;
  }
}
