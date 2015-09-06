package com.meizu.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.meizu.filemanage.Constant;

public class Tools {
	public static void main(String[] args) {
		Tools.gAllFold();
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
		// return yesterday.trim();
		return "2015-09-02";
	}

	public static void gAllFold() {
		Tools.generateFold(Constant.localPath);
		Tools.generateFold(Constant.fold_MeizuAutoTest);
		Tools.generateFold(Constant.fold_downloadFail);
		Tools.generateFold(Constant.fold_installFail);
		Tools.generateFold(Constant.fold_openFail);
		Tools.generateFold(Constant.fold_TextExcel);
		// Tools.generateFold(Constant.excel_downloadFail);
		// Tools.generateFold(Constant.excel_installFail);
		// Tools.generateFold(Constant.excel_openFail);
		// 二轮测试
		Tools.generateFold(Constant.fold_secondRun);
		Tools.generateFold(Constant.fold_srMeizuAutoTest);
		// Tools.generateFold(Constant.excel_srPackageError);
	}

	public static void generateFold(String filePath) {
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
