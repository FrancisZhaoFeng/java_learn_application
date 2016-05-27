package com.zgf.pattern.builder;

/*
 *  下面是GOF对Builder模式的部分阐述，先列出来，用于与后文中的错误案例进行对比。文字很精辟，不易理解；但若真正理解了，会发现这些文字对已经将Builder模式的精髓描述完了。
		(1) 意图：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
		(2) 适用性：当同时满足以下情况的时候可以使用Builder模式
    		a. 当创建复杂对象的算法应该独立于该对象的组成部分以及他们的装配方式；
    		b. 当构造过程必须允许构造的对象有不同的表示；
 */
public class Client {
	public static void main(String arg[]) {
		PersonDirector director = new PersonDirector();
		//
		BuilderPerson builderPerson1 = new neZheBuilder();
		director.createPerson(builderPerson1, 1);
		builderPerson1.getPerson();
		//
		BuilderPerson builderPerson2 = new UltramanBuilder();
		director.createPerson(builderPerson2, 2);
		builderPerson2.getPerson();
	}

}
