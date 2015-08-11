package com.meizu.filemanage;

/**
 * @author zhaoguofeng
 * 基类：
 * sn：记录apk在top的excel文档中的变化
 * name：记录apk文件名
 */
public class ApkName implements Comparable<ApkName> {
	private Integer sn;
	private String name;

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

	@Override
	public int compareTo(ApkName arg0) {
		// TODO Auto-generated method stub
		return this.getSn().compareTo(arg0.getSn());
	}

}
