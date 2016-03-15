package com.meizu.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;

import contants.Constant;

public class WriteToFile {
//	static WriteToFile writeToFile = new WriteToFile();
//	static ExcelHandle excelHandle = new ExcelHandle();

	/**
	 * @param apkName
	 * @param num
	 * @return 根据num值，执行第一次或第二次报告统计
	 */
	public String writeRun(List<ApkName> apkName, int num) {
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
		FileOutputStream fosInstallSussess = null, fosInstallFail = null, fosOpenFail = null, fosDownloadFail = null, fosInexitApk = null;
		int[] apkSize = new int[99999999];
		int maxIndex = 0, minIndex = 99999999;
		// 定义list：服务器文件名；下载失败文件名；
		List<ApkName> lServerApkName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		ReadFromFile.getApkList(Constant.serverPath, lServerApkName, ".apk");
		Collections.sort(lServerApkName);
		try {
			fosInstallSussess = new FileOutputStream(Constant.txt_installSuccess);
			fosInstallFail = new FileOutputStream(Constant.txt_installFail);
			fosOpenFail = new FileOutputStream(Constant.txt_openFail);
			fosDownloadFail = new FileOutputStream(Constant.txt_downloadFail);
			fosInexitApk = new FileOutputStream(Constant.txt_inexitApk);
			for (ApkName an : apkName) {
				String name = an.getName();
				if (maxIndex < an.getSn()) {
					maxIndex = an.getSn();
				}
				if (minIndex > an.getSn()) {
					minIndex = an.getSn();
				}
				apkSize[an.getSn()] = 1;
				name += "\r\n";
				if (name.contains("安装失败")) {
					int index = 0;
					String apkPName;
					apkPName = name.substring(index, name.indexOf("apk") + 3) + "\r\n";
					fosInstallFail.write(apkPName.getBytes());
				} else if (name.contains("打开失败")) {
					int index = 0;
					String apkPName;
					apkPName = name.substring(index, name.indexOf("apk") + 3) + "\r\n";
					fosOpenFail.write(apkPName.getBytes());
				}
			}
			System.out.println("此次测试最小值：" + minIndex + "\n此次测试最大值：" + maxIndex);
			if (new File(Constant.localYesPath).exists())
				minIndex = ReadFromFile.readFileGetMin(Constant.localYesPath);
			System.out.println("昨天最大值为：" + minIndex + "\n共：" + (maxIndex - minIndex));
			// 将下载失败的与服务器上的apk对比，并已apk名称形式保存
			for (int i = minIndex + 1; i <= maxIndex; i++) {
				boolean flag = false;
				if (apkSize[i] == 0) {
					// 赋值给downloadFailName中的name值（文件名）
					for (int j = minIndex; j <= maxIndex; j++) {
						ApkName an = lServerApkName.get(j);
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
			System.out.println("出现异常-最大apk记录：" + maxIndex);
			System.out.println("出现异常-最小apk记录：" + minIndex);
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
		ReadFromFile.getApkList(Constant.serverPath, lFileName, ".apk");
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

	public static void writeSecondRunApptest(String writePath, List<ApkName> apkNames) {
		FileOutputStream fosFail;
		try {
			fosFail = new FileOutputStream(writePath);
			for (ApkName an : apkNames) {
				String apkPName = an.getSn() + "\t" + an.getName().replace(".apk", "") + "\t" + an.getCrash() + "\r\n";
				fosFail.write(apkPName.getBytes());
			}
			fosFail.flush();
			fosFail.close();
			System.out.println(writePath + " 生成txt文件夹完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 服务器找到不存在的apk
	 */
	public static void serverFindInexid() {
		List<ApkName> apkName = new ArrayList<ApkName>();
		List<ApkName> inexitName = new ArrayList<ApkName>();
		ReadFromFile.getApkList(Constant.serverPath, apkName, "apk");
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
		ReadFromFile.writeFileByLines(Constant.fold_TextExcel + "serverFindInexid.txt", inexitName);
		System.out.print("已获取服务器不存在的id：" + Constant.fold_TextExcel + "serverFindInexid.txt");
	}

	public static void main(String[] args) {
		WriteToFile.serverFindInexid();
		ExcelHandle.snFindID(Constant.excel_topapps, "d:\\temp.txt", 1);
	}
}
