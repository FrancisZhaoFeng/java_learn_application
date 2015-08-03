package com.meizu.filemanage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteToFile {

	public static void writeToText(List<ApkName> apkName) {
		String installSuccess = "c:\\w_installSuccess.txt";
		String installFail = "c:\\w_installFail.txt";
		String openFail = "c:\\w_openFail.txt";
		String downloadFail = "c:\\w_downloadFail.txt";
		FileOutputStream fosSussess;
		FileOutputStream fosFail;
		FileOutputStream fosOpenFail;
		FileOutputStream fosDownloadFail;
		int[] apkSize = new int[99999999];
		int maxIndex = 0, minIndex = 99999999;
		try {
			fosSussess = new FileOutputStream(installSuccess);
			fosFail = new FileOutputStream(installFail);
			fosOpenFail = new FileOutputStream(openFail);
			fosDownloadFail = new FileOutputStream(downloadFail);
			for (ApkName an : apkName) {
				String name = an.getName();
				if (maxIndex < an.getSn())
					maxIndex = an.getSn();
				if (minIndex > an.getSn())
					minIndex = an.getSn();
				apkSize[an.getSn()] = 1;
				name += "\r\n";
				if (!name.contains("AppTestCase")) {
					fosSussess.write(name.getBytes());
				} else if (name.contains("APK安装失败")) {
					int index = 0;
					String apkPName;
					if (name.contains("-->")) {
						apkPName = name.substring(index, name.indexOf("--->")) + "\r\n";
					} else {
						apkPName = name.substring(index, name.indexOf("安装")) + "\r\n";
					}
					fosFail.write(apkPName.getBytes());
				} else if (name.contains("打开失败")) {
					int index = 0;
					String apkPName;
					if (name.contains("-->")) {
						apkPName = name.substring(index, name.indexOf("--->")) + "\r\n";
					} else {
						apkPName = name.substring(index, name.indexOf("安装")) + "\r\n";
					}
					fosOpenFail.write(apkPName.getBytes());
				}
			}
			for (int i = minIndex; i <= maxIndex; i++) {
				if (apkSize[i] == 0) {
					String sn = "" + i + "\r\n";
					fosDownloadFail.write(sn.getBytes());
				}
			}
			System.out.println("最小值："+minIndex + "\n最大值：" + maxIndex);
			fosSussess.flush();
			fosSussess.close();
			fosFail.flush();
			fosFail.close();
			fosOpenFail.flush();
			fosOpenFail.close();
			fosDownloadFail.flush();
			fosDownloadFail.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
