package com.meizu.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.CopyFile;
import com.meizu.tools.ReadFromFile;
import com.meizu.tools.WriteToFile;

import contants.Constant;

public class MainApp {
	private static String installFailPath = "", openFailPath = "", testReport = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("命令执行例子：java -classpath 3w_app.jar com.meizu.main.MainApp M85_20160118011047 1 [apk path(default:d:\\app\\)]");
		Constant.changeMobileVersion(args[0]);
		int num = Integer.parseInt(args[1]);// 代表1轮测试，还是2轮测试
		if (args.length == 3) {
			Constant.serverApkPath127 = args[2];
		}
		// 调试
//		 Constant.changeMobileVersion("M86_20160406015109");
//		 int num = 2;
//		 Constant.serverApkPath127= "\\\\172.16.11.78\\2kapp\\2016-04-06\\";

		init(num);
		MainApp.genListFormHtml(testReport, Constant.analysisFApkName);// 读取Html报告，生成 解析失败list
		// 根据服务器的安装和打开失败文件夹的log 获取安装和打开失败的apk名字
		ReadFromFile.genAppListFromFolder(installFailPath, Constant.installFApkName);
		ReadFromFile.genAppListFromFolder(openFailPath, Constant.openFApkName);
		Collections.sort(Constant.allApkName);

		System.out.println("installFailSize:" + Constant.installFApkName.size());
		System.out.println("openFailSize:" + Constant.openFApkName.size());
		System.out.println("analysisFailSize:" + Constant.analysisFApkName.size());
		System.out.println("allSize:" + Constant.allApkName.size());

		switch (num) {
		case 1:
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_sF_allFailApk, Constant.allApkName, false);
			System.out.println(Constant.analysisFApkName);
			System.out.println("一轮报告生成完成,copy apk 完成");
			break;
		case 2:
			ReadFromFile.genAppListFromCrashLog(Constant.fold_final_l_crashFailLog, Constant.crashApkName);
			// 第二轮写入txt
			WriteToFile.writeSecondRunApptest(Constant.txt_sS_installFail, Constant.installFApkName);
			WriteToFile.writeSecondRunApptest(Constant.txt_sS_openFail, Constant.openFApkName);
			WriteToFile.writeSecondRunApptest(Constant.txt_sS_crashFail, Constant.crashApkName);
			WriteToFile.writeSecondRunApptest(Constant.txt_sS_analysisFail, Constant.analysisFApkName);
			// 第二轮下载apk
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_a_installFailApk, Constant.installFApkName, false);
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_a_openFailApk, Constant.openFApkName, false);
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_final_a_crashFailApk, Constant.crashApkName, false);
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
		CopyFile.copyApk(Constant.serverApkPath127, endPath, residueApk, false);
	}

	public static void genListFormHtml(String testReport, List<ApkName> apkNames) {
		List<String> lFileNames = new ArrayList<String>();
		// 显示文件内容
		ReadFromFile.getFileListNormal(testReport, lFileNames, ".html");
		System.out.println("TestReport.html 报告个数：" + lFileNames.size());
		for (String strFileName : lFileNames)
			ReadFromFile.genAppListFromHtml(testReport + strFileName, apkNames);
		Collections.sort(apkNames);
		Constant.allApkName.addAll(apkNames);
	}

}
