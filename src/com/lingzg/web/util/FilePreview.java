package com.lingzg.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FilePreview {
	
	public static String preview(InputStream in, String fileType){
		switch(fileType.toLowerCase()){
		case "doc":
		case "docx":
			return previewWord(in);
		case "xls":
			return previewExcel(in);
		case "xlsx":
			return previewExcelx(in);
		case "ppt":
			return previewPpt(in);
		case "pptx":
			return null;
		case "pdf":
			return previewPdf(in);
		case "txt":
			return previewTxt(in);
		default:
			return null;
		}
	}

	public static String previewWord(InputStream in) {
		WordExtractor ex = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			ex = new WordExtractor(bis);
			String bodyText = ex.getText();
			return bodyText;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ex != null) {
				try {
					ex.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String previewExcel(InputStream in) {
		HSSFWorkbook workbook = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			StringBuilder content = new StringBuilder();
			workbook = new HSSFWorkbook(bis);
			readExcel(workbook, content);
			/*for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				HSSFSheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
				content.append("/n");
				if (null == aSheet) {
					continue;
				}
				for (int rowNum = 0; rowNum <= aSheet.getLastRowNum(); rowNum++) {
					content.append("/n");
					HSSFRow aRow = aSheet.getRow(rowNum);
					if (null == aRow) {
						continue;
					}
					for (int cellNum = 0; cellNum <= aRow.getLastCellNum(); cellNum++) {
						HSSFCell aCell = aRow.getCell(cellNum);
						if (null == aCell) {
							continue;
						}
						if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							content.append(aCell.getRichStringCellValue().getString());
						} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							boolean b = HSSFDateUtil.isCellDateFormatted(aCell);
							if (b) {
								Date date = aCell.getDateCellValue();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								content.append(df.format(date));
							}
						}
					}
				}
			}*/
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String previewExcelx(InputStream in) {
		Workbook workbook = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			StringBuilder content = new StringBuilder();
			workbook = new XSSFWorkbook(bis);
			readExcel(workbook, content);
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void readExcel(Workbook workbook, StringBuilder content){
		for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
			Sheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
			content.append("/n");
			if (null == aSheet) {
				continue;
			}
			for (int rowNum = 0; rowNum <= aSheet.getLastRowNum(); rowNum++) {
				content.append("/n");
				Row aRow = aSheet.getRow(rowNum);
				if (null == aRow) {
					continue;
				}
				for (int cellNum = 0; cellNum <= aRow.getLastCellNum(); cellNum++) {
					Cell aCell = aRow.getCell(cellNum);
					if (null == aCell) {
						continue;
					}
					if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						content.append(aCell.getRichStringCellValue().getString());
					} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						boolean b = HSSFDateUtil.isCellDateFormatted(aCell);
						if (b) {
							Date date = aCell.getDateCellValue();
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							content.append(df.format(date));
						}
					}
				}
			}
		}
	}

	public static String previewPpt(InputStream in) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(in);
			StringBuilder content = new StringBuilder("");
			SlideShow ss = new SlideShow(new HSLFSlideShow(bis));
			Slide[] slides = ss.getSlides();
			for (int i = 0; i < slides.length; i++) {
				TextRun[] t = slides[i].getTextRuns();
				for (int j = 0; j < t.length; j++) {
					content.append(t[j].getText());
				}
				content.append(slides[i].getTitle());
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String previewPdf(InputStream in) {
		BufferedInputStream bis = null;
		OutputStreamWriter writer = null;
		try {
			bis = new BufferedInputStream(in);
			PDFParser parser = new PDFParser(bis);
			parser.parse();
			PDDocument pdfdocument = parser.getPDDocument();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			writer = new OutputStreamWriter(out);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.writeText(pdfdocument, writer);
			writer.close();
			byte[] contents = out.toByteArray();

			String ts = new String(contents);
			return ts;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String previewTxt(InputStream in) {
		BufferedReader bis = null;
		try {
			bis = new BufferedReader(new InputStreamReader(in));
			StringBuilder buf = new StringBuilder();
			String temp;
			while ((temp = bis.readLine()) != null) {
				buf.append(temp).append("\r\n");
				if (buf.length() >= 10000) {
					break;
				}
			}
			return buf.toString();
		} catch (IOException e) {
			return null;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
