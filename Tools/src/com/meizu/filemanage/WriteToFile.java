package com.meizu.filemanage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.meizu.tools.Tools;

public class WriteToFile {

	public static void writeToText(List<ApkName> apkName) {
		Tools.generateFold(Constant.fold_TextExcel);
		FileOutputStream fosInstallSussess;
		FileOutputStream fosInstallFail;
		FileOutputStream fosOpenFail;
		FileOutputStream fosDownloadFail;
		int[] apkSize = new int[99999999];
		int maxIndex = 0, minIndex = 99999999;
		try {
			fosInstallSussess = new FileOutputStream(Constant.txt_installSuccess);
			fosInstallFail = new FileOutputStream(Constant.txt_installFail);
			fosOpenFail = new FileOutputStream(Constant.txt_openFail);
			fosDownloadFail = new FileOutputStream(Constant.txt_downloadFail);
			for (ApkName an : apkName) {
				String name = an.getName();
				if (maxIndex < an.getSn())
					maxIndex = an.getSn();
				if (minIndex > an.getSn())
					minIndex = an.getSn();
				apkSize[an.getSn()] = 1;
				name += "\r\n";
				if (!name.contains("AppTestCase")) {
					fosInstallSussess.write(name.getBytes());
				} else if (name.contains("APK安装失败")) {
					int index = 0;
					String apkPName;
					if (name.contains("-->")) {
						apkPName = name.substring(index, name.indexOf("--->")) + "\r\n";
					} else {
						apkPName = name.substring(index, name.indexOf("apk") + 3) + "\r\n";
					}
					fosInstallFail.write(apkPName.getBytes());
				} else if (name.contains("打开失败")) {
					int index = 0;
					String apkPName;
					if (name.contains("-->")) {
						apkPName = name.substring(index, name.indexOf("--->")) + "\r\n";
					} else {
						apkPName = name.substring(index, name.indexOf("apk") + 3) + "\r\n";
					}
					fosOpenFail.write(apkPName.getBytes());
				}
			}
			for (int i = minIndex; i <= maxIndex; i++) {
				if (apkSize[i] == 0) {
					String sn = "" + i + "_\r\n";
					fosDownloadFail.write(sn.getBytes());
				}
			}
			System.out.println("最小值：" + minIndex + "\n最大值：" + maxIndex);
			fosInstallSussess.flush();
			fosInstallSussess.close();
			fosInstallFail.flush();
			fosInstallFail.close();
			fosOpenFail.flush();
			fosOpenFail.close();
			fosDownloadFail.flush();
			fosDownloadFail.close();
			System.out.println("生成txt文件夹完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
