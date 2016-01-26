package com.meizu.python;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import com.meizu.tools.ExcelHandle;
import com.meizu.tools.ReadFromFile;

import contants.Constant;

public class PyReportHandle {
	private static ExcelHandle excelHandle = new ExcelHandle();
	private static ReadFromFile readFromFile = new ReadFromFile();

	/**
	 * @param reportInfos
	 *            补全类ReportInfo的属性（编号，包名，apk中文名，apk执行信息） 1.根据python生成的报告，从而获取apk中文名 2.根据excel表报告，从而获取：编号，包名 type值为true代表：获取apk中文名
	 */
	public void getReportAllInfo(List<ReportInfo> reportInfos, boolean type) {
		try {
			Sheet sheet = excelHandle.readExcel(Constant.excel_topapps, 0);
			for (int i = 0; i < reportInfos.size(); i++) {
				boolean flag = false;
				for (int j = 0; j < sheet.getRows(); j++) {
					Cell[] cell = sheet.getRow(j);
					if (type) {
						// 1.根据python生成的报告，从而获取apk中文名
						if (cell[3].getContents().trim().equals(reportInfos.get(i).getpName())) {
							reportInfos.get(i).setChName(cell[2].getContents().trim());
							flag = true;
							break;
						}
					} else {
						// 2.根据excel表报告，从而获取：编号，包名
						if (cell[2].getContents().trim().equals(reportInfos.get(i).getChName())) {
							reportInfos.get(i).setpName(cell[3].getContents().trim());
							reportInfos.get(i).setSn(j + 1);
							flag = true;
							break;
						}
					}
				}
				if (!flag) {
					System.out.println("不存在(移除，避免排序错误)：" + reportInfos.get(i).toString());
					reportInfos.remove(i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取python写入的报告，得出类ReportInfo的属性（编号，包名，apk中文名，apk执行信息）
	 */
	public List<ReportInfo> getPythonInfo(String readFile) {
		List<String> apkName = new ArrayList<String>();
		List<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
		readFromFile.readFile(readFile, apkName);
		String strSplit = apkName.get(0).substring(apkName.get(0).indexOf(":"), apkName.get(0).lastIndexOf("/") + 1);
		for (String an : apkName) {
			ReportInfo reportInfo = new ReportInfo();
			String re[] = an.split(strSplit);
			reportInfo.setSn(Integer.parseInt(re[1].split("_")[0]));
			reportInfo.setpName(re[1].substring(re[1].indexOf("_") + 1, re[1].lastIndexOf(".apk")));
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
			reportInfo.setApkInfo(re[0]);
			reportInfos.add(reportInfo);
		}
		getReportAllInfo(reportInfos, true);
		Collections.sort(reportInfos);
		// for (ReportInfo re : reportInfos)
		// System.out.println(re.toString());
		return reportInfos;
	}

	/**
	 * 读取excle报告，得出类ReportInfo的属性（编号，包名，apk中文名，apk执行信息）
	 */
	public List<ReportInfo> getExcelInfo() {
		List<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
		Sheet sheet = excelHandle.readExcel(Constant.fold_TextExcel + "flyme4_liuwen.xls", 0);
		for (int j = 0; j < sheet.getRows(); j++) {
			ReportInfo reportInfo = new ReportInfo();
			Cell[] cell = sheet.getRow(j);
			reportInfo.setChName(cell[2].getContents().trim());
			reportInfo.setApkInfo(cell[3].getContents().trim());
			reportInfos.add(reportInfo);
		}
		getReportAllInfo(reportInfos, false);
		Collections.sort(reportInfos);
		return reportInfos;
	}

	public void excelToList(Sheet sheet, List<ReportInfo> Infos) {
		for (int i = 0; i < sheet.getRows(); i++) {
			Cell[] cell = sheet.getRow(i);
			ReportInfo reportInfo = new ReportInfo();
			reportInfo.setSn(Integer.parseInt(cell[0].getContents().trim()));
			reportInfo.setChName(cell[1].getContents().trim());
			reportInfo.setpName(cell[2].getContents().trim());
			reportInfo.setApkInfo(cell[3].getContents().trim());
			Infos.add(reportInfo);
		}
	}

	public void genReport(String excelReport, String pyReport, String writeReport) {
		List<ReportInfo> excelInfos = new ArrayList<ReportInfo>();
		List<ReportInfo> pyInfos = new ArrayList<ReportInfo>();
		Sheet excelSheet = excelHandle.readExcel(excelReport, 0);
		Sheet pySheet = excelHandle.readExcel(pyReport, 0);
		this.excelToList(excelSheet, excelInfos);
		this.excelToList(pySheet, pyInfos);
		for (int i = 0; i < pyInfos.size(); i++) {
			for (int j = 0; j < excelInfos.size(); j++) {
				if (pyInfos.get(i).getpName().equals(excelInfos.get(j).getpName())) {
					pyInfos.get(i).setApkInfo(pyInfos.get(i).getApkInfo() + "&~&" + excelInfos.get(j).getApkInfo());
					break;
				}
			}
		}
		// excelHandle.writePyExcel(pyInfos, writeReport, 0);
	}

	public static void main(String[] args) {
		String readFileName = "m85_ioFail";
		PyReportHandle pyReportHandle = new PyReportHandle();
		List<ReportInfo> pyReport = pyReportHandle.getPythonInfo(Constant.fold_TextExcel + readFileName);
//		List<ReportInfo> excelReport = pyReportHandle.getExcelInfo();
		// 生成报告
		ExcelHandle.writePyExcel(pyReport, Constant.fold_TextExcel + readFileName + ".xls", 0);
//		ExcelHandle.writePyExcel(excelReport, Constant.fold_TextExcel + "report2.xls", 1);
		pyReportHandle.genReport(Constant.fold_TextExcel + "flyme5_report.xls", Constant.fold_TextExcel + "flyme5_htc_lijie_e.xls", Constant.fold_TextExcel
				+ "flyme5_htc_report.xls");

	}
}
