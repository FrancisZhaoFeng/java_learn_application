package com.meizu.filemanage;

//in ExcelHandle   
//参考：
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Boolean;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelHandle

{
	/*** 读取Excel */

	public static Sheet readExcel(String readExcel) {
		Sheet sheet = null;
		try {
			InputStream is = new FileInputStream(readExcel);
			Workbook rwb = Workbook.getWorkbook(is);
			// Sheet的下标是从0开始 获取第一张Sheet表
			sheet = rwb.getSheet(0);
			// 关闭流
			// rwb.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}

	/** 输出Excel */

	public static void writeExcel(OutputStream os) {
		try {
			/**
			 * 只能通过API提供的 工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，因为类WritableWorkbook的构造函数为 protected类型：方法一：直接从目标文件中读取 WritableWorkbook wwb = Workbook.createWorkbook(new
			 * File(targetfile));方法 二：如下实例所示 将WritableWorkbook直接写入到输出流
			 */
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			/************** 往工作表中添加数据 *****************/
			// 1.添加Label对象
			Label label = new Label(0, 0, "测试");
			ws.addCell(label);
			// 添加带有字型Formatting对象
			WritableFont wf = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			Label labelcf = new Label(1, 0, "this is a label test", wcf);
			ws.addCell(labelcf);
			// 添加带有字体颜色的Formatting对象
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_YELLOW);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			Label labelCF = new Label(1, 0, "Ok", wcfFC);
			ws.addCell(labelCF);
			// 2.添加Number对象
			Number labelN = new Number(0, 1, 3.1415926);
			ws.addCell(labelN);
			// 添加带有formatting的Number对象
			NumberFormat nf = new NumberFormat("#.##");
			WritableCellFormat wcfN = new WritableCellFormat(nf);
			Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			ws.addCell(labelNF);
			// 3.添加Boolean对象
			Boolean labelB = new jxl.write.Boolean(0, 2, true);
			ws.addCell(labelB);
			Boolean labelB1 = new jxl.write.Boolean(1, 2, false);
			ws.addCell(labelB1);
			// 4.添加DateTime对象
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());
			ws.addCell(labelDT);
			// 5.添加带有formatting的DateFormat对象
			DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
			WritableCellFormat wcfDF = new WritableCellFormat(df);
			DateTime labelDTF = new DateTime(1, 3, new java.util.Date(), wcfDF);
			ws.addCell(labelDTF);
			// 6.添加图片对象,jxl只支持png格式图片
			File image = new File("f:\\1.png");
			WritableImage wimage = new WritableImage(0, 4, 6, 17, image);
			ws.addImage(wimage);
			// 7.写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将file1拷贝后,进行修改并创建输出对象file2
	 * 
	 * 单元格原有的格式化修饰不能去掉，但仍可将新的单元格修饰加上去，
	 * 
	 * 以使单元格的内容以不同的形式表现
	 */

	public static void modifyExcel(File file1, File file2) {
		try {
			Workbook rwb = Workbook.getWorkbook(file1);
			WritableWorkbook wwb = Workbook.createWorkbook(file2, rwb);// copy
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			WritableSheet ws = wwb.getSheet(0);
			WritableCell wc = ws.getWritableCell(0, 0);
			// 判断单元格的类型,做出相应的转换
			if (wc.getType() == CellType.LABEL) {
				Label labelCF = new Label(0, 0, "人物（新）", wcfFC);
				ws.addCell(labelCF);
				// Label label = (Label)wc;
				// label.setString("被修改");
			}
			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snFindID(String readExcel, String readFile) {
		List<ApkName> apkName = new ArrayList<ApkName>();
		Sheet sheet = ExcelHandle.readExcel(readExcel);
		ReadFromFile.readFileByLines(readFile, apkName);
		for (int i = 0; i < apkName.size(); i++) {
			ApkName an = apkName.get(i);
			Cell[] cell = sheet.getRow(an.getSn() - 1);
			System.out.println(cell[2].getContents() + "_" + cell[0].getContents() + "_" + an.getSn());
		}
	}

	public static void copyExecl(String readExcel, String writeFile, List<ApkName> apkName) {
		OutputStream os;
		try {
			os = new FileOutputStream(writeFile);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			Sheet sheet = ExcelHandle.readExcel(readExcel);
			for (int i = 0; i < apkName.size(); i++) {
				ApkName an = apkName.get(i);
				Cell[] cell = sheet.getRow(an.getSn() - 1);
				for (int j = 0; j < cell.length + 1; j++) {
					if (j == 0) {
						ws.addCell(new Number(j, i, an.getSn()));
					} else if (j == 1 || j == 2) {
						ws.addCell(new Number(j, i, Integer.parseInt(cell[j - 1].getContents())));
					} else {
						ws.addCell(new Label(j, i, cell[j - 1].getContents()));
					}
				}
			}
			wwb.write();
			wwb.close();
			System.out.println("成功生成excel文件：" + writeFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void copyExcelFristRun() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		List<ApkName> openFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();
		// 根据文件夹中的app获取apk名称
		ReadFromFile.readFileByLines(Constant.txt_downloadFail, downloadFailName);
		ReadFromFile.readFileByLines(Constant.txt_openFail, openFailName);
		ReadFromFile.readFileByLines(Constant.txt_installFail, installFailName);
		// 根据apk名称重excel表中提取对应的行
		ExcelHandle.copyExecl(Constant.excel_topapps, Constant.excel_downloadFail, downloadFailName);
		ExcelHandle.copyExecl(Constant.excel_topapps, Constant.excel_openFail, openFailName);
		ExcelHandle.copyExecl(Constant.excel_topapps, Constant.excel_installFail, installFailName);
	}

	public static void copyExcelSecondRun() {
		// 定义list：解析失败
		List<ApkName> packageError = new ArrayList<ApkName>();
		// 根据文件夹中的app获取apk名称
		ReadFromFile.readFileByLines(Constant.txt_srPackageError, packageError);
		// 根据apk名称重excel表中提取对应的行
		ExcelHandle.copyExecl(Constant.excel_topapps, Constant.excel_srPackageError, packageError);
	}

	public static void getNameByPackage(String readExcel, String readText, int minIndex, int maxIndex) {
		List<ApkName> lApkName = new ArrayList<ApkName>();
		ReadFromFile.readFileByLines(readText, lApkName);
		try {
			Sheet sheet = ExcelHandle.readExcel(readExcel);
			if (readText.contains("install"))
				System.out.println("安装失败：");
			else
				System.out.println("打开失败：");
			String pName = "";
			for (ApkName an : lApkName) {
				pName = an.getName().substring(an.getName().indexOf("_") + 1, an.getName().indexOf(".apk"));
				for (int i = minIndex; i <= maxIndex; i++) {
					Cell[] cell = sheet.getRow(i);
					if (cell[3].getContents().equals(pName)) {
						System.out.println(cell[2].getContents());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void copyExcel(int num) {
		if (num == 1)
			ExcelHandle.copyExcelFristRun();
		else {
			ExcelHandle.copyExcelSecondRun();
		}

	}

	public static void main(String args[]) {
		// ExcelHandle.copyExcelFristRun();
		// ExcelHandle.copyExcelSecondRun();
		// ExcelHandle.getNameByPackage(Constant.excel_topapps, Constant.txt_openFail, 17541, 22541);
		// ExcelHandle.snFindID(Constant.excel_topapps, Constant.txt_inexitApk);
	}

}
