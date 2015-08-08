package com.meizu.filemanage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CopyFile {
	public static void main(String[] args) {
		CopyFile.copyFile();
	}

	public static void copyFile() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> openFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		// 初始化list
		ReadFromFile.getFileList(Constant.serverPath, lFileName, ".apk");
		ReadFromFile.readFileByLines(Constant.txt_openFail, openFailName);
		ReadFromFile.readFileByLines(Constant.txt_installFail, installFailName);
		ReadFromFile.readFileByLines(Constant.txt_downloadFail, downloadFailName);
		// copy文件
		CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, openFailName);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_installFail, installFailName);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_downloadFail, downloadFailName);
	}

	/**
	 * 复制打开和下载失败的apk
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public static void copyApk(String serverPath, String failPath, List<ApkName> failName) {
		try {
			for (ApkName an : failName) {
				if (!an.getName().contains(".apk")) {
					System.out.println("没有进行copy：" + an.getSn());
					continue;
				}
				FileInputStream in = new java.io.FileInputStream(serverPath + an.getName());
				FileOutputStream out = new FileOutputStream(failPath + an.getName());
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			}
			System.out.println("完成：" + failPath);
		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
	}
}
