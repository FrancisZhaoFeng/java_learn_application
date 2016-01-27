package com.meizu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meizu.bean.ApkName;
import com.meizu.tools.ReadFromFile;

import contants.Constant;

public class Rename {
	static ReadFromFile readFromFile = new ReadFromFile();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String path = Constant.fold_final_crashFailLog;
		// String path = "D:\\3W_APP报告\\M86_20160118015021\\测试结果汇总\\crashFail\\temptest\\";
		String path = "D:\\3W_APP报告\\M86_20160118015021\\测试结果汇总\\systemFailLog\\";
		Map<String, String> mapCrashLog = new HashMap<>();
		Map<String, Integer> mapApkName = new HashMap<>();
		Set<String> setPackage = new HashSet<String>();
		Rename.genAppListFromCrashLog(path, setPackage, mapCrashLog, mapApkName);
		for (Iterator iterator = setPackage.iterator(); iterator.hasNext();) {
			String strPackageName = (String) iterator.next();
			if (mapCrashLog.get(strPackageName) != null && mapApkName.get(strPackageName) != null) {
				Rename.rename(path, mapCrashLog.get(strPackageName), mapApkName.get(strPackageName));
			}
		}
		System.out.println("重命名结束");
	}

	public static void rename(String path, String name, int sn) {
		File file = new File(path + name); // 指定文件名及路径
		String desName = sn + "_" + name;
		System.out.println(name + "\t==>\t" + desName);
		file.renameTo(new File(path + desName)); // 改名
	}

	public static void genAppListFromCrashLog(String crashLogPath, Set<String> setPackage, Map<String, String> mapCrashLog, Map<String, Integer> mapApkName) {
		String crash = "Crash_";
		String notRespond = "NotRespond_";
		String time = ", time=";

		List<String> listCrashLog = new ArrayList<String>();
		List<ApkName> listApkName = new ArrayList<ApkName>();
		// 读取crash的log文件
		ReadFromFile.getFileListNormal(crashLogPath, listCrashLog, "*");
		// 读取服务器的所有app文件
		ReadFromFile.getApkList(Constant.serverApkPath127, listApkName, ".apk");
		Pattern pattern = Pattern.compile("\\d{1,5}_(Crash|NotRespond)_");
		// 遍历crash文件，将包名存放在set集合中
		for (String strCrashLog : listCrashLog) {
			String strPackageName = strCrashLog;
			if (pattern.matcher(strPackageName).find()) {
				continue;
			}
			if (strPackageName.contains(crash)) {
				strPackageName = strPackageName.split(crash)[1].split("\\.txt")[0];
			} else if (strPackageName.contains(notRespond)) {
				if (strPackageName.contains(time)) {
					strPackageName = strPackageName.split(notRespond)[1].split(time)[0];
				} else {
					strPackageName = strPackageName.split(notRespond)[1].split("\\.txt")[0];
				}
			}
			if (!strPackageName.contains(".txt") && !strPackageName.contains("(")) {
				strPackageName = strPackageName.replace(",", "");
				setPackage.add(strPackageName.toLowerCase());
				mapCrashLog.put(strPackageName.toLowerCase(), strCrashLog);
			}
		}
		System.out.println("重命名包名个数set size:" + setPackage.size());

		for (ApkName apkName : listApkName) {
			mapApkName.put(apkName.getName().toLowerCase(), apkName.getSn());
		}
	}
}
