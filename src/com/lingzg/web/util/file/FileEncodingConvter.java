package com.lingzg.web.util.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileEncodingConvter {

	public void convter(File dir) throws IOException{
    	File[] files = dir.listFiles();
    	for(File file : files){
    		if(file.isDirectory()){
    			convter(file);
    		}
    		if(file.isFile()){
    			String filePath = file.getAbsolutePath();
    			String encoding = EncodingDetect.getCharset(filePath);
    			System.out.println(filePath+" : "+encoding);
    			if("utf-8".equalsIgnoreCase(encoding)||"utf8".equalsIgnoreCase(encoding)){
    				continue;
    			}
    			String content = FileUtils.readFileToString(file, encoding);
//    			System.out.println(content);
    			FileUtils.write(file, content, "utf-8");//文件编码格式转为UTF-8
    		}
    	}
    }
	
	public static void main(String[] args) throws IOException {
		FileEncodingConvter util = new FileEncodingConvter();
		File dir = new File("D:\\work\\workspace\\cecsys\\jakra-electricai-web\\src\\com\\cecsys\\electric");
		util.convter(dir);
	}
}
