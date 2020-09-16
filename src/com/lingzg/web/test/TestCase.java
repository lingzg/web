package com.lingzg.web.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.w3c.dom.Document;

import com.lingzg.web.util.file.EncodingDetect;
import com.lingzg.web.util.file.Excel2HtmlUtil;
import com.lingzg.web.util.file.FileUtil;
import com.lingzg.web.util.file.PdfToHtml;
import com.lingzg.web.util.file.PptToHtml;
import com.lingzg.web.util.file.TextConvterUtil;
import com.lingzg.web.util.file.Word2HtmlUtil;

public class TestCase {

	@Test
	public void testWord2Html() throws Exception{
//		Word2HtmlUtil.word2Html("D:/cecsysProject/项目原型/电力物联网原型更新记录20200619.docx", "D:/doc/html");
//		Word2HtmlUtil.word2Html("d:/doc/dtt.doc", "D:/doc/html");
//		Excel2HtmlUtil.readExcelToHtml("D:/cecsysProject/项目原型/污染治理设施用电监控系统（第一批功能）_功能清单.xlsx", "D:/doc/html");
//		Excel2HtmlUtil.readExcelToHtml("D:/cecsysProject/项目原型/污染治理设施用电监控系统_功能清单20200804(1).xlsx", "D:/doc/html");
//		PptToHtml.ppt03ToHtml("d:/doc/测试.ppt","d:/doc/html");
//		PptToHtml.ppt07ToHtml("d:/doc/工作总结暨工作计划PPT模板.pptx","d:/doc/html");
//		PdfToHtml.toHtml("d:/doc/开票信息-湖北智控.pdf","d:/doc/html");
//        PdfToHtml.toHtml("d:/doc/营业执照--湖北智控公司.pdf","d:/doc/html");
//        PdfToHtml.toHtml("d:/doc/book/tcp_ip_downcc/001.pdf","d:/doc/html");
		TextConvterUtil.toImage("d:/doc/test/test.txt","d:/doc/images");
    	TextConvterUtil.toImage("d:/doc/DxDiag.txt","d:/doc/images");
    	TextConvterUtil.toHtml("d:/doc/test/test.txt", "d:/doc/html");
    	TextConvterUtil.toHtml("d:/doc/DxDiag.txt", "d:/doc/html");
	}
	
	//@Test
	public void excel2pdf() throws Exception{
		//加载Excel文档
        //Workbook wb = new Workbook();
        //wb.loadFromFile("test.xlsx");

        //调用方法保存为PDF格式
        //wb.saveToFile("ToPDF.pdf",FileFormat.PDF);
	}
	//@Test
	public void writToAjax() throws IOException {
		try {
			FileInputStream in = new FileInputStream("d:/doc/times.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			ExcelToHtmlConverter ethc = new ExcelToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			ethc.setOutputColumnHeaders(false);
			ethc.setOutputRowNumbers(false);
			ethc.processWorkbook(wb);
 
			Document htmlDocument = ethc.getDocument();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(out);
 
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			out.close();
 
			String htmlStr = new String(out.toByteArray(), "UTF-8");
 
//			htmlStr = htmlStr.replace("<h2>Sheet1</h2>", "").replace("<h2>Sheet2</h2>", "")
//					.replace("<h2>Sheet3</h2>", "").replace("<h2>Sheet4</h2>", "").replace("<h2>Sheet5</h2>", "");
			System.out.println(htmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test1() throws Exception{
		String textPath = "d:/doc/DxDiag.txt";
		String charset = EncodingDetect.getCharset(textPath);
		System.out.println(charset);
		String fileContent = FileUtils.readFileToString(new File(textPath),charset);
		System.out.println(fileContent);
		List<String> lines = FileUtils.readLines(new File(textPath), charset);
		System.out.println(lines);
	}
	
//	@Test
	public void test2() throws Exception{
		String textPath = "d:/doc/test4.txt";
		String charset = FileUtil.getTxtCharset(textPath);
		System.out.println(charset);
		String fileContent = FileUtils.readFileToString(new File(textPath),charset);
		System.out.println(fileContent);
	}
	
//	@Test
	public void test3() throws Exception{
		String str = "d:/do时间的话c/t端口est是4.t发xt级";
		String regEx = "[\u4e00-\u9fa5]";
		String regEx2 = "[^\u4e00-\u9fa5]";
        System.out.println(str.replaceAll(regEx, "").length());
        System.out.println(str.replaceAll(regEx2, "").length());
	}
}
