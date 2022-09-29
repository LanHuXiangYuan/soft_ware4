package com.homework.software;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
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
        if(args.length!=2&&args.length!=4){
            System.out.println("输入参数不合法，请输入如10 10  或-e exercisefile.txt  -a answerfile.txt ");
            return ; }
        if(args.length==4){
            checkAnswer(args[1],args[3]);
            return;
        }
        Set<String> formulas= new HashSet<String>(Integer.parseInt(args[0])*2);
        generate(Integer.valueOf(args[0]),Integer.valueOf(args[1]),formulas);
        output(Integer.valueOf(args[0]),formulas);
    }

    private static void checkAnswer(String exercisefile,String answerfile) {
        String exercise=new String(Objects.requireNonNull(FileUtils.readFile(exercisefile)));
        String answer=new String(Objects.requireNonNull(FileUtils.readFile(answerfile)));
        String[] exercises = exercise.split("\\r?\\n");
        ArrayList<String> exercisesAnswer=calculate(exercises);
        String[] answers = answer.split("\\r?\\n");

        ArrayList<Integer> corrects=new ArrayList<Integer>(answers.length>>2);
        ArrayList<Integer> wrongs=new ArrayList<Integer>(answers.length>>2);
        for(int i=answers.length-2; i>-1 ;i--){
            if(exercisesAnswer.get(i).equals(answers[i])){
                corrects.add(i+1);
            }else {
                wrongs.add(i+1);
            }
        }
        String result="Correct:"+corrects.size()
                +corrects.toString()+"\n"+"Wrong:"
                +wrongs.size()+wrongs.toString();
        System.out.println(result);
    }



    private static ArrayList<String> calculate(String[] exercises) {
        ArrayList<String> ans= new ArrayList<String>(exercises.length);
        String first,second,operator="+";
        for(int i=0;i<exercises.length-1;i++){
            if(exercises[i].contains("+")) {
                operator="+";}
            else if(exercises[i].contains("-")){
                operator="-";
            }
            else if(exercises[i].contains("*")){
                operator="*";
            }
            else if(exercises[i].contains("/")){
                operator="/";
            }
            first=StringUtils.substringAfter(StringUtils.substringBefore(exercises[i], operator), ":");
            second=StringUtils.substringBefore(StringUtils.substringAfter(exercises[i], operator), "=");
            String an = switch (operator) {
                case "+" -> Integer.toString(Integer.parseInt(first) + Integer.parseInt(second));
                case "-" -> Integer.toString(Integer.parseInt(first) - Integer.parseInt(second));
                case "*" -> Integer.toString(Integer.parseInt(first) * Integer.parseInt(second));
                case "/" -> first+ "/" + second;
                default -> "0";
            };
            ans.add(an);
        }

        return ans;
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
        FileUtils.writeAnswer(exercises.toString(),EXERCISE_ADDRESS);
        FileUtils.writeAnswer(ans.toString(),ANSWER_ADDRESS);
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
                String count = switch (j) {
                    case 0 -> Integer.toString(numbers.get(i) + numbers.get(i - 1));
                    case 1 -> Integer.toString(numbers.get(i) - numbers.get(i - 1));
                    case 2 -> Integer.toString(numbers.get(i) * numbers.get(i - 1));
                    case 3 -> numbers.get(i) + "/" + numbers.get(i - 1);
                    default -> "0";
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
