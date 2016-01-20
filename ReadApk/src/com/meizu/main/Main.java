package com.meizu.main;

import java.util.ArrayList;
import java.util.List;

import com.meizu.bean.ApkInfo;
import com.meizu.tools.Constant;
import com.meizu.tools.ReadFromFile;
import com.meizu.util.ApkUtil;

public class Main {

	public static void main(String args[]) {
		ReadFromFile readFromFile = new ReadFromFile();
		ApkUtil apkUtil = new ApkUtil();

		List<String> listApkNames = new ArrayList<String>();
		List<ApkInfo> listApkInfos = new ArrayList<ApkInfo>();
		readFromFile.getFileList(Constant.serverApkPath127, listApkNames, "*");// Constant.serverApkPath127
		for (String strApkName : listApkNames) {
			ApkInfo apkInfo = apkUtil.getApkInfo(Constant.serverApkPath127 + strApkName);
			if (apkInfo == null) {
				System.out.println(strApkName);
				listApkInfos.add(apkInfo);
			}
		}
		// apkUtil.getApkInfo("E:\\3W_Apps\\flyme5_M85_iof\\49_com.tencent.qqpimsecure.apk");
		System.out.println("finish~~~~,size:" + listApkInfos.size());
	}
}
