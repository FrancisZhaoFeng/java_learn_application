package com.meizu.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import contants.Constant;

public class Tools {
	public static void main(String[] args) {
		Tools tools = new Tools();
		System.out.println("命令执行例子：java -classpath 3w_app.jar com.meizu.tools.Tools M85_20160118011047");
		if (args.length == 0) {
			Constant.changeMobileVersion("M80_testversion");
		} else {
			Constant.changeMobileVersion(args[0]);
		}
		tools.genServerFold();
	}

	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = df.format(new Date());
		System.out.println(date);// new Date()为获取当前系统时间
		return date;
	}

	public static String getYDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return yesterday.trim();
	}

	public static void genLocalFold() {
		Tools.generateFold(Constant.localPath);

		Tools.generateFold(Constant.fold_MeizuAutoTest);
		Tools.generateFold(Constant.fold_analysisFail);
		Tools.generateFold(Constant.fold_downloadFail);
		Tools.generateFold(Constant.fold_installFail);
		Tools.generateFold(Constant.fold_openFail);
		Tools.generateFold(Constant.fold_allFail);
		Tools.generateFold(Constant.fold_crashFail);
		Tools.generateFold(Constant.fold_TextExcel);
		Tools.generateFold(Constant.fold_secondRun);
		// 二轮测试
		Tools.generateFold(Constant.fold_srMeizuAutoTest);
	}

	public void genServerFold() {
		// 版本中
		Tools.generateFold(Constant.serverMobileVersionPath127);
		// 主目录
		Tools.generateFold(Constant.serverFristPath127);
		Tools.generateFold(Constant.serverSecondPath127);
		Tools.generateFold(Constant.fold_finalTestReport);
		Tools.generateFold(Constant.fold_comFail);
		// 第一轮
		Tools.generateFold(Constant.fold_sF_allFailApk);
		Tools.generateFold(Constant.fold_sF_installFailLog);
		Tools.generateFold(Constant.fold_sF_openFailLog);
		Tools.generateFold(Constant.fold_sF_testReport);
		// 第二轮
		Tools.generateFold(Constant.fold_sS_installFailLog);
		Tools.generateFold(Constant.fold_sS_openFailLog);
		Tools.generateFold(Constant.fold_sS_testReport);
		Tools.generateFold(Constant.fold_sS_data);
		// 汇总
		Tools.generateFold(Constant.fold_final_failLog);
		Tools.generateFold(Constant.fold_final_failApk);
		Tools.generateFold(Constant.fold_final_others);
		// 汇总 apk
		Tools.generateFold(Constant.fold_final_a_installFailApk);
		Tools.generateFold(Constant.fold_final_a_openFailApk);
		Tools.generateFold(Constant.fold_final_a_crashFailApk);
		Tools.generateFold(Constant.fold_final_l_systemFail);
		// 汇总 log
		Tools.generateFold(Constant.fold_final_l_installFailLog);
		Tools.generateFold(Constant.fold_final_l_installFailComLog);
		Tools.generateFold(Constant.fold_final_l_openFailLog);
		Tools.generateFold(Constant.fold_final_l_openFailComLog);
		Tools.generateFold(Constant.fold_final_l_crashFailLog);
		Tools.generateFold(Constant.fold_final_l_crashFailComLog);
		System.out.println("生成文件夹成功");
	}

	public static void generateFold(String filePath) {
		System.out.println("路径:" + filePath);
		File headPath = new File(filePath);// 获取文件夹路径
		if (!headPath.exists()) {// 判断文件夹是否创建，没有创建则创建新文件夹
			if (!filePath.contains("excel")) {
				headPath.mkdirs();
			} else {
				try {
					headPath.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
