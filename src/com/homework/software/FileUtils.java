package com.homework.software;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @auther DR_bluelake
 * @create 2022/9/30-1:26
 */
public class FileUtils {
    static public byte[] readFile(String path) {
        File file = new File(path);
        try(FileInputStream fileInputStream =new FileInputStream(file)) {
            byte [] bytes =new byte[1024*10];
            int len=fileInputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public void writeAnswer(String str,String address) {
        File answer_file=new File(address);
        if(!answer_file.exists()){
            try {
                answer_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes =str.getBytes();
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
