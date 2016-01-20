package com.meizu.tools;


public class Constant {
	public static final String localPath = "E:\\3W_Apps\\" + Tools.getDate() + "\\";
	public static final String serverPath = "\\\\172.16.11.206\\applist\\newapps\\";
	// 文件夹
	public static final String fold_installFail = localPath + "fold_installFail\\";
	public static final String fold_openFail = localPath + "fold_openFail\\";
	public static final String fold_analysisFail = localPath + "fold_fail\\";
	public static final String fold_downloadFail = localPath + "fold_downloadFail\\";
	public static final String fold_allFail = localPath + "fold_allFail\\";
	public static final String fold_TextExcel = localPath + "fold_TextExcel\\";
	public static final String fold_MeizuAutoTest = localPath + "fold_MeizuAutoTest\\";
	// txt文件
	public static final String txt_installSuccess = fold_TextExcel + "txt_installSuccess.txt";
	public static final String txt_installFail = fold_TextExcel + "txt_installFail.txt";
	public static final String txt_openFail = fold_TextExcel + "txt_openFail.txt";
	public static final String txt_downloadFail = fold_TextExcel + "txt_downloadFail.txt";
	public static final String txt_inexitApk = fold_TextExcel + "txt_inexitApk.txt";
	public static final String txt_analysisFail = fold_TextExcel + "txt_analysisFail.txt";
	public static final String txt_crashFail = fold_TextExcel + "txt_crashFail.txt";
	// excel文件
	public static final String excel_installFail = fold_TextExcel + "excel_installFail.xls";
	public static final String excel_openFail = fold_TextExcel + "excel_openFail.xls";
	public static final String excel_downloadFail = fold_TextExcel + "excel_downloadFail.xls";
	public static final String excel_analysisFail = fold_TextExcel + "excel_analysisFail.xls";
	public static final String excel_topapps = "E:\\3W_Apps\\top300001.xls";
	// 二轮测试
	public static final String fold_secondRun = localPath + "fold_secondRun\\";
	public static final String fold_srMeizuAutoTest = fold_secondRun + "fold_srMeizuAutoTest\\";
	public static final String txt_srPackageError = fold_secondRun + "txt_srPackageError.txt";
	public static final String txt_srInstallFail = fold_secondRun + "txt_srInstallFail.txt";
	public static final String txt_srOpenFail = fold_secondRun + "txt_srOpenFail.txt";
	public static final String excel_srPackageError = fold_secondRun + "excel_srPackageError.xls";
	// 获取测试apk编号最小值
	public static final String localYesPath = "E:\\3W_Apps\\" + Tools.getYDate() + "\\fold_TextExcel\\txt_installSuccess.txt";

	// apptest 127服务器路径
	public static final String mobileVersion = "M85_20160118011047";
	public static final String serverApkPath127 = "\\\\172.16.11.127\\app\\";
	public static final String serverFristPath127 = "\\\\172.16.11.127\\3w_app报告\\" + mobileVersion + "\\第一轮测试\\";
	public static final String serverSecondPath127 = "\\\\172.16.11.127\\3w_app报告\\" + mobileVersion + "\\第二轮测试\\";
	// apptest 文件夹第一轮
	public static final String fold_sF_allFailApk = serverFristPath127 + "allFailApk\\";
	public static final String fold_sF_openFailLog = serverFristPath127 + "openFailLog\\";
	public static final String fold_sF_installFailLog = serverFristPath127 + "installFailLog\\";
	public static final String fold_sF_crashFailLog = serverFristPath127 + "crashFailLog\\";
	public static final String fold_sF_testReport = serverFristPath127 + "testReport\\";
	// apptest 文件夹第二轮
	public static final String fold_sS_allFailApk = serverSecondPath127 + "allFailApk\\";
	public static final String fold_sS_openFailLog = serverSecondPath127 + "openFailLog\\";
	public static final String fold_sS_installFailLog = serverSecondPath127 + "installFailLog\\";
	public static final String fold_sS_crashFailLog = serverSecondPath127 + "crashFailLog\\";
	public static final String fold_sS_testReport = serverSecondPath127 + "testReport\\";
	//apptest 文件夹第二轮 allFailApk中
	public static final String fold_sS_installFailApk = fold_sS_allFailApk + "installFailApk\\";
	public static final String fold_sS_openFailApk = fold_sS_allFailApk + "openFailApk\\";
	public static final String fold_sS_crashFailApk = fold_sS_allFailApk + "crashFailApk\\";

}
