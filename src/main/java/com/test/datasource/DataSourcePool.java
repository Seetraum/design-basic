package com.test.datasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DataSourcePool implements DataSource {
  private static LinkedList<Connection> pool = new LinkedList<>();

  @Override
  public Connection getConnection() throws SQLException {
    return null;
  }

  public  DataSourcePool(int count){
    for (int i = 0; i < count ; i++) {
//      Connection connection = JdbcUtils.getConnection();//      pool.add(connection);
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    Connection conn = pool.removeFirst();
    return conn;
  }

  //动态代理获取
  public Connection getConn() throws SQLException {
    Connection conn = pool.removeFirst();
    //使用动态代理改造
    ClassLoader  loader = conn.getClass().getClassLoader();
    //Class<?>[] interfaces = conn.getClass().getInterfaces();
    Class<?>[] interfaces = {java.sql.Connection.class};
    InvocationHandler ih = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果是close方法就改造
        if ("close".equals(method.getName())){
          pool.addLast(conn);
        }else {//如果是其它方法直接反射调用
          Object invoke = method.invoke(conn,args);
          return invoke;
        }
        return null;
      }
    };
    Connection connProxy = (Connection) Proxy.newProxyInstance(loader,interfaces,ih);
    return connProxy;
  }

  public void backToPool(Connection conn){
    pool.add(conn);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {

  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}
