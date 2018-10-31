package com.example.shihong.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;
import java.util.ArrayList;
import java.util.*;

public class Calculator {

    //使用集合定义好符号的运算优先级别
    private static final Map<Character,Integer>basic =new HashMap<Character, Integer>();
    static {
        basic.put('＋',1);
        basic.put('－', 1);
        basic.put('×', 2);
        basic.put('÷', 2);
        basic.put('(', 0);//在运算中（）的优先级最高，但是此处因程序中需要故设置为0
    }

    //将中缀表达式转换为后缀表达式
    public String infixToSuffix(StringBuilder infix){
        List<String> queue = new ArrayList<String>();                                    //用于存储数字以及最后的后缀表达式
        List<Character> stack = new ArrayList<Character>();                             //用于存储运算符
        int sign=0,index=0;
        if(infix.charAt(0) == '-') {                                                   //如果第一个字符为负号，则在负号前插入一个0
            infix.insert(0, '0');
            sign=1;
        }
        char[] charArr = infix.substring(0,infix.length()).trim().toCharArray();
        String standard = "＋－×÷()";
        char ch;
        int len = 0;                                                                    //用于记录字符长度
        for (int i = 0; i < charArr.length; i++) {
            ch = charArr[i];
            if(Character.isDigit(ch))                                                    //如果当前变量为数字
                len++;
            else if(ch == '.')
                len++;
            else if(standard.indexOf(ch) != -1) {
                if(len > 0) {
                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));   //说明符号之前的可以截取下来做数字
                    len = 0;
                }
                if(ch == '(') {
                    stack.add(ch);
                    continue;                                                            //跳出本次循环继续找下一个位置
                }
                if (!stack.isEmpty()) {
                    int size = stack.size() - 1;                                        //栈最后一个元素的下标
                    boolean flag = false;                                                //设置标志位
                    while (size >= 0 && ch == ')' && stack.get(size) != '(') {            //若当前ch为右括号，则栈里元素从栈顶一直弹出，直到弹出到左括号
                        queue.add(String.valueOf(stack.remove(size)));
                        size--;
                        flag = true;                                                    //设置标志位为true，表明一直在取（）中的元素
                    }
                    if(ch==')'&&stack.get(size) == '('){
                        flag = true;
                    }
                    while (size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)) {    //若取得不是（）内的元素，并且当前栈顶元素的优先级>=对比元素 那就出栈插入队列
                        queue.add(String.valueOf(stack.remove(size)));
                        size--;
                    }
                }
                if(ch != ')') {
                    stack.add(ch);
                }
                else {
                    stack.remove(stack.size() - 1);
                }
            }
            if(i == charArr.length - 1) {
                if(len > 0) {
                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len+1, i+1)));
                }
                int size = stack.size() - 1;                                            //栈内最后一个元素下标
                while (size >= 0) {                                                    //一直将栈内符号全部出栈并且加入队列中
                    queue.add(String.valueOf(stack.remove(size)));
                    size--;
                }
            }
        }
        if(sign==1)
            infix.deleteCharAt(0);
        String a = queue.toString();
        return a.substring(1,a.length()-1);
    }

    public String calculateResult(String result){
        String [] arr = result.split(", ");                                    //根据,拆分字符串
        List<String> list = new ArrayList<String>();                            //用于计算时存储运算过程的集合
        BigDecimal bd1;
        BigDecimal bd2;
        for (int i = 0; i < arr.length; i++) {
            int size = list.size();
            switch (arr[i]) {
                case "＋":
                    bd1 = new BigDecimal(list.remove(size - 2));
                    bd2 = new BigDecimal(list.remove(size - 2));
                    list.add(bd1.add(bd2).toString());
                    break;
                case "－":
                    bd1 = new BigDecimal(list.remove(size - 2));
                    bd2 = new BigDecimal(list.remove(size - 2));
                    list.add(bd1.subtract(bd2).toString());
                    break;
                case "×":
                    bd1 = new BigDecimal(list.remove(size - 2));
                    bd2 = new BigDecimal(list.remove(size - 2));
                    list.add(bd1.multiply(bd2).toString());
                    break;
                case "÷":
                    bd1 = new BigDecimal(list.remove(size - 2));
                    bd2 = new BigDecimal(list.remove(size - 2));
                    try{
                        list.add(bd1.divide(bd2).toString());
                    } catch (Exception ex){
                        list.add(bd1.divide(bd2,20,BigDecimal.ROUND_HALF_UP).toString());
                    }
                    break;
                default: list.add(arr[i]);break;                                    //如果是数字直接放进list中
            }
        }
        return list.get(0);
    }

    public String scienceCalculate(StringBuilder expression,String symbol){
        double number=Double.parseDouble(expression.substring(0,expression.length()));
        double result=1;
        BigInteger bi;
        BigDecimal bd;
        switch (symbol) {
            case "sin": return(String.valueOf(Math.sin(number)));
            case "cos": return(String.valueOf(Math.cos(number)));
            case "tan": return(String.valueOf(Math.tan(number)));
            case "log":
                if(number <= 0)
                    return "运算失败";
                return(String.valueOf(Math.log(number)/Math.log(2)));
            case "ln":
                if(number <= 0)
                    return "运算失败";
                return(String.valueOf(Math.log(number)));
            case "²":
                if(number == 0)
                    return "0";
                bd = new BigDecimal(expression.substring(0,expression.length()));
                result=bd.multiply(bd).doubleValue();
                break;
            case "³":
                if(number == 0)
                    return "0";
                bd = new BigDecimal(expression.substring(0,expression.length()));
                result=bd.multiply(bd.multiply(bd)).doubleValue();
                break;
            case "√":
                if(number == 0)
                    return "0";
                if(number <= 0)
                    return "运算失败";
                result=Math.sqrt(number);
                break;
            case "!":
                if ((int) number != number)
                    return "运算失败";
                else{
                    while(number > 0){
                        result = result * number;
                        number--;
                    }
                }
                break;
            case "TenToTwo":
                bi = new BigInteger(expression.substring(0,expression.length()));
                return bi.toString(2);
            case "TwoToTen":
                bi = new BigInteger(expression.substring(0,expression.length()),2);
                return bi.toString();
            case "TenToSixteen":
                bi = new BigInteger(expression.substring(0,expression.length()));
                return bi.toString(16).toUpperCase();
            case "TwoToSixteen":
                bi = new BigInteger(expression.substring(0,expression.length()),2);
                return bi.toString(16).toUpperCase();
            default: ;break;
        }
        if(result > Double.MAX_VALUE || result < Double.MIN_VALUE)
            return "运算失败";
        return String.valueOf(result);
    }
}
