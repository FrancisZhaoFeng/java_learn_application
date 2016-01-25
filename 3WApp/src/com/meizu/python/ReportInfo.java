package com.meizu.python;


public class ReportInfo implements Comparable<ReportInfo>{
	private Integer sn;
	private String chName;
	private String apkInfo;
	private String pName;

	public ReportInfo() {

	}

	public ReportInfo(Integer sn, String chName, String apkInfo) {
		super();
		this.sn = sn;
		this.chName = chName;
		this.apkInfo = apkInfo;
	}

	@Override
	public String toString() {
		return "ReportInfo [sn=" + sn + ", chName=" + chName + ", apkInfo=" + apkInfo + ", pName=" + pName + "]";
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getApkInfo() {
		return apkInfo;
	}

	public void setApkInfo(String apkInfo) {
		this.apkInfo = apkInfo;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Override
	public int compareTo(ReportInfo arg0) {
		// TODO Auto-generated method stub
		return this.getSn().compareTo(arg0.getSn());
	}
}
