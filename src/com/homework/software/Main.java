package com.homework.software;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @auther DR_bluelake
 * @create 2022/9/27-13:30
 */
public class Main {

    final static String[] OPERATORS={"+","-","*","/"};
    final static int OPERATORS_NUMBER=4;
    final static String ANSWER_ADDRESS="Answers.txt";
    final static String EXERCISE_ADDRESS="Exercises.txt";


    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("输入参数不为2");
            return ; }
        Set<String> formulas= new HashSet<String>(Integer.parseInt(args[0])*2);

        generate(Integer.valueOf(args[0]),Integer.valueOf(args[1]),formulas);
        output(Integer.valueOf(args[0]),formulas);
    }

    private static void output(Integer num,Set<String> formulas) {
        int i=1;
        StringBuilder ans= new StringBuilder();
        StringBuilder exercises= new StringBuilder();
        for (Iterator<String> iterator = formulas.iterator(); iterator.hasNext();)
        {
            String str=iterator.next();
            exercises.append(i).append(" .四则运算题目:").append(StringUtils.substringBefore(str, "=")).append("=").append("\n");;
            ans.append(i).append("  .答案:").append(StringUtils.substringAfter(str, "=")).append("\n");
            i++;
            if(i==num+1)break;
        }
        System.out.println(exercises.toString());
        System.out.println(ans.toString());
        writeAnswer(exercises.toString(),EXERCISE_ADDRESS);
        writeAnswer(ans.toString(),ANSWER_ADDRESS);
    }

    static public void writeAnswer(String answer,String address) {
        File answer_file=new File(address);
        if(!answer_file.exists()){
            try {
                answer_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes =answer.getBytes();
        int b=bytes.length;  //是字节的长度，不是字符串的长度
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(answer_file);
            fileOutputStream.write(bytes,0,b);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generate(Integer num,Integer range,Set<String> formulas) {
        //从范围内选择不重复的随机数
        Set<Integer> hs = new HashSet<Integer>();
        Random r = new Random();
        for(int i=0;i<num;i++){
            hs.add(r.nextInt(range));
        }
        ArrayList<Integer> numbers =new ArrayList<Integer>(hs);

        StringBuilder sb= new StringBuilder();
        for(int i=numbers.size()-1;i>0;i--){
            for(int j=0;j<OPERATORS_NUMBER;j++){
                if(j==3&&numbers.get(i - 1)==0)continue;
                //利用数字numbers.get(i)和numbers.get(i-1)和OPERATORS生成算式
                sb.delete(0,sb.length());
                float count = switch (j) {
                    case 0 -> numbers.get(i) + numbers.get(i - 1);
                    case 1 -> numbers.get(i) - numbers.get(i - 1);
                    case 2 -> numbers.get(i) * numbers.get(i - 1);
                    case 3 -> (float)numbers.get(i) / (float)numbers.get(i - 1);
                    default -> 0;
                };
                sb.append(numbers.get(i))
                        .append(OPERATORS[j])
                        .append(numbers.get(i - 1))
                        .append("=")
                        .append(count);
                formulas.add(sb.toString());
            }
        }


    }


}
