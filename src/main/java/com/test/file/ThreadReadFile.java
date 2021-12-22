package com.test.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadReadFile implements Runnable{

  //定义文件读取的游标位置
  private static int now=0;
  //定义即将被读取的文件
  static File file = new File("source/error.log");
  //使用io包中的RandomAccessFile类，支持文件的随机访问
  static RandomAccessFile raf=null;
  //定义每次读取的字节数
  final static int len=256;

  ThreadReadFile() throws IOException {
    raf=new RandomAccessFile(file, "rw");
  }
  @Override
  public void run() {
    while(true){
      try {
        //synchronized实现多线程的同步
        synchronized (raf) {
          //将文件内容读取到b字节数组中
          byte[] b = new byte[len];
          //设置游标位置
          raf.seek(now);
          int temp=raf.read(b);
          //如果没读取到，就结束线程
          if(temp==-1){
            return ;
          }
          //设置游标偏移量
          now+=temp;
          //打印文件内容
          System.out.println(new String(b));
        }


      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
  public static void main(String[] args) throws IOException {
    ThreadReadFile run = new ThreadReadFile();
    ExecutorService service = Executors.newFixedThreadPool(4,new SimpleThreadFactory("readfile"));
    service.submit(run);
  }

  static class SimpleThreadFactory implements ThreadFactory {

    private final String threadNamePrefix;

    public SimpleThreadFactory(String threadNamePrefix) {
      this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread thread = new Thread(r);
      thread.setName(threadNamePrefix + "-" + thread.getId());
      return thread;
    }
  }
}
