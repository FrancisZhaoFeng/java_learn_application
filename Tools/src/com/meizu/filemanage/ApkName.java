package com.meizu.filemanage;

/**
 * @author zhaoguofeng 基类： sn：记录apk在top的excel文档中的变化 name：记录apk文件名
 */
public class ApkName implements Comparable<ApkName> {
	private Integer sn;
	private String name;
	private String fName;

	public ApkName() {

	}

	public ApkName(String fName) {
		this.sn = Integer.valueOf(fName.split("_")[0]);
		this.name = fName.substring(fName.indexOf("_") + 1);
		this.fName = fName;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fName;
	}

	@Override
	public int compareTo(ApkName arg0) {
		// TODO Auto-generated method stub
		return this.getSn().compareTo(arg0.getSn());
	}

}
