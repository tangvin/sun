package com.example.user.batch.util;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/17 20:16
 */
public class MyTextFileWriter extends AbstractTextFileHandler{
    BufferedOutputStream writer;
    String lineSeparator;
    boolean endWithLineSeparator;

    public MyTextFileWriter(String fileName,String path,String charSet){
        super(fileName,path,charSet);
        lineSeparator  = System.getProperty("line.separator");
    }

    public MyTextFileWriter(String fileName,String path,String charSet,String separator){
        super(fileName,path,charSet);
        lineSeparator  = separator;
    }

    @Override
    public void open() throws Exception {

        if(!getFile().exists()){
            endWithLineSeparator = true;
            File directory = new File(getPath());
            if(!directory.exists()){
                directory.mkdirs();
            }
            getFile().createNewFile();
        }else {
            InputStreamReader in = new InputStreamReader(new FileInputStream(getFile()),getCharSet());
            long fileLen = getFile().length();
            if(fileLen > 0){
                in.skip(fileLen - 1);
                String lastChar =  String.valueOf((char)in.read());
                if(lastChar.equals("\n") || lastChar.equals("\r")){
                    endWithLineSeparator = true;
                }else {
                    endWithLineSeparator = false;
                }
                in.close();
            }else {
                endWithLineSeparator = true;
            }
        }
        writer = new BufferedOutputStream(new FileOutputStream(getFile(),true));

    }

    @Override
    public void close() throws Exception {
        if(writer == null){
            throw new RuntimeException("File not exist");
        }
        writer.close();
    }

    public void appendLine(String line) throws IOException{
        if(writer == null){
            throw new RuntimeException("File not opened");
        }
        if(endWithLineSeparator){
            writer.write(line.getBytes(this.getCharSet()));
            endWithLineSeparator = false;
        }else {
           writer.write((lineSeparator + line).getBytes(this.getCharSet()));
        }
    }


    public void appendLine(byte[] bytes) throws IOException{
        if(writer == null){
            throw new RuntimeException("File not opened");
        }
        if(endWithLineSeparator){
            writer.write(bytes);
            endWithLineSeparator = false;
        }else {
            writer.write(lineSeparator.getBytes());
            writer.write(bytes);
        }
    }

    public void appendLineSeparator()throws IOException{
        if(writer == null){
            throw new RuntimeException("File not opened");
        }
        writer.write(lineSeparator.getBytes(this.getCharSet()));
    }

    public void appendLines(String[] lines) throws IOException{
        for (String line : lines) {
            appendLine(line);
        }
    }



}
