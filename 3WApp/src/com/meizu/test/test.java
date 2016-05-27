package com.meizu.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.meizu.main.Rename;
import com.meizu.tools.ReadFromFile;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String pathr = "D:\\3w_app_18001-21000\\1k\\";
		// String pathr = "\\\\172.16.11.127\\3w_app报告\\M85_20160118011047\\测试结果汇总\\failApk\\crashFailApk\\";
		List<String> srcFileName = new ArrayList<String>();
		ReadFromFile.getFileListNormal(pathr, srcFileName, "*");
		for (String name : srcFileName) {
			System.out.println(name + "====");// packName + "====" +
			fileNameToApkBean(name);
		}
	}

	private static void fileNameToApkBean(String name) {
		int sn = Integer.parseInt(name.substring(0, name.indexOf("_")));
		String packName = "", apkVersion = "";
		if (name.lastIndexOf("_v") != -1) {
			packName = name.substring(name.indexOf("_") + 1, name.lastIndexOf("_v"));
			apkVersion = name.substring(name.lastIndexOf("_v") + 1, name.length() - 4);
		}

		System.out.println(packName + "====" + apkVersion);
	}

	public static String getDate() {
		String date = new SimpleDateFormat("yyyyMMddmmss").format(new Date());
		return date;
	}

	public static void rename(String path, String srcName, String desName) {
		String pathr = "C:\\Users\\zhaoguofeng\\Desktop\\apptest报告\\alllog\\";
		List<String> srcFileName = new ArrayList<String>();
		ReadFromFile.getFileListNormal(pathr, srcFileName, "*");

		for (int i = 0; i < srcFileName.size(); i++) {
			String name = srcFileName.get(i);
			name = name.substring(name.indexOf("_"), name.length());
			name = String.valueOf(700 + i) + name;
			System.out.println(name);
			test.rename(pathr, srcFileName.get(i), name);
		}
		System.out.println("重命名结束");
		File file = new File(pathr + srcName); // 指定文件名及路径
		file.renameTo(new File(pathr + desName)); // 改名
	}

	public static boolean readFile() {
		String path = "C:\\Users\\zhaoguofeng\\Desktop\\tem\\报告\\EC\\image\\";
		List<String> srcFileName = new ArrayList<String>();
		List<String> lFileNames = new ArrayList<String>();
		// 显示文件内容
		ReadFromFile.getFileListNormal(path, lFileNames, ".txt");
		System.out.println("\n个数：" + lFileNames.size());
		for (String strFileName : lFileNames) {
			File file = new File(path + strFileName);
			BufferedReader reader = null;
			try {
				boolean out = false;
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					if (tempString.contains("package not installed")) {
						if (!out) {
							System.out.println("以行为单位读取文件内容，一次读一整行：" + strFileName);
							out = true;
						}
						System.out.println(tempString);
					}
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
		}
		return true;
	}
}
