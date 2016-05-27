package com.zgf.pattern.singleton;

public class MySqlConnect {
	private static MySqlConnect  mySqlConnect;
	
	private MySqlConnect(){
		
	}
	
	public static MySqlConnect getMySqlConnect(){
		if(mySqlConnect == null){
			synchronized (MySqlConnect.class) {
				if(mySqlConnect==null){
					mySqlConnect = new MySqlConnect();
				}
			}
		}
		return mySqlConnect;
	}

}
