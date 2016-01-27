package com.meizu.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.CopyFile;
import com.meizu.tools.ExcelHandle;
import com.meizu.tools.ReadFromFile;
import com.meizu.tools.WriteToFile;

import contants.Constant;

public class MainApp {
	private static String installFailPath = "", openFailPath = "", testReport = "";
	private static ReadFromFile readFromFile = new ReadFromFile();
	private static WriteToFile writeToFile = new WriteToFile();
	private static CopyFile copyFile = new CopyFile();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = 2;// 代表1轮测试，还是2轮测试
		init(num);

		MainApp.genListFormHtml(testReport, Constant.analysisFApkName);// 读取Html报告，生成 解析失败list
		// 根据服务器的安装和打开失败文件夹的log 获取安装和打开失败的apk名字
		readFromFile.genAppListFromFolder(installFailPath, Constant.installFApkName);
		readFromFile.genAppListFromFolder(openFailPath, Constant.openFApkName);
		Collections.sort(Constant.allApkName);

		System.out.println("installFailSize:" + Constant.installFApkName.size() + ",\t第一个apk：" + Constant.installFApkName.get(0));
		System.out.println("openFailSize:" + Constant.openFApkName.size() + ",\t第一个apk：" + Constant.openFApkName.get(0));
		// System.out.println("analysisFailSize:" + Constant.analysisFApkName.size() + ",\t第一个apk：" + Constant.analysisFApkName.get(0));
		System.out.println("allSize:" + Constant.allApkName.size() + ",\t第一个apk：" + Constant.allApkName.get(0));

		switch (num) {
		case 1:
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_sF_allFailApk, Constant.allApkName);
			System.out.println("一轮报告生成完成");
			break;
		case 2:
			readFromFile.genAppListFromCrashLog(Constant.fold_final_crashFailLog, Constant.crashApkName);
			// 第二轮写入txt
			writeToFile.writeSecondRunApptest(Constant.txt_sS_installFail, Constant.installFApkName);
			writeToFile.writeSecondRunApptest(Constant.txt_sS_openFail, Constant.openFApkName);
			writeToFile.writeSecondRunApptest(Constant.txt_sS_crashFail, Constant.crashApkName);
			// writeToFile.writeSecondRunApptest(Constant.txt_sS_analysisFail, Constant.analysisFApkName);
			// 第二轮下载apk
			// CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_installFailApk, Constant.installFApkName);
			// CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_openFailApk, Constant.openFApkName);
			// CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_crashFailApk, Constant.crashApkName);
			// CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_analysisFail, Constant.analysisFApkName);
			System.out.println("二轮报告生成完成");
			break;
		case 3:
			MainApp.copyResidueApk(Constant.fold_sF_allFailApk, Constant.fold_sS_data, Constant.allApkName); // 分段下载apk
			System.out.println("分段下载apk完成");
			break;
		default:
			break;
		}
	}

	public static void init(int num) {
		if (num == 1 || num == 3) {
			installFailPath = Constant.fold_sF_installFailLog;
			openFailPath = Constant.fold_sF_openFailLog;
			testReport = Constant.fold_sF_testReport;
		} else {
			installFailPath = Constant.fold_sS_installFailLog;
			openFailPath = Constant.fold_sS_openFailLog;
			testReport = Constant.fold_sS_testReport;
		}
	}

	public static void copyResidueApk(String srcPath, String endPath, List<ApkName> apkNames) {
		List<String> downloadedApk = new ArrayList<String>();
		List<ApkName> residueApk = new ArrayList<ApkName>();
		ReadFromFile.getFileListNormal(srcPath, downloadedApk, ".apk");
		for (ApkName apkName : apkNames) {
			boolean flag = false;
			for (String strApkName : downloadedApk) {
				if (strApkName.equals(apkName.getfName())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				residueApk.add(apkName);
				System.out.println(apkName.toString());
			}
		}
		CopyFile.copyApk(Constant.serverApkPath127, endPath, residueApk);
	}

	public static void genListFormHtml(String testReport, List<ApkName> apkNames) {
		List<String> lFileNames = new ArrayList<String>();
		// 显示文件内容
		ReadFromFile.getFileListNormal(testReport, lFileNames, ".html");
		System.out.println("TestReport.html 报告个数：" + lFileNames.size());
		for (String strFileName : lFileNames)
			readFromFile.genAppListFromHtml(testReport + strFileName, apkNames);
		Collections.sort(apkNames);
		Constant.allApkName.addAll(apkNames);
	}

}
