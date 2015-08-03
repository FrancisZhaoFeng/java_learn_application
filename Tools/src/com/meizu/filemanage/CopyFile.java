package com.meizu.filemanage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyFile {
	public static void main(String[] args) {
		// 初始化变量
		String serverPath = "\\\\172.16.152.206\\applist\\newapps\\";
		String openFailPath = "E:\\w_openFailAPK\\";
		String installFailPath = "E:\\w_installFailAPK\\";
		String downloadFailPath = "E:\\w_downloadFailAPK\\";
		String openFailText = "c:/w_openFail.txt";
		String installFailText = "c:/w_installFail.txt";
		String downloadFailText = "c:/w_downloadFail.txt";
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> openFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();

		ReadFromFile.getFileList(serverPath, lFileName, ".apk");
		ReadFromFile.readFileByLines(openFailText, openFailName);
		ReadFromFile.readFileByLines(installFailText, installFailName);
		ReadFromFile.readFileByLines(downloadFailText, downloadFailName);

		for (ApkName an : lFileName) {
			String lFile = an.getName();
			int ltemp = Integer.parseInt(lFile.substring(0, lFile.indexOf("_")));
			for (int i = 0; i < downloadFailName.size(); i++) {
				ApkName dan = downloadFailName.get(i);
				int name = dan.getSn();
				if (ltemp == name) {
					dan.setName(lFile);
					downloadFailName.set(i, dan);
					System.out.println(lFile);
				}
			}
		}
		for (int i = 0; i < downloadFailName.size(); i++) {
			if (!downloadFailName.get(i).getName().contains(".")) {
				downloadFailName.remove(i);
				System.out.println(downloadFailName.get(i).getSn());
			}
		}
		CopyFile copyFile = new CopyFile();
		copyFile.copyApk(serverPath, openFailPath, openFailName);
		copyFile.copyApk(serverPath, installFailPath, installFailName);
		copyFile.copyApk(serverPath, downloadFailPath, downloadFailName);
	}

	/**
	 * 复制打开和下载失败的apk
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public void copyApk(String serverPath, String odFailPath, List<ApkName> odFailName) {
		try {
			for (ApkName an : odFailName) {
				FileInputStream in = new java.io.FileInputStream(serverPath + an.getName());
				FileOutputStream out = new FileOutputStream(odFailPath + an.getName());
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
	}
}
