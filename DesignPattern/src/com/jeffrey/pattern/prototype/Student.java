package com.jeffrey.pattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author weij
 */
public class Student implements Serializable, Cloneable {
	private static final long serialVersionUID = -4160976962521976156L;
	private String name;
	private int age;
	private Pet pet;

	public Student(String name, int age, Pet pet) {
		this.name = name;
		this.age = age;
		this.pet = pet;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 深层拷贝
	 *深克隆：被克隆对象的所有变量都含有与原来的对象相同的值，但它所有的对其他对象的引用不再是原有的，而这是指向被复制过的新对象。换言之，深复制把要复制的对象的所有引用的对象都复制了一遍，这种叫做间接复制。
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deepCopy() throws IOException, ClassNotFoundException {
		// 字节数组输出流，暂存到内存中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 序列化
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		// 反序列化
		return ois.readObject();
	}

	/**
	 * 浅层拷贝
	 *浅克隆：被克隆对象的所有变量都含有与原来的对象相同的值，而它所有的对其他对象的引用都仍然指向原来的对象。换一种说法就是浅克隆仅仅克隆所考虑的对象，而不克隆它所引用的对象
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Student student = (Student) super.clone();
		return student;
	}

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + ", age=" + age + '}';
	}
}
