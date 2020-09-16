package com.lingzg.web.util.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.ImageType;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class WordToImage {

	public static void main(String[] args) throws FileNotFoundException {
//		docToImg("D:/cecsysProject/项目原型/电力物联网原型更新记录20200619.docx","apo");
//		docToImg("C:/Users/zdka001/Documents/快递费，出租车费，宴请费，月油费，礼品费明细单/油费报销明细说明.doc","okj");
		//docToImg("d:/cecsysProject/智慧用电项目/G5微断电工操作手册.docx","bbt");
		excel2pdf();
		//world2pdf();
	}
	//有水印，最多转换10张图片
	public static void docToImg(String fileName, String imgName) {
        try {
        	File file = new File(fileName);
        	FileInputStream in = new FileInputStream(file);
        	Document doc = new Document();
            //加载文件 第二个参数 FileFormat.Auto 会自动去分别上传文件的 docx、doc类型
            doc.loadFromStream(in, FileFormat.Auto);
            //上传文档页数，也是最后要生成的图片数
            Integer pageCount = doc.getPageCount();
            System.out.println("pageCount:"+pageCount);
            // 参数第一个和第三个都写死 第二个参数就是生成图片数
            BufferedImage[] image = doc.saveToImages(0, pageCount, ImageType.Bitmap);
            // 循环，输出图片保存到本地
            for (int i = 0; i < image.length; i++) {
                File f = new File("d:/doc/img/" + imgName + "_" + (i + 1) + ".png");
                ImageIO.write(image[i], "PNG", f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void excel2pdf() throws FileNotFoundException{
		//加载Excel文档
        Workbook wb = new Workbook();
        File file = new File("d:/cecsysProject/智慧用电项目/5月任务计划.xlsx");
    	FileInputStream in = new FileInputStream(file);
        wb.loadFromStream(in);

        //调用方法保存为PDF格式
        //wb.saveToFile("d:/doc/ToPDF.pdf", com.spire.xls.FileFormat.PDF);
      
        //获取工作表
        Worksheet sheet = wb.getWorksheets().get(0);
        //调用方法将Excel工作表保存为图片
        sheet.saveToImage("d:/doc/ToImg.png");
        //调用方法，将指定Excel单元格数据范围保存为图片
        //sheet.saveToImage("ToImg2.png",8,1,30,7);

        //调用方法将Excel保存为HTML
        sheet.saveToHtml("d:/doc/ToHtml.html");
	}
	
	public static void world2pdf(){
		//加载Excel文档
		Document doc = new Document();
        //加载文件 第二个参数 FileFormat.Auto 会自动去分别上传文件的 docx、doc类型
        doc.loadFromFile("d:/cecsysProject/智慧用电项目/G5微断电工操作手册.docx", FileFormat.Auto);

        //调用方法保存为PDF格式
        //OutputStream out = new FileOutputStream();
        doc.saveToFile("d:/doc/xlsToPDF.pdf", FileFormat.PDF);
	}
	
}
