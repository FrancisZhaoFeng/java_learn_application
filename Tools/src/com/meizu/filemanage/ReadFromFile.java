package com.meizu.filemanage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFromFile {
	public static void getFileList(String baseFilePath, List<ApkName> listApkFName, String keyword) {
		File file = new File(baseFilePath);
		File[] filesName = file.listFiles();
		if (filesName != null && file.exists()) {
			for (File fileName : filesName) {
				String strName = fileName.getName().toString();
				if (strName.trim().endsWith(keyword) || keyword.contains("*")) {
					ApkName an = new ApkName();
					an.setName(strName);
					if (strName.indexOf("_") != -1) {
						an.setSn(Integer.parseInt(strName.substring(0, strName.indexOf("_"))));
					} else {
						an.setSn(0);
					}
					listApkFName.add(an);
				}
			}
		}
	}

	public static void getFileListNormal(String baseFilePath, List<String> listName, String keyword) {
		File file = new File(baseFilePath);
		File[] filesName = file.listFiles();
		if (filesName != null && file.exists()) {
			for (File fileName : filesName) {
				String strName = fileName.getName().toString();
				if (strName.trim().endsWith(keyword) || keyword.contains("*")) {
					listName.add(strName);
				}
			}
		}
	}

	public static void getFileListByPng(String baseFilePath, List<ApkName> listApkFName, String keyword) {
		File file = new File(baseFilePath);
		File[] filesName = file.listFiles();
		if (filesName != null && file.exists()) {
			ApkName apkTemp = new ApkName();
			for (File fileName : filesName) {
				String strName = fileName.getName().toString();
				if (strName.trim().endsWith(keyword)) {
					ApkName an = new ApkName();
					if (strName.indexOf("_") != -1) {
						an.setSn(Integer.parseInt(strName.substring(0, strName.indexOf("_"))));
					} else {
						an.setSn(0);
					}
					boolean flag = false;
					if (strName.contains("Crash_")) {
						an.setName(an.getSn() + "_" + strName.subSequence(strName.indexOf("Crash_") + 6, strName.lastIndexOf("__2015")) + ".apk");
					} else if (strName.contains("NotRespond_")) {
						an.setName(an.getSn() + "_" + strName.subSequence(strName.indexOf("NotRespond_") + 11, strName.lastIndexOf("__2015")) + ".apk");
					} else {
						flag = true;
					}
					if (an.getName() != null && an.getName().equals(apkTemp.getName()))
						continue;
					System.out.println(an.getName());
					if (!flag) {
						listApkFName.add(an);
						apkTemp = an;
					}
				}
			}
		}
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			ReadFromFile.showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static void readAppTest(String fileName, List<ApkName> apkName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：" + fileName);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				ApkName an = new ApkName();
				// 显示行号
				if (!tempString.contains("Crash") && !tempString.contains("版本号") && !tempString.contains("NotRespond")) {
					int indexNum = 0, index_ = 0;
					Pattern patternFristChar = Pattern.compile("[0-9]");
					Pattern patternLetter = Pattern.compile("[A-Z]");
					Matcher matcher = patternFristChar.matcher(tempString);
					if (matcher.find())
						indexNum = tempString.indexOf(matcher.group());
					if (indexNum != -1 && (index_ = tempString.indexOf("_")) != -1 && (indexNum < index_)) {
						// System.out.println(indexNum + "==" + index_ + ":" + tempString);
						if (!patternLetter.matcher(tempString.substring(indexNum, index_)).find()) {
							an.setSn(Integer.parseInt(tempString.substring(indexNum, index_)));
							an.setName(tempString.substring(indexNum, tempString.length()));
							apkName.add(an);
						}
					}

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

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static boolean readFile(String fileName, List<String> apkName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：" + fileName);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				apkName.add(tempString);
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

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static int readFileGetMin(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		int minValue = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (tempString.contains("最大值"))
					minValue = Integer.parseInt(tempString.split("：")[1]);
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
		return minValue;
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFileByLines(String writeFile, List<ApkName> apkName) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(writeFile);
			for (ApkName an : apkName) {
				fos.write((an.getName() + "\r\n").getBytes());
			}
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + oldname);
			File newfile = new File(path + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	public static void main(String args[]) {
		ReadFromFile.renameFile("E:\\3W_Apps\\temp\\oldtemp\\", "Crash_baidumapsdk.demo222_MA01_20151016160606.png", "123_Crash_baidumapsdk.demo222_MA01_20151016160606.png");
	}
}
