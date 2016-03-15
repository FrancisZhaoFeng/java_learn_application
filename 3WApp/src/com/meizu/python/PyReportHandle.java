package com.meizu.python;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.ExcelHandle;
import com.meizu.tools.ReadFromFile;
import com.meizu.tools.WriteToFile;

import contants.Constant;

public class PyReportHandle {

//	/**
//	 * @param reportInfos
//	 *            补全类ReportInfo的属性（编号，包名，apk中文名，apk执行信息） 1.根据python生成的报告，从而获取apk中文名 2.根据excel表报告，从而获取：编号，包名 type值为true代表：获取apk中文名
//	 */
//	public void getReportAllInfo(List<ReportInfo> reportInfos, boolean type) {
//		try {
//			Sheet sheet = ExcelHandle.readExcel(Constant.excel_topapps, 0);
//			for (int i = 0; i < reportInfos.size(); i++) {
//				boolean flag = false;
//				for (int j = 0; j < sheet.getRows(); j++) {
//					Cell[] cell = sheet.getRow(j);
//					if (type) {
//						// 1.根据python生成的报告，从而获取apk中文名
//						if (cell[3].getContents().trim().equals(reportInfos.get(i).getpName())) {
//							reportInfos.get(i).setChName(cell[2].getContents().trim());
//							flag = true;
//							break;
//						}
//					} else {
//						// 2.根据excel表报告，从而获取：编号，包名
//						if (cell[2].getContents().trim().equals(reportInfos.get(i).getChName())) {
//							reportInfos.get(i).setpName(cell[3].getContents().trim());
//							reportInfos.get(i).setSn(j + 1);
//							flag = true;
//							break;
//						}
//					}
//				}
//				if (!flag) {
//					System.out.println("不存在(移除，避免排序错误)：" + reportInfos.get(i).toString());
//					reportInfos.remove(i);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 读取python写入的报告，得出类ReportInfo的属性（编号，包名，apk中文名，apk执行信息）
	 */
	public static List<ApkName> getPythonInfo(String readFile) {
		List<String> listAName = new ArrayList<String>();
		List<ApkName> apkNames = new ArrayList<ApkName>();
		ReadFromFile.readFile(readFile, listAName);
		String strSplit = listAName.get(0).substring(listAName.get(0).indexOf(":"), listAName.get(0).lastIndexOf("/") + 1);
		for (String an : listAName) {
//			ReportInfo reportInfo = new ReportInfo();
			ApkName apkName =new ApkName();
			String re[] = an.split(strSplit);
			apkName.setSn(Integer.parseInt(re[1].split("_")[0]));
			apkName.setName(re[1].substring(re[1].indexOf("_") + 1, re[1].lastIndexOf(".apk")));
			switch (re[0]) {
			case "crash":
				re[0] = "应用崩溃";
				break;
			case "NotRespond":
				re[0] = "无响应";
				break;
			case "Error":
				re[0] = "错误";
				break;
			case "Success":
				re[0] = "正常";
				break;
			case "OpenFail":
				re[0] = "打开失败";
				break;
			case "InstallFail":
				re[0] = "不能安装";
				break;
			default:
				re[0] = "没有apk信息";
				break;
			}
			apkName.setCrash(re[0]);
			apkNames.add(apkName);
		}
		// getReportAllInfo(reportInfos, true);
		Collections.sort(apkNames);
		return apkNames;
	}

//	/**
//	 * 读取excle报告，得出类ReportInfo的属性（编号，包名，apk中文名，apk执行信息）
//	 */
//	public List<ReportInfo> getExcelInfo() {
//		List<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
//		Sheet sheet = ExcelHandle.readExcel(Constant.fold_TextExcel + "flyme4_liuwen.xls", 0);
//		for (int j = 0; j < sheet.getRows(); j++) {
//			ReportInfo reportInfo = new ReportInfo();
//			Cell[] cell = sheet.getRow(j);
//			reportInfo.setChName(cell[2].getContents().trim());
//			reportInfo.setApkInfo(cell[3].getContents().trim());
//			reportInfos.add(reportInfo);
//		}
//		getReportAllInfo(reportInfos, false);
//		Collections.sort(reportInfos);
//		return reportInfos;
//	}
//
//	public void excelToList(Sheet sheet, List<ReportInfo> Infos) {
//		for (int i = 0; i < sheet.getRows(); i++) {
//			Cell[] cell = sheet.getRow(i);
//			ReportInfo reportInfo = new ReportInfo();
//			reportInfo.setSn(Integer.parseInt(cell[0].getContents().trim()));
//			reportInfo.setChName(cell[1].getContents().trim());
//			reportInfo.setpName(cell[2].getContents().trim());
//			reportInfo.setApkInfo(cell[3].getContents().trim());
//			Infos.add(reportInfo);
//		}
//	}
//
//	public void genReport(String excelReport, String pyReport, String writeReport) {
//		List<ReportInfo> excelInfos = new ArrayList<ReportInfo>();
//		List<ReportInfo> pyInfos = new ArrayList<ReportInfo>();
//		Sheet excelSheet = ExcelHandle.readExcel(excelReport, 0);
//		Sheet pySheet = ExcelHandle.readExcel(pyReport, 0);
//		this.excelToList(excelSheet, excelInfos);
//		this.excelToList(pySheet, pyInfos);
//		for (int i = 0; i < pyInfos.size(); i++) {
//			for (int j = 0; j < excelInfos.size(); j++) {
//				if (pyInfos.get(i).getpName().equals(excelInfos.get(j).getpName())) {
//					pyInfos.get(i).setApkInfo(pyInfos.get(i).getApkInfo() + "&~&" + excelInfos.get(j).getApkInfo());
//					break;
//				}
//			}
//		}
//	}

	public static void main(String[] args) {
		System.out.println("命令执行例子：java -classpath 3w_app.jar com.meizu.python.PyReportHandle M85_20160118011047 m85_ioFail");
		Constant.changeMobileVersion(args[0]);
		String readFileName = args[1];
		List<ApkName> apkNames = PyReportHandle.getPythonInfo(Constant.fold_comFail + readFileName);
		// 生成报告
		WriteToFile.writeSecondRunApptest( Constant.fold_comFail + readFileName + ".txt",apkNames);

	}
}
