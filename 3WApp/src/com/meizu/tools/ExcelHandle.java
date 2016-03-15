package com.meizu.tools;

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

import com.meizu.bean.ApkName;
import com.meizu.python.ReportInfo;

import contants.Constant;

public class ExcelHandle {

	static ExcelHandle excelHandle = new ExcelHandle();
	ReadFromFile readFromFile = new ReadFromFile();

	/**
	 * @param readExcel
	 * @param sheetNum
	 * @return 读取excel文件
	 */
	public static Sheet readExcel(String readExcel, int sheetNum) {
		Sheet sheet = null;
		try {
			InputStream is = new FileInputStream(readExcel);
			System.out.println(readExcel);
			Workbook rwb = Workbook.getWorkbook(is);
			// Sheet的下标是从0开始 获取第一张Sheet表
			sheet = rwb.getSheet(sheetNum);
			// 关闭流
			// rwb.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * @param os
	 *            输出excel文件
	 */
	public void writeExcelExample(String writeExcel) {
		try {
			OutputStream os = new FileOutputStream(writeExcel);
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

	public static void writePyExcel(List<ReportInfo> reportInfos, String writeExcel, int page) {
		try {
			OutputStream os = new FileOutputStream(writeExcel);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("sheet " + page, page);
			ws.setColumnView(1, 40);
			ws.setColumnView(2, 50);
			ws.setColumnView(3, 20);
			/************** 往工作表中添加数据 *****************/
			for (int i = 0; i < reportInfos.size(); i++) {
				Number nSn = new Number(0, i, reportInfos.get(i).getSn());
				Label lChName = new Label(1, i, reportInfos.get(i).getChName());
				Label lPName = new Label(2, i, reportInfos.get(i).getpName());
				Label lApkInfo = new Label(3, i, reportInfos.get(i).getApkInfo());
				ws.addCell(nSn);
				ws.addCell(lChName);
				ws.addCell(lPName);
				ws.addCell(lApkInfo);

			}
			// 7.写入工作表
			wwb.write();
			wwb.close();
			os.close();
			System.out.println("写入excel成功:" + writeExcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file1
	 * @param file2
	 *            将file1拷贝后,进行修改并创建输出对象file2 单元格原有的格式化修饰不能去掉，但仍可将新的单元格修饰加上去， 以使单元格的内容以不同的形式表现
	 */
	public void modifyExcel(File file1, File file2) {
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

	/**
	 * @param readExcel
	 * @param readFile
	 *            通过apk编号，在top排行榜excel表中找到需要的信息 1.pid+编号+包名 ，用于下载apk 2.应用名称（中文名）+包名，用于报告统计 3.编号+包名，用于生产apk名字
	 */
	public static void snFindID(String readExcel, String readFile, int type) {
		List<ApkName> apkName = new ArrayList<ApkName>();
		Sheet sheet = excelHandle.readExcel(readExcel, 0);
		ReadFromFile.readAppTest(readFile, apkName);
		String typeName = "";
		for (ApkName an : apkName) {
			Cell[] cell = sheet.getRow(an.getSn() - 1);
			switch (type) {
			case 1:
				typeName = cell[0].getContents() + "-" + an.getSn() + "_" + cell[3].getContents();
				break;
			case 2:
				typeName = cell[2].getContents() + "_" + cell[3].getContents();
				break;
			case 3:
				typeName = an.getSn() + "_" + cell[3].getContents() + ".apk";
				an.setName(typeName);
				break;
			}
			System.out.println(typeName);
		}
		ReadFromFile.writeFileByLines("E:\\temp.txt", apkName);
	}

	/**
	 * @param readExcel
	 *            读取top排行榜excel文件，生成用于下载的字符串：pid+编号+包名
	 */
	public void genDownload(String readExcel) {
		Sheet sheet = excelHandle.readExcel(readExcel, 0);
		for (int i = 0; i < sheet.getRows(); i++) {
			Cell[] cell = sheet.getRow(i);
			System.out.println(cell[1].getContents() + "-" + cell[0].getContents() + "_" + cell[4].getContents());
		}
	}

	/**
	 * @param allFile
	 * @param readFailExcel
	 *            用于最终验证，通过包名获取apk名字，从而将apk复制到本地进行最终验证
	 */
	public void packageFindApkName(String serverPath, boolean excelORFile, String readpath) {
		List<ApkName> listApkFName = new ArrayList<ApkName>();
		readFromFile.getApkList(serverPath, listApkFName, ".apk");
		List<String> name = new ArrayList<String>();
		if (excelORFile) {
			Sheet sheetFailExcel = readExcel(readpath, 2);
			for (int i = 0; i < sheetFailExcel.getRows(); i++) {
				Cell[] cell = sheetFailExcel.getRow(i);
				name.add(cell[1].getContents());
			}
		} else {
			readFromFile.readFile(readpath, name);
		}

		for (String strName : name) {
			boolean flag = false;
			for (int i = 0; i < listApkFName.size(); i++) {
				if ((listApkFName.get(i).getName().contains(strName))) {
					System.out.println(listApkFName.get(i).getName());
					flag = true;
					break;
				}
			}
			if (!flag)
				System.out.println(strName);
		}
	}

	/**
	 * @param readTopExcel
	 * @param readFailExcel
	 *            通过应用程序名（中文名或包名），找到其包名，用于开始报告只统计应用程序名（中文名），后需求更改为：应用程序名（中文名）+包名
	 */
	public void nameFindPackage(String readTopExcel, String readFailExcel, int type) {
		Sheet sheetTopExcel = readExcel(readTopExcel, 0);
		Sheet sheetFailExcel = readExcel(readFailExcel, 1);
		List<String> name = new ArrayList<String>();
		for (int i = 0; i < sheetFailExcel.getRows(); i++) {
			Cell[] cell = sheetFailExcel.getRow(i);
			for (int j = 0; j < sheetFailExcel.getColumns(); j++) {
				String cName = cell[j].getContents().trim();
				if (cName != null && !cName.equals("")) {
					name.add(cName);
				}
			}
		}
		System.out.println("================");
		for (String strName : name) {
			boolean flag = false;
			int num = 0;
			for (int i = 0; i < sheetTopExcel.getRows(); i++) {
				Cell[] cell = sheetTopExcel.getRow(i);
				if (strName.equals(cell[2].getContents().trim())) {
					num++;
					switch (type) {
					case 1:
						System.out.println(cell[3].getContents());
						break;
					case 2:
						System.out.println(i + 1 + "_" + cell[3].getContents() + ".apk");
						break;
					case 3:
						System.out.println(i + 1 + "&~&" + strName + "&~&" + cell[3].getContents());
						break;
					}
					flag = true;
				}
			}
			if (num >= 2)
				System.out.println("num:" + num + "==apk:" + strName);
			if (!flag)
				System.out.println(strName);
		}
		System.out.println("获取apk文件名成功");
	}

	/**
	 * @param readTopExcel
	 * @param readName
	 *            用于apk原始名字名称，找到其应用程序名（中文名）
	 */
	public void findApkName(String readTopExcel, String readName) {
		Sheet sheetTopExcel = readExcel(readTopExcel, 0);
		List<String> name = new ArrayList<String>();
		readFromFile.readFile(readName, name);
		for (String strName : name) {
			boolean flag = false;
			if (strName.indexOf(".apk") != -1)
				strName = strName.substring(0, strName.indexOf(".apk"));
			for (int i = 0; i < sheetTopExcel.getRows(); i++) {
				Cell[] cell = sheetTopExcel.getRow(i);
				if (cell[6].getContents().contains(strName)) {
					System.out.println(cell[1].getContents() + "_" + strName + ".apk");
					flag = true;
					break;
				}
			}
			if (!flag)
				System.out.println(strName);
		}
	}

	/**
	 * @param readExcel
	 * @param writeFile
	 * @param apkName
	 *            以topapps文件为基础，生成excel文件
	 */
	public void genExecl(String readExcel, String writeFile, String readFailFile) {
		List<ApkName> failName = new ArrayList<ApkName>();
		readFromFile.readAppTest(readFailFile, failName);//
		OutputStream os;
		try {
			os = new FileOutputStream(writeFile);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			Sheet sheet = excelHandle.readExcel(readExcel, 0);
			for (int i = 0; i < failName.size(); i++) {
				ApkName an = failName.get(i);
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

	public void genExeclDownload(String readExcel, String writeFile, String readFailFile) {
		List<ApkName> failName = new ArrayList<ApkName>();
		readFromFile.genAppListFromTxt(readFailFile, failName);
		OutputStream os;
		int iSheet = 0;
		WritableSheet ws;
		int sheetNum = 140;
		try {
			os = new FileOutputStream(writeFile);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			ws = wwb.createSheet("Test Sheet" + iSheet, iSheet);
			Sheet sheet = excelHandle.readExcel(readExcel, 0);
			for (int i = 0; i < failName.size(); i++) {
				if (i / sheetNum != iSheet) {
					iSheet++;
					ws = wwb.createSheet("Test Sheet" + iSheet, iSheet);
				}

				ApkName an = failName.get(i);
				Cell[] cell = sheet.getRow(an.getSn());
				for (int j = 0; j < cell.length; j++) {
					if (j == 0 || j == 2 || j == 5) {
						ws.addCell(new Number(j, i % sheetNum, Integer.parseInt(cell[j].getContents())));
					} else {
						ws.addCell(new Label(j, i % sheetNum, cell[j].getContents()));
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

	public void genExcelFristRun() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		// 根据文件夹中的app获取apk名称
		// 根据apk名称重excel表中提取对应的行
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_downloadFail, Constant.txt_downloadFail);
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_openFail, Constant.txt_openFail);
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_installFail, Constant.txt_installFail);
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_inexitApk, Constant.txt_inexitApk);
	}

	public void genExcelSecondRun() {
		// 定义list：解析失败
		// 根据文件夹中的app获取apk名称
		// 根据apk名称重excel表中提取对应的行
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_srPackageError, Constant.txt_srPackageError);
	}

	/**
	 * @param readExcel
	 * @param readText
	 * @param minIndex
	 * @param maxIndex
	 *            通过包名获取应用名称（中文名），输出：应用名称（中文名）+包名，用于报告统计
	 */
	public void getNameByPackage(String readExcel, String readText, int minIndex, int maxIndex) {
		List<ApkName> lApkName = new ArrayList<ApkName>();
		readFromFile.readAppTest(readText, lApkName);
		// List<String> lApkName = new ArrayList<String>();
		// readFromFile.readFile(readText, lApkName);
		try {
			Sheet sheet = excelHandle.readExcel(readExcel, 0);
			if (readText.contains("Install"))
				System.out.println("安装失败：");
			else
				System.out.println("打开失败：");
			String pName = "";
			for (ApkName an : lApkName) {
				pName = an.getName().substring(an.getName().indexOf("_") + 1, an.getName().indexOf(".apk"));
				for (int i = minIndex; i <= maxIndex; i++) {
					Cell[] cell = sheet.getRow(i);
					if (cell[3].getContents().equals(pName)) {
						System.out.println(i + "&~&" + cell[2].getContents() + "&~&" + pName);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param num
	 *            根据num值，调用第一轮或第二轮报告生成
	 */
	public void genExcel(int num) {
		if (num == 1)
			excelHandle.genExcelFristRun();
		else {
			excelHandle.genExcelSecondRun();
		}
	}

	public static void main(String args[]) {
		// excelHandle.copyExcelFristRun();
		// excelHandle.copyExcelFristRun();
		// excelHandle.copyExcelSecondRun();
		// excelHandle.getNameByPackage(Constant.excel_topapps, Constant.txt_openFail, 17541, 22541);
		// excelHandle.snFindID(Constant.excel_topapps, "C:\\Users\\zhaoguofeng\\Desktop\\temp.txt", 3);
		// excelHandle.snFindID(Constant.excel_topapps, Constant.txt_inexitApk);
		// excelHandle.packageFindApkName(Constant.serverPath, false, "C:\\Users\\zhaoguofeng\\Desktop\\temp.txt");
		// excelHandle.genDownload(Constant.excel_topapps);
		// excelHandle.packageFindApkName(Constant.serverPath, "E:\\3W_Apps\\temp.xls");
		// excelHandle.packageFindApkName(Constant.serverPath, "E:\\3W_Apps\\2015-08-28\\fold_TextExcel\\3wflyme4.xls");
		// excelHandle.nameFindPackage(Constant.excel_topapps, Constant.fold_TextExcel + "flyme5_iof.xls", 3);
		excelHandle.genExecl(Constant.excel_topapps, Constant.excel_inexitApk, Constant.txt_inexitApk);
		
	}

}
