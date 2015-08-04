package com.meizu.filemanage;

import com.meizu.tools.Tools;

public class Constant {
	public static final String localPath = "E:\\3W_Apps\\" + Tools.getDate() + "\\";
	public static final String serverPath = "\\\\172.16.152.206\\applist\\newapps\\";
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
}
