package com.meizu.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.meizu.bean.ApkName;
import com.meizu.tools.CopyFile;
import com.meizu.tools.ExcelHandle;
import com.meizu.tools.ReadFromFile;
import com.meizu.tools.WriteToFile;

import contants.Constant;

public class MainGet {
	static ReadFromFile readFromFile = new ReadFromFile();
	static WriteToFile writeToFile = new WriteToFile();
	static CopyFile copyFile = new CopyFile();
	static ExcelHandle excelHandle = new ExcelHandle();

	public static void main(String[] args) {
		int num = 1;// 代表1轮测试，还是2轮测试

		ExcelHandle excelHandle = new ExcelHandle();
		String meizuAutoTest = "";
		if (num == 1)
			meizuAutoTest = Constant.fold_MeizuAutoTest;
		else
			meizuAutoTest = Constant.fold_srMeizuAutoTest;
		// 吃时候list
		List<String> lFileName = new ArrayList<String>();
		List<ApkName> apkName = new ArrayList<ApkName>();
		// 显示文件内容
		readFromFile.getFileList(meizuAutoTest, lFileName, ".txt");
		for (String strFileName : lFileName)
			readFromFile.readAppTest(meizuAutoTest + strFileName, apkName);
		Collections.sort(apkName);
		String indexRange = writeToFile.writeRun(apkName, num); // 分析报告内容，写入不同的文件
		excelHandle.genExcel(num);
		if (num == 1) {
			copyFile.copyFile();
			System.out.println("一轮报告生成完成");
		}
		if (num == 2) {
			int minIndex = Integer.parseInt(indexRange.split("_")[0]);
			int maxIndex = Integer.parseInt(indexRange.split("_")[1]);
			excelHandle.getNameByPackage(Constant.excel_topapps, Constant.txt_srInstallFail, minIndex, maxIndex);
			excelHandle.getNameByPackage(Constant.excel_topapps, Constant.txt_srOpenFail, minIndex, maxIndex);
			System.out.println("二轮报告生成完成");
		}
	}

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
}
