package com.meizu.filemanage;

public class Sort implements Comparable<Sort> {

	private String name;
	private Integer number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public int compareTo(Sort arg0) {
		return this.getNumber().compareTo(arg0.getNumber());
	}
}
