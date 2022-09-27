package com.homework.software;

import com.sun.source.util.SourcePositions;

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


    public static void main(String[] args) {
        if(args.length!=2){ return ; }
        generate(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
    }

    private static void generate(Integer num,Integer range) {
        System.out.println("num:"+num);
        System.out.println("range:"+range);

        //从范围内选择不重复的随机数
        Set<Integer> hs = new HashSet<Integer>();
        Random r = new Random();
        for(int i=0;i<num;i++){
            hs.add(r.nextInt(range));
        }
        ArrayList<Integer> numbers =new ArrayList<Integer>(hs);

        StringBuilder sb= new StringBuilder();
        Set<String> formulas= new HashSet<String>(num*2);
        for(int i=numbers.size()-1;i>0;i--){
            for(int j=0;j<OPERATORS_NUMBER;j++){
                //利用数字numbers.get(i)和numbers.get(i-1)和OPERATORS生成算式
                sb.delete(0,sb.length());
                int count = switch (j) {
                    case 0 -> numbers.get(i) + numbers.get(i - 1);
                    case 1 -> numbers.get(i) - numbers.get(i - 1);
                    case 2 -> numbers.get(i) * numbers.get(i - 1);
                    case 3 -> numbers.get(i) / numbers.get(i - 1);
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

        for(String str:formulas){
            System.out.println("算式："+str);
        }
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
}
