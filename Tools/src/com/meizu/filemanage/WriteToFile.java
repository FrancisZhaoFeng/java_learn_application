package com.meizu.filemanage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WriteToFile {

	/**
	 * @param apkName
	 * @param num
	 * @return 根据num值，执行第一次或第二次报告统计
	 */
	public static String writeRun(List<ApkName> apkName, int num) {
		if (num == 1)
			return WriteToFile.writeFristRun(apkName);
		else
			return WriteToFile.writeSecondRun(apkName);
	}

	/**
	 * @param apkName
	 * @return 第一次报告统计，生成txt文件数为5及过滤关键词：安装打开成功（不包含：AppTestCase），安装失败(APK安装失败)，打开失败(打开失败)，下载失败()，服务器不存在的apk
	 */
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
		Collections.sort(lFileName);
		try {
			fosInstallSussess = new FileOutputStream(Constant.txt_installSuccess);
			fosInstallFail = new FileOutputStream(Constant.txt_installFail);
			fosOpenFail = new FileOutputStream(Constant.txt_openFail);
			fosDownloadFail = new FileOutputStream(Constant.txt_downloadFail);
			fosInexitApk = new FileOutputStream(Constant.txt_inexitApk);
			for (ApkName an : apkName) {
				String name = an.getName();
				if (maxIndex < an.getSn()) {
					System.out.println("最大apk记录：" + an.getName());
					maxIndex = an.getSn();
				}
				if (minIndex > an.getSn()) {
					System.out.println("最小apk记录：" + an.getName());
					minIndex = an.getSn();
				}
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
					for (int j = minIndex; j <= maxIndex; j++) {
						ApkName an = lFileName.get(j);
						if (an.getSn() == i) {
							fosDownloadFail.write((an.getName() + "\r\n").getBytes());
							downloadFailName.add(an);
							flag = true;
							break;
						}
						if (an.getSn() > i)
							break;
					}
					if (!flag)
						fosInexitApk.write(("" + i + "_\r\n").getBytes()); // 记录服务器不存在的apk
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

	/**
	 * @param apkName
	 * @return 第一次报告统计，生成txt文件数为3及过滤关键词：安装包解析错误（解析错误），安装失败(APK安装失败)，打开失败(打开失败)
	 */
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
					int index = 0;
					String apkPName;
					if (name.contains("-->")) {
						apkPName = name.substring(index, name.indexOf("--->")) + "\r\n";
					} else {
						apkPName = name.substring(index, name.indexOf("apk") + 3) + "\r\n";
					}
					fosPackageError.write(apkPName.getBytes());
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

	/**
	 * 服务器找到不存在的apk
	 */
	public static void serverFindInexid() {
		List<ApkName> apkName = new ArrayList<ApkName>();
		List<ApkName> inexitName = new ArrayList<ApkName>();
		ReadFromFile.getFileList(Constant.serverPath, apkName, "apk");
		int maxIndex = 0;
		int[] lApk = new int[9999999];
		for (ApkName an : apkName) {
			lApk[an.getSn()] = 1;
			if (maxIndex < an.getSn())
				maxIndex = an.getSn();
		}
		for (int i = 1; i <= maxIndex; i++) {
			if (lApk[i] == 0) {
				ApkName an = new ApkName();
				an.setName(i + "_");
				inexitName.add(an);
			}
		}
		ReadFromFile.writeFileByLines("d:\\temp.txt", inexitName);
		System.out.print("已获取服务器不存在的id：d:\\temp.txt");
	}

	public static void main(String[] args) {
		// WriteToFile.serverFindInexid();
		// ExcelHandle.snFindID(Constant.excel_topapps, "d:\\temp.txt", 1);
	}
}
