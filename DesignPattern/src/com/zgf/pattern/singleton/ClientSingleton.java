package com.zgf.pattern.singleton;

public class ClientSingleton {

	public static void main(String[] args) {
		MySqlConnect mySqlConnect1 = MySqlConnect.getMySqlConnect();
		MySqlConnect mySqlConnect2 = MySqlConnect.getMySqlConnect();
		System.out.println(mySqlConnect1 == mySqlConnect2);  //== 比较两个引用是否一样
	}

}
