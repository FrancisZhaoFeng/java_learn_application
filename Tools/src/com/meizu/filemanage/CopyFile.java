package com.meizu.filemanage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoguofeng 根据文件信息，从服务器中copy apk文件到本地目录
 */
public class CopyFile {
	public static void main(String[] args) {
		// CopyFile.copyFile();
		// CopyFile.copyFileTest();
		// CopyFile.copyByNumber();
		// CopyFile.handleReName();
		ReadFromFile.renameFile("E:\\3W_Apps\\temp\\oldtemp\\", "Crash_baidumapsdk.demo222_MA01_20151016160606.png", "123_Crash_baidumapsdk.demo222_MA01_20151016160606.png");
	}

	/**
	 * 根据类WriteToFile中生成的文件：txt_openFail,txt_installFail,txt_downloadFail 的内容 从服务器中copy对应的apk到本地文件夹中
	 */
	public static void copyFile() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> openFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		// 初始化list
		ReadFromFile.getFileList(Constant.serverPath, lFileName, ".apk");
		ReadFromFile.readAppTest(Constant.txt_openFail, openFailName);
		ReadFromFile.readAppTest(Constant.txt_installFail, installFailName);
		ReadFromFile.readAppTest(Constant.txt_downloadFail, downloadFailName);
		// copy文件
		CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, openFailName);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_installFail, installFailName);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_downloadFail, downloadFailName);
	}

	/**
	 * 用于自由的copy文件
	 */
	public static void copyFileTest() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> failName = new ArrayList<ApkName>();
		// 初始化list
		ReadFromFile.getFileList(Constant.serverPath, lFileName, ".apk");
		ReadFromFile.readAppTest("E:\\3W_Apps\\2015-10-15\\fold_TextExcel\\txt_openFail.txt", failName);
		// copy文件
		CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, failName);
	}

	private static void handleReName() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<String> lFileName = new ArrayList<String>();
		// 初始化list
		ReadFromFile.getFileListNormal("E:\\3W_Apps\\temp\\oldtemp", lFileName, "*");
		// copy文件
		CopyFile.copyNormal(Constant.serverPath, Constant.fold_openFail, lFileName);
	}

	/**
	 * 执行复制操作的方法
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public static void copyApk(String serverPath, String failPath, List<ApkName> failName) {
		for (ApkName an : failName) {
			try {
				if (!an.getName().contains(".apk")) {
					System.out.println("没有进行copy：" + an.getSn());
					continue;
				}
				FileInputStream in;
				try {
					in = new java.io.FileInputStream(serverPath + an.getName());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				FileOutputStream out = new FileOutputStream(failPath + an.getName());
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		System.out.println("完成：" + failPath);
	}

	/**
	 * 执行复制操作的方法
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public static void copyNormal(String serverPath, String failPath, List<String> failName) {
		for (String strName : failName) {
			try {
				FileInputStream in;
				try {
					in = new java.io.FileInputStream("E:\\3W_Apps\\temp\\oldtemp\\" + strName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				String newName = "";
				if (strName.substring(0, strName.indexOf("Crash")).split("_").length == 2)
					newName = (String) strName.subSequence(strName.indexOf("Crash"), strName.length());
				else
					newName = strName;
				FileOutputStream out = new FileOutputStream("E:\\3W_Apps\\temp\\newtemp\\" + newName);
				byte[] bt = new byte[1024];
				int count;
				while ((count = in.read(bt)) > 0) {
					out.write(bt, 0, count);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		System.out.println("完成：" + failPath);
	}
}
