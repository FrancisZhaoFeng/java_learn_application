package com.meizu.filemanage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {

	public static String writeRun(List<ApkName> apkName, int num) {
		if (num == 1)
			return WriteToFile.writeFristRun(apkName);
		else
			return WriteToFile.writeSecondRun(apkName);
	}

	@SuppressWarnings("resource")
	public static String writeFristRun(List<ApkName> apkName) {
		FileOutputStream fosInstallSussess;
		FileOutputStream fosInstallFail;
		FileOutputStream fosOpenFail;
		FileOutputStream fosDownloadFail;
		FileOutputStream fosInexitApk;
		int[] apkSize = new int[99999999];
		int maxIndex = 0, minIndex = 99999999;
		// 定义list：服务器文件名；下载失败文件名；
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		ReadFromFile.getFileList(Constant.serverPath, lFileName, ".apk");
		try {
			fosInstallSussess = new FileOutputStream(Constant.txt_installSuccess);
			fosInstallFail = new FileOutputStream(Constant.txt_installFail);
			fosOpenFail = new FileOutputStream(Constant.txt_openFail);
			fosDownloadFail = new FileOutputStream(Constant.txt_downloadFail);
			fosInexitApk = new FileOutputStream(Constant.txt_inexitApk);
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
			System.out.println("最小值：" + minIndex + "\n最大值：" + maxIndex + "\n共：" + (maxIndex - minIndex));
			// 将下载失败的与服务器上的apk对比，并已apk名称形式保存
			for (int i = minIndex; i <= maxIndex; i++) {
				boolean flag = false;
				if (apkSize[i] == 0) {
					// 赋值给downloadFailName中的name值（文件名）
					for (ApkName an : lFileName) {
						if (an.getSn() == i) {
							fosDownloadFail.write((an.getName() + "\r\n").getBytes());
							downloadFailName.add(an);
							flag = true;
							break;
						}
					}
					if (!flag)
						fosInexitApk.write(("" + i + "_\r\n").getBytes());
				}
			}
			fosInstallSussess.write(("最小值：" + minIndex + "\n最大值：" + maxIndex + "\n共：" + (maxIndex - minIndex)).getBytes());
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
		return "" + minIndex + "_" + maxIndex;
	}

	public static String writeSecondRun(List<ApkName> apkName) {
		FileOutputStream fosPackageError;
		FileOutputStream fosInstallFail;
		FileOutputStream fosOpenFail;
		int maxIndex = 0, minIndex = 99999999;
		// 定义list：服务器文件名；下载失败文件名；
		List<ApkName> lFileName = new ArrayList<ApkName>();
		ReadFromFile.getFileList(Constant.serverPath, lFileName, ".apk");
		try {
			fosPackageError = new FileOutputStream(Constant.txt_srPackageError);
			fosInstallFail = new FileOutputStream(Constant.txt_srInstallFail);
			fosOpenFail = new FileOutputStream(Constant.txt_srOpenFail);
			for (ApkName an : apkName) {
				if (maxIndex < an.getSn())
					maxIndex = an.getSn();
				if (minIndex > an.getSn())
					minIndex = an.getSn();
				String name = an.getName();
				name += "\r\n";
				if (name.contains("解析错误")) {
					fosPackageError.write(name.getBytes());
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
			fosPackageError.flush();
			fosPackageError.close();
			fosInstallFail.flush();
			fosInstallFail.close();
			fosOpenFail.flush();
			fosOpenFail.close();
			System.out.println("生成txt文件夹完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "" + minIndex + "_" + maxIndex;
	}
}
