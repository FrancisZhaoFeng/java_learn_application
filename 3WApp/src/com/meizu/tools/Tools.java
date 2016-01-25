package com.meizu.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import contants.Constant;

public class Tools {
	public static void main(String[] args) {
		Tools.genServerFold();
		// Tools.genLocalFold();
	}

	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = df.format(new Date());
		System.out.println(date);// new Date()为获取当前系统时间
		return date;
		// return "2016-01-20";
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

	public static void genServerFold() {
		// 版本中
		Tools.generateFold(Constant.serverMobileVersionPath127);
		// 第一轮
		Tools.generateFold(Constant.fold_sF_allFailApk);
		Tools.generateFold(Constant.fold_sF_installFailLog);
		Tools.generateFold(Constant.fold_sF_openFailLog);
		Tools.generateFold(Constant.fold_sF_testReport);
		// 第二轮
		Tools.generateFold(Constant.fold_sS_installFailLog);
		Tools.generateFold(Constant.fold_sS_openFailLog);
		Tools.generateFold(Constant.fold_sS_testReport);
		// 汇总
		Tools.generateFold(Constant.fold_final_installFail);
		Tools.generateFold(Constant.fold_final_openFail);
		Tools.generateFold(Constant.fold_final_crashFail);
		Tools.generateFold(Constant.fold_final_comFail);
		// 汇总 apk及log
		Tools.generateFold(Constant.fold_final_installFailApk);
		Tools.generateFold(Constant.fold_final_installFailLog);
		Tools.generateFold(Constant.fold_final_openFailApk);
		Tools.generateFold(Constant.fold_final_openFailLog);
		Tools.generateFold(Constant.fold_final_crashFailApk);
		Tools.generateFold(Constant.fold_final_crashFailLog);
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
