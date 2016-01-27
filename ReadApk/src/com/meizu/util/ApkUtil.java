package com.meizu.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.meizu.bean.ApkName;

public class ApkUtil {

	private final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

	public ApkName getApkInfo(String apkPath) {
		ApkName apkInfo = new ApkName();
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(getXmlInputStream(apkPath));
		} catch (Exception e) {
			e.printStackTrace();
			// return null;
		}
		Element root = document.getRootElement();// 跟节点-->manifest
		apkInfo.setApkName(root.getAttributeValue("package"));
		getActivity(root, apkInfo);
		return apkInfo;
	}

	private void getActivity(Element root, ApkName apkInfo) {
		Element element = root.getChild("application");
		List<?> activity = element.getChildren("activity");// 子节点-->uses-sdk
		List<String> activitys = new ArrayList<String>();
		for (Object object : activity) {
			String aty = ((Element) object).getAttributeValue("name", NS);
			activitys.add(aty);
		}
		apkInfo.setActivitys(activitys);
	}

	@SuppressWarnings("unused")
	private void getUsesPer(Element root, ApkName apkInfo) {
		apkInfo.setVersionCode(root.getAttributeValue("versionCode", NS));
		apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
		Element elemUseSdk = root.getChild("uses-sdk");// 子节点-->uses-sdk
		apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
		List<?> listPermission = root.getChildren("uses-permission");// 子节点是个集合
		List<String> permissions = new ArrayList<String>();
		for (Object object : listPermission) {
			String permission = ((Element) object).getAttributeValue("name", NS);
			permissions.add(permission);
		}
		apkInfo.setUses_permission(permissions);
	}

	@SuppressWarnings("resource")
	public boolean apkUsable(String apkPath) {
		ZipFile zipFile = null;
		boolean flag = false;
		try {
			zipFile = new ZipFile(apkPath);
			if (zipFile != null)
				flag = true;
		} catch (IOException e) {
		}
		return flag;
	}

	private InputStream getXmlInputStream(String apkPath) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(apkPath);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
		} catch (IOException e) {
			// e.printStackTrace();
			try {
				inputStream.close();
				zipFile.close();
			} catch (IOException e1) {
				// e1.printStackTrace();
			}
		}
		return xmlInputStream;
	}

}
