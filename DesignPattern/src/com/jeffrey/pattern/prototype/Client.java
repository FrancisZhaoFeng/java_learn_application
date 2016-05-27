package com.jeffrey.pattern.prototype;

import java.io.IOException;

/**
 * 功能说明：
 *http://blog.csdn.net/yanbober/article/details/45363525
 * @author weij
 */
public class Client {
	public static void main(String[] args) {
		Pet pet = new Pet("Tom");
		Student stu1 = new Student("A", 12, pet);
		try {
			Student stu2 = (Student) stu1.clone();
			System.out.println("浅层拷贝");
			System.out.println(stu1 == stu2);
			System.out.println(stu2.getPet().getName());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		try {
			Student stu3 = (Student) stu1.deepCopy();
			System.out.println("深层拷贝");
			System.out.println(stu1.getPet() == stu3.getPet());
			System.out.println(stu3.getPet().getName());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
