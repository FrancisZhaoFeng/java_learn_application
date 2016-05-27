package com.zgf.pattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Student implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Course course;

	public Student(String name, int age, Course course) {
		super();
		this.name = name;
		this.age = age;
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	/*
	 * 浅克隆：被克隆对象的所有变量都含有与原来的对象相同的值，而它所有的对其他对象的引用都仍然指向原来的对象。换一种说法就是浅克隆仅仅克隆所考虑的对象，而不克隆它所引用的对象
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Student)super.clone();
	}
	
	public Object deepClone() throws IOException, ClassNotFoundException{
		//字节数据输出流，暂存到内存中
		ByteArrayOutputStream  arrayOutputStream = new ByteArrayOutputStream();
		//序列化
		ObjectOutputStream   objectOutputStream = new ObjectOutputStream(arrayOutputStream);
		objectOutputStream.writeObject(this);
		ByteArrayInputStream  arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
		//反序列化
		return objectInputStream.readObject();
	}
	

}
