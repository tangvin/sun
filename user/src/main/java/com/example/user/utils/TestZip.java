package com.example.user.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * @author TY
 * @description
 * @date 2021/9/28 20:03
 */
public class TestZip {

    public static void zipUncompress(String inputFile) throws Exception {
        File srcFile = new File(inputFile);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        String destDirPath = inputFile.replace(".zip", "");
        //创建压缩文件对象
        ZipFile zipFile = new ZipFile(srcFile);
        //开始解压
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            // 如果是文件夹，就创建个文件夹
            if (entry.isDirectory()) {
                srcFile.mkdirs();
            } else {
                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                File targetFile = new File(destDirPath + "/" + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                fos.close();
                is.close();
            }
        }
    }

    public static void readFiles(String inputFile) throws Exception {
        File srcFile = new File(inputFile);
        if (srcFile.isDirectory()) {
            File next[] = srcFile.listFiles();
            for (int i = 0; i < next.length; i++) {
                System.out.println(next[i].getName());
                if(!next[i].isDirectory()){
                    BufferedReader br = new BufferedReader(new FileReader(next[i]));
                    List<String> arr1 = new ArrayList<>();
                    String contentLine ;
                    while ((contentLine = br.readLine()) != null) {
                       arr1.add(contentLine);
                    }
                    System.out.println("arr1.toString()=="+arr1.toString());
                    System.out.println("arr1.get(0)=="+arr1.get(0));
                }

            }
        }
    }

    public static void main(String[] args) {
        try {
            String localPath2 = "D:\\path222";
            UUID uuid = UUID.randomUUID();

            Path tempPath = Paths.get(localPath2, File.separator + uuid.toString()+".txt");
            //通过oaas获取文件流
            String localPath = "D:\\testBIN\\test.txt";
            File localFile = new File(localPath);
            FileInputStream fileInputStream = new FileInputStream(localFile);



            //文件写到本地
            Files.copy(fileInputStream, tempPath);

            String path = "D:\\testBIN\\test111";
            zipUncompress(path);
            readFiles(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
