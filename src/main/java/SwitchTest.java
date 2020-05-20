public class SwitchTest {
    public static void main(String[] args) {
        Weekday day = Weekday.FRI; //初始化一个枚举变量
        //case L:标签具有贯穿功能
        switch (day){//不需要break;
            case MON, TUE, WEN -> {
                System.out.println("上半周");
                String s = "abc"; //局部变量只在当前代码块有效
            }
            case THU, FRI      -> {
                System.out.println("下半周");
                String s = "def";
            }
            case SAT, SUN      -> {
                System.out.println("周末");
            }
        }

        switch (day){
            case FRI:
                System.out.println("1111");
            case MON:
                System.out.println("2222");
        }
    }
    //定义枚举
    enum Weekday{
        MON,TUE,WEN,THU,FRI,SAT,SUN
    }

}
