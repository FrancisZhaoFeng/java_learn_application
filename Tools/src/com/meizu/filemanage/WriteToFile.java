package com.meizu.filemanage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteToFile {

	public static void writeToText(List<ApkName> apkName) {
		String installSuccess = "c:\\installSuccess.txt";
		String installFail = "c:\\installFail.txt";
		String odFail = "c:\\odFail.txt";
		FileOutputStream fosSussess;
		FileOutputStream fosFail;
		FileOutputStream fosODFail;
		int[] apkSize = new int[99999999];
		int maxIndex = 0, minIndex = 99999999;
		try {
			fosSussess = new FileOutputStream(installSuccess);
			fosFail = new FileOutputStream(installFail);
			fosODFail = new FileOutputStream(odFail);
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
					fosODFail.write(apkPName.getBytes());
				}
			}
			for (int i = minIndex; i <= maxIndex; i++) {
				if (apkSize[i] == 0) {
					String sn = "" + i + "_\r\n";
					fosODFail.write(sn.getBytes());
				}
			}
			System.out.println(maxIndex);
			fosSussess.flush();
			fosSussess.close();
			fosFail.flush();
			fosFail.close();
			fosODFail.flush();
			fosODFail.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
