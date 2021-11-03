package com.test.hw.obs;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class ObsUtil {

  private String endPoint = "https://obs.cn-north-1.myhuaweicloud.com";
  //private String endPoint = "https://obs.cn-east-3.myhuaweicloud.com";
  private String ak = "5VG9X35TRG1MZ5DWY0TD";
  private String sk = "rZ9Yixl9p1X5k32SBREjpo3WWqeOl210fXwR5y6M";

  /**
   * 列出所有对象
   * */
  public void getAllObject(){
    ObsClient obsClient = new ObsClient(ak, sk, endPoint);
    ListObjectsRequest request = new ListObjectsRequest("sandbox-experiment-resource-north-4");
    // 设置文件夹对象名"dir/"为前缀
    //request.setPrefix("dir/");
    request.setMaxKeys(1000);

    ObjectListing result;

    do{
      result = obsClient.listObjects(request);
      for (ObsObject obsObject : result.getObjects())
      {
        System.out.println("\t" + obsObject.getObjectKey());
        //System.out.println("\t" + obsObject.getOwner());
      }
      request.setMarker(result.getNextMarker());
    }while(result.isTruncated());
  }

  /**
   * 按文件夹分组列举所有对象
   * */
  public void getObjectsOfDirectory() throws IOException {
    ObsClient obsClient = new ObsClient(ak, sk, endPoint);
    ListObjectsRequest request = new ListObjectsRequest("sandbox-experiment-resource");
    //ListObjectsRequest request = new ListObjectsRequest("sandbox-experiment-resource-north-4");
    request.setMaxKeys(1000);
    // 设置文件夹分隔符"/"
    request.setDelimiter("/");
    ObjectListing result = obsClient.listObjects(request);
    System.out.println("Objects in the root directory:");
    for(ObsObject obsObject : result.getObjects()){
      System.out.println("\t" + obsObject.getObjectKey());
      //System.out.println("\t" + obsObject.getOwner());
    }
    //\r\n为换行符
    FileWriter fw = new FileWriter("/Users/seetraum/Downloads/obs-objects.txt");
    listObjectsByPrefix(obsClient, request, result,fw);
    fw.close();
  }

  /**
   * 递归列出子文件夹中对象的函数listObjectsByPrefix
   * */
  public void listObjectsByPrefix(ObsClient obsClient, ListObjectsRequest request, ObjectListing result,FileWriter fw)
      throws ObsException, IOException {
    for(String prefix : result.getCommonPrefixes()){
      //System.out.println("Objects in folder [" + prefix + "]:");
      request.setPrefix(prefix);
      result  = obsClient.listObjects(request);
      for(ObsObject obsObject : result.getObjects()){
        String k = obsObject.getObjectKey();

        //fw.write(obsObject.getObjectKey());
        //fw.write(System.getProperty("line.separator"));
        /*if(k.indexOf(".yaml") != -1 || k.indexOf(".properties") != -1 || k.indexOf(".conf") != -1 ||
            k.indexOf(".sh") != -1 || k.indexOf(".py") != -1 || k.indexOf(".repo") != -1){
          if(k.indexOf(".zip") != -1 || k.indexOf(".tzr.gz") != -1 || k.indexOf(".tar") != -1 ||
              k.indexOf(".csv") != -1 || k.indexOf(".jar") != -1 || k.indexOf(".sql") != -1){*/
          String[] names = obsObject.getObjectKey().split("\\.");
          //String filename = names[names.length -1];
            String[] cpArray = new String[names.length -1];
            System.arraycopy(names,1,cpArray,0,cpArray.length);
            //String filename = names[1];
            String filename = arrToStr(cpArray);
          System.out.println(filename);
          //ObsObject obj = obsClient.getObject("sandbox-experiment-resource-north-4", obsObject.getObjectKey());
          //InputStream in = obj.getObjectContent();
          //writeFile(filename,in);
          fw.write(filename);
          fw.write(System.getProperty("line.separator"));
        //}
        //System.out.println("\t" + obsObject.getOwner());
      }
      listObjectsByPrefix(obsClient, request, result, fw);
    }
  }
  /**
   * 文件写入
   * */
  public void writeFile(String filename, InputStream in) throws IOException {
    String path = "/Users/seetraum/Downloads/sandbox-obs-file/";
    FileOutputStream fos = new FileOutputStream(path + filename);
    byte[] b = new byte[1024];
    while ((in.read(b)) != -1) {
      fos.write(b);// 写入数据
    }
    in.close();
    fos.close();// 保存数据
  }

  /**
   * 数组转字符串
   * */
  public String arrToStr(String[] arr){
    StringBuilder sb = new StringBuilder(".");
    for (int i = 0; i < arr.length; i++){
      if (i > 0 && i <= arr.length - 1){
        sb.append(".");
        sb.append(arr[i]);
      }else {
        sb.append(arr[i]);
      }
    }
    return sb.toString();
  }
  public static void main(String[] args) throws IOException {
    ObsUtil obsUtil = new ObsUtil();
    //obsUtil.getAllObject();
    obsUtil.getObjectsOfDirectory();
  }
}
