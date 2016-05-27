package com.zgf.pattern.builder;

public interface BuilderPerson {
	Person person = new Person();
	void buildName(String name);
	void buildHead(int head);
	void buildbody(int body);
	void buildarm(int arm);
	void buildleg(int leg);
	void getPerson();
}
