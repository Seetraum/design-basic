package com.test.lambda;

import java.util.Optional;
import java.util.stream.Stream;

public class Reduce {

  public static void main(String[] args) {
    Optional<Integer> sum = Stream.of(1,2,3).reduce(Integer::sum);
    System.out.println(sum.get());

    String s = "id,SuoShuFenLei,ShengChanChangShang,ChanPingXingHao,MiaoShu,LeiXingDaiMa,YanSeDaiMa,CaiLiaoDaiMa,ZhongLiang,JoydriveTuXing,TuXingLeiXing,WaiJing,NeiJing,KongJing,ShiPeiDaoXianWaiJing,ShiPeiDaoXianJieMianJi"
        + ",FangShui,ZhiXingBiaoZhun,WuLiaoBianMa,LuRuShiJian,LuRuRen,ZuiHouXiuGaiShiJian,ZuiHouXiuGaiRen,ZhuangTai,liuchengid";
    //方法一
    String ss = s.replace("liuchengid","FLOWID").replace("id","RUBBERID");
    //方法二
    String[] arr = s.split(",");
    StringBuilder sb = new StringBuilder();
    for (int i = 0;i < arr.length; i++){
      if (arr[i].equals("id")){
        arr[i] = "RUBBERID";
      }
      if (arr[i].equals("liuchengid")){
        arr[i] = "FLOWID";
      }
      sb.append(arr[i]);
      if (i < arr.length -1){
        sb.append(",");
      }
    }
    System.out.println(ss);
    System.out.println(sb.toString());
  }
}
