package com.example.user.utils;

import com.alibaba.fastjson.JSON;
import com.example.user.batch.rz.DownloadEiopOmgCustomerWhiteListSetNoFile;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.dao.EiopOmgCustomerGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author TY
 * @description
 * @date 2021/9/28 19:26
 */
public class ZipUtils {

    private final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    private static MyTextFileWriter writer = null;
    @Autowired
    EiopOmgCustomerGroupMapper eiopOmgCustomerGroupMapper;

    public static void main(String[] args) throws Exception{

        //获取本地文件
        String localPath = "D:\\testBIN\\111.zip";

        File file = new File(localPath);
        readZipCvsFile(file);

    }


    public static void test1(){
        //获取本地文件
        String localPath = "D:\\testBIN\\111.zip";
        InputStream inputStream =null;
        BufferedReader reader = null;
        FileOutputStream outputStream = null ;
        ZipFile zipFile = null;
        String path = "D:\\testBIN";
        String fileType = "zip";
        Map<String, Object> map = new HashMap<>(1);
        try{

            if(fileType.contains("zip")){
                File file = new File(localPath);
                inputStream = new FileInputStream(file);
                if(!file.exists()){
                    file.createNewFile();
                }
                outputStream =   new FileOutputStream(file);
//                inputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int index ;
                while((index = inputStream.read(bytes))!= -1){
                    outputStream.write(bytes,0,index);
                    outputStream.flush();
                }
                zipFile = new ZipFile(file);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while(entries.hasMoreElements()){
                    ZipEntry zipEntry = entries.nextElement();
                    inputStream = zipFile.getInputStream(zipEntry);
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String userId = null;
                    while((userId = reader.readLine()) != null){
                        map.put("userId",userId);
                        formatData();
                    }
                }
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 格式化数据写入bin文件
     * @param
     */
    public static void formatData() {
        char delimeter = 27;
        StringBuilder sb = new StringBuilder("");
        try {
            int count = 0;
            while (true) {
                count++;
                sb.append(count).append(delimeter);
                if (count > 5) {
                    break;
                }
            }
            writer.appendLine(sb.toString());
        } catch (Exception e) {
           e.printStackTrace();
        }

    }


    public static void readZipCvsFile(File file) throws Exception {
        //获得输入流，文件为zip格式，
        //zip可以包含对个文件，如果只有一个文件，则只解析一个文件的，包含多个文件则分别解析
        ZipInputStream in = new ZipInputStream(new FileInputStream(file));
        //不解压直接读取,加上gbk解决乱码问题
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
        ZipEntry zipFile;
        //循环读取zip中的cvs/txt文件，zip文件名不能包含中文
        while ((zipFile = in.getNextEntry())!=null) {
            //如果是目录，不处理
            if (zipFile.isDirectory()){
                System.err.println("当前路径为目录："+zipFile.getName());
            }
            //获得cvs名字
            String fileName = zipFile.getName();
            //检测文件是否存在
            if (fileName != null && fileName.indexOf(".") != -1) {
                System.out.println("---------------------开始解析文件："+fileName+"-----------------------------");
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        //关闭流
        br.close();
        in.close();
    }

}
