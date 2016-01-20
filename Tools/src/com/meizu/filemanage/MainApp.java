package com.meizu.filemanage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = 1;// 代表1轮测试，还是2轮测试
		// 读取Html报告
		MainApp.genAnalysisList(num);// num是1时执行，生成 解析失败list
		// 根据服务器的安装和打开失败文件夹的log 获取安装和打开失败的apk名字
		ReadFromFile.genAppListFromFolder(num);
		Collections.sort(Constant.allApkName);

		System.out.println("installSize:" + Constant.installFApkName.size() + "，" + Constant.installFApkName.get(0));
		System.out.println("openSize:" + Constant.openFApkName.size() + "，" + Constant.openFApkName.get(0));
		System.out.println("analysisSize:" + Constant.analysisFApkName.size() + "," + Constant.analysisFApkName.get(0));
		System.out.println("allSize:" + Constant.allApkName.size() + "，" + Constant.allApkName.get(0));

		if (num == 1)
			CopyFile.copyApk(Constant.serverApkPath127, Constant.fold_allFail, Constant.allApkName);
		 System.out.println("一轮报告生成完成");
		// if (num == 2) {
		// CopyFile.copyApk(Constant.serverPath, Constant.fold_analysisFail, Constant.analysisFApkName);
		// CopyFile.copyApk(Constant.serverPath, Constant.fold_installFail, Constant.installFApkName);
		// CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, Constant.openFApkName);
		// }
	}

	public static void genAnalysisList(int num) {
		if (num != 1)
			return;
		// 吃时候list
		List<String> lFileNames = new ArrayList<String>();
		// 显示文件内容
		ReadFromFile.getFileListNormal(Constant.fold_sF_testReport, lFileNames, ".html");
		for (String strFileName : lFileNames)
			ReadFromFile.genAppAnalysisListFromHtml(Constant.fold_sF_testReport + strFileName);
		Collections.sort(Constant.analysisFApkName);
		Constant.allApkName.addAll(Constant.analysisFApkName);
	}

}
