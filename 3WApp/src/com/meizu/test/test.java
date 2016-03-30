package com.meizu.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.meizu.main.Rename;
import com.meizu.tools.ReadFromFile;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\zhaoguofeng\\Desktop\\apptest报告\\alllog\\";
		List<String> srcFileName = new ArrayList<String>();
		List<String> lFileNames = new ArrayList<String>();
		// 显示文件内容
		ReadFromFile.getFileListNormal(path, lFileNames, ".txt");
		System.out.println("\nTestReport.html 报告个数：" + lFileNames.size());
		for (String strFileName : lFileNames)
			test.readFile(path + strFileName, "package not installed");
//		
//		
//		ReadFromFile.getFileListNormal(path, srcFileName, "*");
//
//		for (int i = 0; i < srcFileName.size(); i++) {
//			String name = srcFileName.get(i);
//			name = name.substring(name.indexOf("_"), name.length());
//			name = String.valueOf(700 + i) + name;
//			System.out.println(name);
//			test.rename(path, srcFileName.get(i), name);
//		}
//		System.out.println("重命名结束");
	}

	public static void rename(String path, String srcName, String desName) {
		File file = new File(path + srcName); // 指定文件名及路径
		file.renameTo(new File(path + desName)); // 改名
	}
	
	public static boolean readFile(String fileName, String key) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			boolean out=false;
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if(tempString.contains(key)){
					if(!out){
						System.out.println("以行为单位读取文件内容，一次读一整行：" + fileName);
						out =true;
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
		return true;
	}
}
