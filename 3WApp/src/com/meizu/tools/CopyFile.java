package com.meizu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meizu.bean.ApkName;

import contants.Constant;

/**
 * @author zhaoguofeng 根据文件信息，从服务器中copy apk文件到本地目录
 */
public class CopyFile {
	static ReadFromFile readFromFile = new ReadFromFile();
	static CopyFile copyFile = new CopyFile();

	public static void main(String[] args) {
		// copyFile.copyLogHandle(Constant.fold_final_openFailLog, Constant.fold_com_dataOpenFailLog, "D:\\temp.txt");
		// copyFile.copyLogHandle(Constant.fold_final_l_crashFailLog, Constant.fold_final_l_crashFailComLog, Constant.fold_sS_data + "m80_i.txt");
		// copyFile.copyLogHandle(Constant.fold_final_l_installFailLog, Constant.fold_final_l_installFailComLog, Constant.fold_sS_data + "m80_i.txt");
		copyFile.copyLogHandle(Constant.fold_final_l_openFailLog, Constant.fold_final_l_openFailComLog, Constant.fold_sS_data + "m80_o.txt");
	}

	public void copyLogHandle(String srcPath, String desPath, String filePath) {
		List<String> copyName = new ArrayList<>();
		List<String> packageName = new ArrayList<>();
		List<String> logName = new ArrayList<>();
		Set<String> setTemp = new HashSet<>();

		readFromFile.readFile(filePath, packageName);
		readFromFile.getFileList(srcPath, logName, "*");

		System.out.println("packageName size:" + packageName.size());
		System.out.println("logName size:" + logName.size());
		for (String lName : logName) {
			for (int i = 0; i < packageName.size(); i++) {
				if (lName.subSequence(0, lName.indexOf("_")).equals(packageName.get(i))) {
					copyName.add(lName);
					// if (!setTemp.add(packageName.get(i))) {
					// System.out.println(packageName.get(i));
					// }
					break;
				}
			}
		}
		System.out.println("需要复制的log文件个数：" + copyName.size() + "包名个数：" + setTemp.size());
		CopyFile.copyNormal(srcPath, desPath, copyName, false);
		System.out.println("复制log完毕。。。。。");
	}

	/**
	 * 根据类WriteToFile中生成的文件：txt_openFail,txt_installFail,txt_downloadFail 的内容 从服务器中copy对应的apk到本地文件夹中
	 */
	public void copyFile() {
		// 获取处理对象
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> openFailName = new ArrayList<ApkName>();
		List<ApkName> installFailName = new ArrayList<ApkName>();
		List<ApkName> downloadFailName = new ArrayList<ApkName>();
		// 初始化list
		readFromFile.getApkList(Constant.serverPath, lFileName, ".apk");
		readFromFile.readAppTest(Constant.txt_openFail, openFailName);
		readFromFile.readAppTest(Constant.txt_installFail, installFailName);
		readFromFile.readAppTest(Constant.txt_downloadFail, downloadFailName);
		// copy文件
		CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, openFailName, false);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_installFail, installFailName, false);
		CopyFile.copyApk(Constant.serverPath, Constant.fold_downloadFail, downloadFailName, false);
	}

	/**
	 * 用于自由的copy文件
	 */
	public void copyFileTest() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> failName = new ArrayList<ApkName>();
		// 初始化list
		readFromFile.getApkList(Constant.serverPath, lFileName, ".apk");
		readFromFile.readAppTest("E:\\3W_Apps\\2015-10-15\\fold_TextExcel\\txt_openFail.txt", failName);
		// copy文件
		CopyFile.copyApk(Constant.serverPath, Constant.fold_openFail, failName, false);
	}

	@SuppressWarnings("unused")
	private void handleReName() {
		// 定义list：服务器文件名；打开下载失败文件名；安装失败文件名
		List<String> lFileName = new ArrayList<String>();
		// 初始化list
		readFromFile.getFileList("E:\\3W_Apps\\temp\\oldtemp", lFileName, "*");
		// copy文件
		CopyFile.copyNormal(Constant.serverPath, Constant.fold_openFail, lFileName, false);
	}

	/**
	 * 执行复制操作的方法
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public static void copyApk(String serverPath, String failPath, List<ApkName> failName, boolean cover) {
		System.out.println("正在进行copy.......");
		for (ApkName an : failName) {
			try {
				if (!an.getfName().contains(".apk")) {
					System.out.println("没有进行copy：" + an.getSn());
					continue;
				}
				if (!cover && new File(failPath + an.getfName()).exists()) {
					System.out.println("文件已存在不进行copy操作：" + failPath + an.getfName());
					continue;
				}
				FileInputStream in;
				try {
					in = new java.io.FileInputStream(serverPath + an.getfName());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				FileOutputStream out = new FileOutputStream(failPath + an.getfName());
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
		System.out.println("copy apk到路径完成：" + failPath);
	}

	/**
	 * 执行复制操作的方法
	 * 
	 * @param serverPath
	 * @param odFailPath
	 * @param odFailName
	 */
	public static void copyNormal(String serverPath, String failPath, List<String> failName, boolean cover) {
		for (String strName : failName) {
			try {
				FileInputStream in;
				try {
					in = new java.io.FileInputStream(serverPath + strName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				if (!cover && new File(failPath + strName).exists()) {
					System.out.println("文件已存在不进行copy操作：" + failPath + strName);
					continue;
				}
				FileOutputStream out = new FileOutputStream(failPath + strName);
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
