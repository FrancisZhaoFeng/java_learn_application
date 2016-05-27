package com.zgf.pattern.builder;

public class neZheBuilder implements BuilderPerson {
	private int fireIndex;

	public int getFireIndex() {
		return fireIndex;
	}




	@Override
	public void buildName(String name) {
		// TODO Auto-generated method stub
		person.setName(name);
	}
	
	public void setFireIndex(int fireIndex) {
		this.fireIndex = fireIndex;
	}

	@Override
	public void buildHead(int head) {
		// TODO Auto-generated method stub
		person.setHead(head);

	}

	@Override
	public void buildbody(int body) {
		// TODO Auto-generated method stub
		person.setBody(body);
	}

	@Override
	public void buildarm(int arm) {
		// TODO Auto-generated method stub
		person.setArm(arm);
	}

	@Override
	public void buildleg(int leg) {
		// TODO Auto-generated method stub
		person.setLeg(leg);
	}
	
	@Override
	public void getPerson() {
		// TODO Auto-generated method stub
		System.out.println("名字：" + person.getName() + "\t头个数：" + person.getHead() + "\t身体个数:" + person.getBody() + "\t手个数：" + person.getArm() + "\t腿个数：" + person.getLeg());
	}

}
