package test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedList;

public class CalculateTest {
    public static void main(String[] args) {
        //必须用键盘加减乘除（+-*/），并且括号为英文输入状态下的
        System.out.println(getResult("(12+5*6*2)/((2+1)*(1+1+1))"));
    }

    private static boolean isRightFormat = true;

    public static double getResult(String formula){
        LocalDate now = LocalDate.now();
        LocalDate  n = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(now.toString());
        System.out.println(n.toString());
        double returnValue = 0;
        try{
            returnValue = doAnalysis(formula);
        }catch(NumberFormatException nfe){
            System.out.println("公式格式有误，请检查:" + formula);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!isRightFormat){
            System.out.println("公式格式有误，请检查:" + formula);
        }
        return returnValue;
    }

    private static double doAnalysis(String formula){
        double returnValue = 0;
        //记录公式
        LinkedList<Integer> stack = new LinkedList<Integer>();
        //字符数组长度下标
        int curPos = 0;
        String beforePart = "";
        String afterPart = "";
        String calculator = "";
        isRightFormat = true;
        //检索字符串是否含有括号
        while(isRightFormat && (formula.indexOf('(') >= 0 || formula.indexOf(')') >= 0)){
            curPos = 0;
            //字符串转为字符 遍历
            for(char s : formula.toCharArray()){
                if(s == '('){
                    stack.add(curPos);
                }else if(s == ')'){
                    if(stack.size() > 0){
                        beforePart = formula.substring(0, stack.getLast());
                        afterPart = formula.substring(curPos + 1);
                        calculator = formula.substring(stack.getLast() + 1, curPos);
                        formula = beforePart + doCalculation(calculator) + afterPart;
                        stack.clear();
                        break;
                    }else{
                        System.out.println("有未关闭的右括号！");
                        isRightFormat = false;
                    }
                }
                curPos++;
            }
            if(stack.size() > 0){
                System.out.println("有未关闭的左括号！");
                break;
            }
        }
        if(isRightFormat){
            returnValue = doCalculation(formula);
        }
        return returnValue;
    }

    private static double doCalculation(String formula) {
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> operators = new ArrayList<String>();
        int curPos = 0;
        int prePos = 0;
        int minus = 0;
        for (char s : formula.toCharArray()) {
            if ((s == '+' || s == '-' || s == '*' || s == '/') && minus != 0 && minus != 2) {
                values.add(Double.parseDouble(formula.substring(prePos, curPos).trim()));
                operators.add(String.valueOf(s));
                prePos = curPos + 1;
                minus = minus + 1;
            }else{
                minus = 1;
            }
            curPos++;
        }
        //最后的数字
        values.add(Double.parseDouble(formula.substring(prePos).trim()));
        char op;
        for (curPos = 0; curPos <= operators.size() - 1; curPos++) {
            op = operators.get(curPos).charAt(0);
            switch (op) {
                case '*':
                    values.add(curPos, values.get(curPos) * values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    curPos = -1;
                    break;
                case '/':
                    values.add(curPos, values.get(curPos) / values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    curPos = -1;
                    break;
            }
        }
        for (curPos = 0; curPos <= operators.size() - 1; curPos++) {
            op = operators.get(curPos).charAt(0);
            switch (op) {
                case '+':
                    values.add(curPos, values.get(curPos) + values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    curPos = -1;
                    break;
                case '-':
                    values.add(curPos, values.get(curPos) - values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    curPos = -1;
                    break;
            }
        }
        return values.get(0).doubleValue();
    }
}
