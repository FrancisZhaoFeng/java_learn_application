package com.meizu.filemanage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyFile {
	public static void main(String[] args) {
		FileOutputStream fosInexitApk = null;
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

		// 赋值给downloadFailName中的name值（文件名）
		for (ApkName an : lFileName) {
			String lFile = an.getName();
			int ltemp = Integer.parseInt(lFile.substring(0, lFile.indexOf("_")));
			for (int i = 0; i < downloadFailName.size(); i++) {
				ApkName dan = downloadFailName.get(i);
				int name = dan.getSn();
				if (ltemp == name) {
					dan.setName(lFile);
					downloadFailName.set(i, dan);
				}
			}
		}

		try {
			fosInexitApk = new FileOutputStream(Constant.txt_inexitApk);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 有些apk服务器并没有下载成功，故不进行测试
		System.out.print("服务器上没有下载的apk编号：");
		for (int i = 0; i < downloadFailName.size(); i++) {
			if (!downloadFailName.get(i).getName().contains(".")) {
				try {
					fosInexitApk.write((String.valueOf(downloadFailName.get(i).getSn()) + "\r\n").getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print(downloadFailName.get(i).getSn() + "、");
				downloadFailName.remove(i);
			}
		}
		CopyFile copyFile = new CopyFile();
		copyFile.copyApk(Constant.serverPath, Constant.fold_openFail, openFailName);
		copyFile.copyApk(Constant.serverPath, Constant.fold_installFail, installFailName);
		copyFile.copyApk(Constant.serverPath, Constant.fold_downloadFail, downloadFailName);
	}

	/**
	 * 复制打开和下载失败的apk
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public void copyApk(String serverPath, String FailPath, List<ApkName> odFailName) {
		try {
			for (ApkName an : odFailName) {
				FileInputStream in = new java.io.FileInputStream(serverPath + an.getName());
				FileOutputStream out = new FileOutputStream(FailPath + an.getName());
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			}
			System.out.println("完成：" + FailPath);
		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
	}
}
