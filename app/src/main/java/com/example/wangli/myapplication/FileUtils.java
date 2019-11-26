package com.example.wangli.myapplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {


    public static void copyFile(File sourceFile,File targetFile) throws IOException {
        //新建文件输入流，并对他进行缓冲
        FileInputStream inputStream = new FileInputStream(sourceFile);
        BufferedInputStream bufferedOutputStream = new BufferedInputStream(inputStream);

        //新建文件输出流并对他进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream bos = new BufferedOutputStream(output);

        //缓冲数组
        byte[] b = new byte[1024*5];
        int len;
        while ((len=bufferedOutputStream.read(b))!=-1){
            bos.write(b,0,len);
        }
        //刷新此缓冲区的流
        bos.flush();
        /**
         * 关闭流
         */
        inputStream.close();
        bos.close();
        output.close();
        bufferedOutputStream.close();

    }
}
