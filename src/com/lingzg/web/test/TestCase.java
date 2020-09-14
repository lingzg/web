package com.lingzg.web.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.w3c.dom.Document;

import com.lingzg.web.util.file.Excel2HtmlUtil;
import com.lingzg.web.util.file.FileConverter;
import com.lingzg.web.util.file.Word2HtmlUtil;

public class TestCase {

	@Test
	public void testWord2Html() throws Exception{
//		Word2HtmlUtil.word2Html("D:/cecsysProject/项目原型/电力物联网原型更新记录20200619.docx", "D:/test/","doctest1.html");
//		Word2HtmlUtil.word2Html("C:/Users/zdka001/Documents/快递费，出租车费，宴请费，月油费，礼品费明细单/油费报销明细说明.doc", "D:/test/","doctest3.html");
		//Excel2HtmlUtil.readExcelToHtml("D:/cecsysProject/项目原型/污染治理设施用电监控系统（第一批功能）_功能清单.xlsx", "D:/test/污染治理设施用电监控系统（第一批功能）_功能清单.html", true);
//		Excel2HtmlUtil.readExcelToHtml("D:/cecsysProject/项目原型/污染治理设施用电监控系统_功能清单20200804(1).xlsx", "D:/test/污染治理设施用电监控系统_功能清单20200804(1).html", true);
		new FileConverter().wordToHtml("D:/cecsysProject/项目原型/电力物联网原型更新记录20200619.docx", "D:/test/电力物联网原型更新记录20200619.html");
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
}
