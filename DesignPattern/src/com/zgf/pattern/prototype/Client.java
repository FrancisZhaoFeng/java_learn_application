package com.zgf.pattern.prototype;

import java.io.IOException;

public class Client {
	public static void main(String[] args) {
		Course course = new Course("c++", "Mr zhang");
		Student student = new Student("zgf", 23, course);
		// 浅层复制
		try {
			Student stuA = (Student) student.clone();
			System.out.println(student.getCourse() == stuA.getCourse());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Student stuB = (Student) student.deepClone();
			System.out.println(student.getCourse() == stuB.getCourse());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
