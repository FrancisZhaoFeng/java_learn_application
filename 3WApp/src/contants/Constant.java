package contants;

import java.util.ArrayList;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.Tools;

public class Constant {
	public static final String localPath = "E:\\3W_Apps\\" + Tools.getDate() + "\\";
	public static final String serverPath = "\\\\172.16.11.206\\applist\\newapps\\";
	// 文件夹
	public static final String fold_installFail = localPath + "fold_installFail\\";
	public static final String fold_openFail = localPath + "fold_openFail\\";
	public static final String fold_analysisFail = localPath + "fold_analysisFail\\";
	public static final String fold_downloadFail = localPath + "fold_downloadFail\\";
	public static final String fold_allFail = localPath + "fold_allFail\\";
	public static final String fold_crashFail = localPath + "fold_crashFail\\";
	public static final String fold_TextExcel = localPath + "fold_TextExcel\\";
	public static final String fold_MeizuAutoTest = localPath + "fold_MeizuAutoTest\\";
	public static final String fold_secondRun = localPath + "fold_secondRun\\";
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
	public static final String excel_crashFail = fold_TextExcel + "excel_crashFail.xls";
	public static final String excel_analysisFail = fold_TextExcel + "excel_analysisFail.xls";
	public static final String excel_inexitApk = fold_TextExcel + "excel_inexitApk.xls";
	public static final String excel_topapps = "E:\\3W_Apps\\top3w+.xls";

	// 二轮测试
	public static final String fold_srMeizuAutoTest = fold_secondRun + "fold_srMeizuAutoTest\\";
	public static final String txt_srPackageError = fold_secondRun + "txt_srPackageError.txt";
	public static final String txt_srInstallFail = fold_secondRun + "txt_srInstallFail.txt";
	public static final String txt_srOpenFail = fold_secondRun + "txt_srOpenFail.txt";
	public static final String excel_srPackageError = fold_secondRun + "excel_srPackageError.xls";
	// 获取测试apk编号最小值
	public static final String localYesPath = "E:\\3W_Apps\\" + Tools.getYDate() + "\\fold_TextExcel\\txt_installSuccess.txt";

	public static void changeMobileVersion(String mobile) {
		serverMobileVersionPath127 = serverMobileVersionPath127.replace(mobileVersion, mobile);
		serverFristPath127 = serverFristPath127.replace(mobileVersion, mobile);
		serverSecondPath127 = serverSecondPath127.replace(mobileVersion, mobile);
		fold_finalTestReport = fold_finalTestReport.replace(mobileVersion, mobile);
		fold_comFail = fold_comFail.replace(mobileVersion, mobile);
		// apptest 文件夹第一轮
		fold_sF_allFailApk = fold_sF_allFailApk.replace(mobileVersion, mobile);
		fold_sF_openFailLog = fold_sF_openFailLog.replace(mobileVersion, mobile);
		fold_sF_installFailLog = fold_sF_installFailLog.replace(mobileVersion, mobile);
		fold_sF_testReport = fold_sF_testReport.replace(mobileVersion, mobile);
		// apptest 文件夹第二轮
		fold_sS_openFailLog = fold_sS_openFailLog.replace(mobileVersion, mobile);
		fold_sS_installFailLog = fold_sS_installFailLog.replace(mobileVersion, mobile);
		fold_sS_testReport = fold_sS_testReport.replace(mobileVersion, mobile);
		fold_sS_data = fold_sS_data.replace(mobileVersion, mobile);
		// apptest 汇总.replace(mobileVersion, mobile);
		fold_final_failLog = fold_final_failLog.replace(mobileVersion, mobile);
		fold_final_failApk = fold_final_failApk.replace(mobileVersion, mobile);
		fold_final_others = fold_final_others.replace(mobileVersion, mobile);
		// apptest 汇总 log
		fold_final_l_installFail = fold_final_l_installFail.replace(mobileVersion, mobile);
		fold_final_l_openFail = fold_final_l_openFail.replace(mobileVersion, mobile);
		fold_final_l_crashFail = fold_final_l_crashFail.replace(mobileVersion, mobile);
		fold_final_l_systemFail = fold_final_l_systemFail.replace(mobileVersion, mobile);
		// apptest 汇总 apk
		fold_final_a_installFailApk = fold_final_a_installFailApk.replace(mobileVersion, mobile);
		fold_final_a_openFailApk = fold_final_a_openFailApk.replace(mobileVersion, mobile);
		fold_final_a_crashFailApk = fold_final_a_crashFailApk.replace(mobileVersion, mobile);
		// apptest 汇总log包括对比机型
		fold_final_l_installFailLog = fold_final_l_installFailLog.replace(mobileVersion, mobile);
		fold_final_l_installFailComLog = fold_final_l_installFailComLog.replace(mobileVersion, mobile);
		fold_final_l_openFailLog = fold_final_l_openFailLog.replace(mobileVersion, mobile);
		fold_final_l_openFailComLog = fold_final_l_openFailComLog.replace(mobileVersion, mobile);
		fold_final_l_crashFailLog = fold_final_l_crashFailLog.replace(mobileVersion, mobile);
		fold_final_l_crashFailComLog = fold_final_l_crashFailComLog.replace(mobileVersion, mobile);
		// txt文件
		txt_sS_installSuccess = txt_sS_installSuccess.replace(mobileVersion, mobile);
		txt_sS_installFail = txt_sS_installFail.replace(mobileVersion, mobile);
		txt_sS_openFail = txt_sS_openFail.replace(mobileVersion, mobile);
		txt_sS_downloadFail = txt_sS_downloadFail.replace(mobileVersion, mobile);
		txt_sS_inexitApk = txt_sS_inexitApk.replace(mobileVersion, mobile);
		txt_sS_analysisFail = txt_sS_analysisFail.replace(mobileVersion, mobile);
		txt_sS_crashFail = txt_sS_crashFail.replace(mobileVersion, mobile);
	}

	// apptest 127服务器路径
	public static final String server = "D:\\";
	public static String mobileVersion = "meizu";
	public static String serverApkPath127 = server + "app\\";
	// 需要mobile
	public static String serverMobileVersionPath127 = server + "3w_app报告\\" + mobileVersion + "\\";//
	public static String serverFristPath127 = serverMobileVersionPath127 + "第一轮测试\\";
	public static String serverSecondPath127 = serverMobileVersionPath127 + "第二轮测试\\";
	public static String fold_finalTestReport = serverMobileVersionPath127 + "测试结果汇总\\";
	public static String fold_comFail = serverMobileVersionPath127 + "机型对比\\";
	// apptest 文件夹第一轮
	public static String fold_sF_allFailApk = serverFristPath127 + "allFailApk\\";
	public static String fold_sF_openFailLog = serverFristPath127 + "openFailLog\\";
	public static String fold_sF_installFailLog = serverFristPath127 + "installFailLog\\";
	public static String fold_sF_testReport = serverFristPath127 + "testReport\\";
	// apptest 文件夹第二轮
	public static String fold_sS_openFailLog = serverSecondPath127 + "openFailLog\\";
	public static String fold_sS_installFailLog = serverSecondPath127 + "installFailLog\\";
	public static String fold_sS_testReport = serverSecondPath127 + "testReport\\";
	public static String fold_sS_data = serverSecondPath127 + "统计数据\\";
	// apptest 汇总
	public static String fold_final_failLog = fold_finalTestReport + "failLog\\";
	public static String fold_final_failApk = fold_finalTestReport + "failApk\\";
	public static String fold_final_others = fold_finalTestReport + "others\\";
	// apptest 汇总 log
	public static String fold_final_l_installFail = fold_final_failLog + "installFail\\";
	public static String fold_final_l_openFail = fold_final_failLog + "openFail\\";
	public static String fold_final_l_crashFail = fold_final_failLog + "crashFail\\";
	public static String fold_final_l_systemFail = fold_final_failLog + "systemFail\\";
	// apptest 汇总 apk
	public static String fold_final_a_installFailApk = fold_final_failApk + "installFailApk\\";
	public static String fold_final_a_openFailApk = fold_final_failApk + "openFailApk\\";
	public static String fold_final_a_crashFailApk = fold_final_failApk + "crashFailApk\\";
	// apptest 汇总log包括对比机型
	public static String fold_final_l_installFailLog = fold_final_l_installFail + "installFailLog\\";
	public static String fold_final_l_installFailComLog = fold_final_l_installFail + "comInstallFailLog\\";
	public static String fold_final_l_openFailLog = fold_final_l_openFail + "openFailLog\\";
	public static String fold_final_l_openFailComLog = fold_final_l_openFail + "comOpenFailLog\\";
	public static String fold_final_l_crashFailLog = fold_final_l_crashFail + "crashFailLog\\";
	public static String fold_final_l_crashFailComLog = fold_final_l_crashFail + "comCrashFailLog\\";

	// txt文件
	public static String txt_sS_installSuccess = fold_sS_data + "txt_installSuccess.txt";
	public static String txt_sS_installFail = fold_sS_data + "txt_installFail.txt";
	public static String txt_sS_openFail = fold_sS_data + "txt_openFail.txt";
	public static String txt_sS_downloadFail = fold_sS_data + "txt_downloadFail.txt";
	public static String txt_sS_inexitApk = fold_sS_data + "txt_inexitApk.txt";
	public static String txt_sS_analysisFail = fold_sS_data + "txt_analysisFail.txt";
	public static String txt_sS_crashFail = fold_sS_data + "txt_crashFail.txt";
	// 数据统计

	// apptest客户端，通过list保存报告数据
	public static final List<ApkName> allApkName = new ArrayList<ApkName>();
	public static final List<ApkName> analysisFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> installFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> openFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> crashApkName = new ArrayList<ApkName>();

}
