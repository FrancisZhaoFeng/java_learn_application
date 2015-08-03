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
		String odFailPath = "E:\\w_odFailAPK\\";
		String installFailPath = "E:\\w_installAPK\\";
		String odFailText = "c:/odFail.txt";
		String installFailText = "c:/installFail.txt";
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<String> lFileName = new ArrayList<String>();
		List<ApkName> odFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();

		ReadFromFile.getFileList(serverPath, lFileName, ".apk");
		ReadFromFile.readFileByLines(odFailText, odFailName);
		ReadFromFile.readFileByLines(installFailText, installFailName);

		for (String lFile : lFileName) {
			int ltemp = Integer.parseInt(lFile.substring(0, lFile.indexOf("_")));
			for (int i = 0; i < odFailName.size(); i++) {
				ApkName an = odFailName.get(i);
				int name = an.getSn();
				if (ltemp == name) {
					an.setName(lFile);
					odFailName.set(i, an);
					System.out.println(lFile);
				}
			}
		}
		CopyFile copyFile = new CopyFile();
		copyFile.copyODFailApk(serverPath, odFailPath, odFailName);
		copyFile.copyInstallFailApk(serverPath, installFailPath, installFailName);

	}

	public void copyODFailApk(String serverPath, String odFailPath, List<ApkName> odFailName) {
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

	public void copyInstallFailApk(String serverPath, String installFailPath, List<ApkName> installFailName) {
		try {
			for (ApkName an : installFailName) {
				FileInputStream in = new java.io.FileInputStream(serverPath + an.getName());
				FileOutputStream out = new FileOutputStream(installFailPath + an.getName());
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
