package com.zgf.pattern.builder;

public class PersonDirector {

	public BuilderPerson createPerson(BuilderPerson builderPerson,int type) {
		switch (type) {
		case 1:
			builderPerson.buildName("哪吒");
			builderPerson.buildHead(3);
			builderPerson.buildbody(1);
			builderPerson.buildarm(6);
			builderPerson.buildleg(2);
			break;
		case 2:
			builderPerson.buildName("奥特曼");
			builderPerson.buildHead(1);
			builderPerson.buildbody(1);
			builderPerson.buildarm(2);
			builderPerson.buildleg(2);
			break;
		}
		return builderPerson;
	}
}
