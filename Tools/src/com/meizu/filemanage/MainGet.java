package com.meizu.filemanage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.tools.Tools;

public class MainGet {
	/**
	 * A方法追加文件：使用RandomAccessFile
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Tools.gAllFold();
		List<ApkName> lFileName = new ArrayList<ApkName>();
		List<ApkName> apkName = new ArrayList<ApkName>();
		// 显示文件内容
		ReadFromFile.getFileList(Constant.fold_MeizuAutoTest, lFileName, ".txt");
		for (ApkName strFileName : lFileName) {
			ReadFromFile.readFileByLines(Constant.fold_MeizuAutoTest + strFileName.getName(), apkName);
		}
		Collections.sort(apkName);
		WriteToFile.writeToText(apkName);
	}
}
