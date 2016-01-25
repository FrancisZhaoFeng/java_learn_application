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
	public static final String excel_analysisFail = fold_TextExcel + "excel_analysisFail.xls";
	public static final String excel_inexitApk = fold_TextExcel + "excel_inexitApk.xls";
	public static final String excel_topapps = "E:\\3W_Apps\\top30000.xls";
	// 二轮测试
	public static final String fold_srMeizuAutoTest = fold_secondRun + "fold_srMeizuAutoTest\\";
	public static final String txt_srPackageError = fold_secondRun + "txt_srPackageError.txt";
	public static final String txt_srInstallFail = fold_secondRun + "txt_srInstallFail.txt";
	public static final String txt_srOpenFail = fold_secondRun + "txt_srOpenFail.txt";
	public static final String excel_srPackageError = fold_secondRun + "excel_srPackageError.xls";
	// 获取测试apk编号最小值
	public static final String localYesPath = "E:\\3W_Apps\\" + Tools.getYDate() + "\\fold_TextExcel\\txt_installSuccess.txt";

	// apptest 127服务器路径
	// public static final String server = "D:\\";
	public static final String server = "\\\\172.16.11.127\\";
	// public static final String mobileVersion = "M85_20160118011047";
	public static final String mobileVersion = "balabala";
	public static final String serverApkPath127 = server + "app\\";
	public static final String serverMobileVersionPath127 = server + "3w_app报告\\" + mobileVersion + "\\";
	public static final String serverFristPath127 = serverMobileVersionPath127 + "第一轮测试\\";
	public static final String serverSecondPath127 = serverMobileVersionPath127 + "第二轮测试\\";
	public static final String fold_finalTestReport = serverMobileVersionPath127 + "测试结果汇总\\";
	// apptest 文件夹第一轮
	public static final String fold_sF_allFailApk = serverFristPath127 + "allFailApk\\";
	public static final String fold_sF_openFailLog = serverFristPath127 + "openFailLog\\";
	public static final String fold_sF_installFailLog = serverFristPath127 + "installFailLog\\";
	public static final String fold_sF_testReport = serverFristPath127 + "testReport\\";
	// apptest 文件夹第二轮
	public static final String fold_sS_openFailLog = serverSecondPath127 + "openFailLog\\";
	public static final String fold_sS_installFailLog = serverSecondPath127 + "installFailLog\\";
	public static final String fold_sS_testReport = serverSecondPath127 + "testReport\\";
	public static final String fold_sS_data = serverSecondPath127 + "统计数据\\";
	// apptest 汇总
	public static final String fold_final_installFail = fold_finalTestReport + "installFail\\";
	public static final String fold_final_openFail = fold_finalTestReport + "openFail\\";
	public static final String fold_final_crashFail = fold_finalTestReport + "crashFail\\";
	public static final String fold_final_comFail = fold_finalTestReport + "对比机型\\";
	// apptest 汇总 apk及log路劲
	public static final String fold_final_installFailApk = fold_final_installFail + "installFailApk\\";
	public static final String fold_final_installFailLog = fold_final_installFail + "installFailLog\\";
	public static final String fold_final_openFailApk = fold_final_openFail + "openFailApk\\";
	public static final String fold_final_openFailLog = fold_final_openFail + "openFailLog\\";
	public static final String fold_final_crashFailApk = fold_final_crashFail + "crashFailApk\\";
	public static final String fold_final_crashFailLog = fold_final_crashFail + "crashFailLog\\";
	// txt文件
	public static final String txt_sS_installSuccess = fold_sS_data + "txt_installSuccess.txt";
	public static final String txt_sS_installFail = fold_sS_data + "txt_installFail.txt";
	public static final String txt_sS_openFail = fold_sS_data + "txt_openFail.txt";
	public static final String txt_sS_downloadFail = fold_sS_data + "txt_downloadFail.txt";
	public static final String txt_sS_inexitApk = fold_sS_data + "txt_inexitApk.txt";
	public static final String txt_sS_analysisFail = fold_sS_data + "txt_analysisFail.txt";
	public static final String txt_sS_crashFail = fold_sS_data + "txt_crashFail.txt";
	// apptest客户端，通过list保存报告数据
	public static final List<ApkName> allApkName = new ArrayList<ApkName>();
	public static final List<ApkName> analysisFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> installFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> openFApkName = new ArrayList<ApkName>();
	public static final List<ApkName> crashApkName = new ArrayList<ApkName>();

}
