package com.zgf.pattern.builder;

public class Person {
	private String name;
	private int head;
	private int body;
	private int arm;
	private int leg;
	public Person(){
		
	}
	

	public Person(String name, int head, int body, int arm, int leg) {
		super();
		this.name = name;
		this.head = head;
		this.body = body;
		this.arm = arm;
		this.leg = leg;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public int getArm() {
		return arm;
	}

	public void setArm(int arm) {
		this.arm = arm;
	}

	public int getLeg() {
		return leg;
	}

	public void setLeg(int leg) {
		this.leg = leg;
	}

}
