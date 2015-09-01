package com.meizu.filemanage;

import com.meizu.tools.Tools;

public class Constant {
	public static final String localPath = "E:\\3W_Apps\\" + Tools.getDate() + "\\";
	public static final String serverPath = "\\\\172.16.11.206\\applist\\newapps\\";
	// 文件夹
	public static final String fold_installFail = localPath + "fold_installFail\\";
	public static final String fold_openFail = localPath + "fold_openFail\\";
	public static final String fold_downloadFail = localPath + "fold_downloadFail\\";
	public static final String fold_TextExcel = localPath + "fold_TextExcel\\";
	public static final String fold_MeizuAutoTest = localPath + "fold_MeizuAutoTest\\";
	// txt文件
	public static final String txt_installSuccess = fold_TextExcel + "txt_installSuccess.txt";
	public static final String txt_installFail = fold_TextExcel + "txt_installFail.txt";
	public static final String txt_openFail = fold_TextExcel + "txt_openFail.txt";
	public static final String txt_downloadFail = fold_TextExcel + "txt_downloadFail.txt";
	public static final String txt_inexitApk = fold_TextExcel + "txt_inexitApk.txt";
	// excel文件
	public static final String excel_installFail = fold_TextExcel + "excel_installFail.xls";
	public static final String excel_openFail = fold_TextExcel + "excel_openFail.xls";
	public static final String excel_downloadFail = fold_TextExcel + "excel_downloadFail.xls";
	public static final String excel_topapps = "E:\\3W_Apps\\topapps.xls";
	//二轮测试
	public static final String fold_secondRun = localPath + "fold_secondRun\\";
	public static final String fold_srMeizuAutoTest = fold_secondRun + "fold_srMeizuAutoTest\\";
	public static final String txt_srPackageError = fold_secondRun + "txt_srPackageError.txt";
	public static final String txt_srInstallFail = fold_secondRun + "txt_srInstallFail.txt";
	public static final String txt_srOpenFail = fold_secondRun + "txt_srOpenFail.txt";
	public static final String excel_srPackageError = fold_secondRun + "excel_srPackageError.xls";
	//获取测试apk编号最小值
	public static final String localYesPath = "E:\\3W_Apps\\" + Tools.getYDate() + "\\fold_TextExcel\\txt_installSuccess.txt";
	
}
