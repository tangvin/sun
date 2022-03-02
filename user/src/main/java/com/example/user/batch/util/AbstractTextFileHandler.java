package com.example.user.batch.util;

import java.io.File;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/17 20:17
 */
public abstract class AbstractTextFileHandler {
    private String fileName;
    private String path;
    private String charSet;

    private File file;

    public AbstractTextFileHandler(String fileName, String path, String charSet) {
        this.fileName = fileName;
        this.path = path;
        this.charSet = charSet;
        if(path.lastIndexOf(File.separator) == path.length() - 1){
            file = new  File(path + fileName);

        }else {
            file = new File(path + File.separator + fileName);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getCharSet() {
        return charSet;
    }

    protected final File getFile() {
        return file;
    }

    public final String getAbsoutePath(){
        return file.getAbsolutePath();
    }

    public final long length(){
        return file.length();
    }

    public final boolean exist(){
        return file.exists();
    }

    abstract public void open() throws Exception;
    abstract public void close() throws Exception;



}
