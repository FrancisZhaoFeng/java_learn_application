package com.meizu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.Constant;
import com.meizu.tools.ReadFromFile;
import com.meizu.util.ApkUtil;

public class Main {

	public static void main(String args[]) {
		ReadFromFile readFromFile = new ReadFromFile();
		ApkUtil apkUtil = new ApkUtil();
		String apkPath = "C:\\Users\\zhaoguofeng\\Desktop\\解析失败\\";
		// String apkPath = "\\\\172.16.11.127\\解析失败\\";
		// String apkPath = "\\\\172.16.11.127\\FailApp\\";
		// String apkPath = Constant.serverApkPath127;

		List<ApkName> listApkNames = new ArrayList<ApkName>();
		ReadFromFile.getApkList(apkPath, listApkNames, ".apk");
		Collections.sort(listApkNames);
		int num = 0;
		for (ApkName apkName : listApkNames) {
			System.out.println(apkName.getfName() + ",====," + apkUtil.getApkInfo(apkPath + apkName.getfName()).toString());
			System.out.println();
			// if (apkUtil.getApkInfo(apkPath + apkName.getfName()).getVersionCode() == null) {// !apkUtil.apkUsable(apkPath + apkName.getfName())
			// System.out.println(apkName.getfName());
			// // Main.deleteFile(apkPath + apkName.getfName());
			// num++;
			// }
		}
		System.out.println("finish~~~~,error size:" + num);
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

}
