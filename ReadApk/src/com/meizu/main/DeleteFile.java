package com.meizu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.meizu.tools.ReadFromFile;

public class DeleteFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ReadFromFile readFromFile = new ReadFromFile();
		List<String> listProfile = new ArrayList<String>();
		List<String> listLang = new ArrayList<String>();
		String profilePath = "C:/Users/zhaoguofeng/Desktop/多语言对比/profiles/";
		String langPath = "C:/Users/zhaoguofeng/Desktop/temp.txt";

		ReadFromFile.getFileListNormal(profilePath, listProfile, "*");
		ReadFromFile.readFile(langPath, listLang);

		for (String strProfile : listLang) {
			boolean flag = false;
			for (String strLang : listProfile) {
				if (strProfile.equals(strLang)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				// deleteFile(profilePath + strProfile);
				System.out.println(strProfile);
			}
		}
		System.out.println("finish~~~~,error size:");
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

}
