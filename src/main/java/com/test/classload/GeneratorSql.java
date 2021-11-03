package com.test.classload;

import com.test.classload.annotation.Column;
import com.test.classload.annotation.Table;
import com.test.classload.model.Person;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GeneratorSql {

  public static String query(Person person){
    StringBuilder sb = new StringBuilder();
    Class c = person.getClass();
    boolean isExists = c.isAnnotationPresent(Table.class);
    if (!isExists){
      return null;
    }
    Table t = (Table) c.getAnnotation(Table.class);
    String tableName = t.value();
    sb.append("select * from ").append(tableName).append(" where 1=1 ");
    Field[] fields = c.getDeclaredFields();
    for (Field field : fields){
      boolean fExists = field.isAnnotationPresent(Column.class);
      if (!fExists){
        continue;
      }
      Column column = field.getAnnotation(Column.class);
      String columnName = column.value();
      //获取字段值
      String fName = field.getName();
      String getMethodName = "get" + fName.substring(0,1).toUpperCase() + fName.substring(1);
      Object fValue = null;
      try {
        Method method = c.getMethod(getMethodName);
        fValue = method.invoke(person,null);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      if (fValue == null || (fValue instanceof Integer && (Integer) fValue == 0)){
        continue;
      }
      sb.append(" and ").append(columnName);
      if (fValue instanceof String){
        sb.append(" = ").append("'").append(fValue).append("'");
      }else if (fValue instanceof Integer){
        sb.append(" = ").append(fValue);
      }
    }
    return sb.toString();
  }
}
