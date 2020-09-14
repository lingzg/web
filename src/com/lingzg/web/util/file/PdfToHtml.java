package com.lingzg.web.util.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfToHtml {

	private final static Logger log = Logger.getLogger(PdfToHtml.class);
	
	public static void main(String[] args) {
        //传入PDF地址
        //PdfToImage("d:/doc/开票信息-湖北智控.pdf","d:/doc/html");
        //PdfToImage("d:/doc/营业执照--湖北智控公司.pdf","d:/doc/html");
        PdfToImage("d:/doc/book/tcp_ip_downcc/001.pdf","d:/doc/html");
    }
 
    public static void PdfToImage(String pdfPath, String htmlPath){
        StringBuffer buffer = new StringBuffer();
        FileOutputStream fos;
        PDDocument document;
        File pdfFile;
        int size;
        BufferedImage image;
        FileOutputStream out;
        //PDF转换成HTML保存的文件夹
        FileUtil.fileExists(htmlPath);
        FileUtil.fileExists(htmlPath+"/images");
        try{
            //遍历处理pdf附件
            buffer.append("<!doctype html>\r\n");
            buffer.append("<head>\r\n");
            buffer.append("<meta charset=\"UTF-8\">\r\n");
            buffer.append("</head>\r\n");
            buffer.append("<body style=\"background-color:gray;\">\r\n");
            buffer.append("<style>\r\n");
            buffer.append("img {background-color:#fff; text-align:center; width:100%; max-width:100%;margin-top:6px;}\r\n");
            buffer.append("</style>\r\n");
            document = new PDDocument();
            //pdf附件
            pdfFile = new File(pdfPath);
            document = PDDocument.load(pdfFile, (String) null);
            size = document.getNumberOfPages();
            Long start = System.currentTimeMillis(), end = null;
            log.info("===>pdf : " + pdfFile.getName() +" , size : " + size);
            String name = pdfFile.getName();
            name = name.substring(0, name.lastIndexOf("."));
            PDFRenderer reader = new PDFRenderer(document);
            for(int i=0 ; i < size; i++){
                //image = new PDFRenderer(document).renderImageWithDPI(i,130,ImageType.RGB);
                image = reader.renderImage(i, 1.5f);
                //生成图片,保存位置
                out = new FileOutputStream(htmlPath + "/"+ "images/" + name+"_" + i + ".jpg");
                ImageIO.write(image, "png", out); //使用png的清晰度
                //将图片路径追加到网页文件里
                buffer.append("<img src=\"images/" + name+"_" + i + ".jpg\"/>\r\n");
                image = null; 
                out.flush(); 
                out.close(); 
            }
            reader = null;
            document.close();
            buffer.append("</body>\r\n");
            buffer.append("</html>");
            end = System.currentTimeMillis() - start;
            log.info("===> Reading pdf times: " + (end/1000));
            start = end = null;
            //生成网页文件
            fos = new FileOutputStream(htmlPath+"/"+name+".html");
            log.info(htmlPath+"/"+name+".html");
            fos.write(buffer.toString().getBytes());
            fos.flush(); 
            fos.close();
            buffer.setLength(0);
 
        }catch(Exception e){
            log.error("===>Reader parse pdf to jpg error : " + e.getMessage(), e);
        }
    }

}
