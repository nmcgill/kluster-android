package com.cs446.kluster.user;

import java.math.BigInteger;

public class UserInfo{
	
    private String mUserName;
    private String mUserEmail;
    private String mFirstName;
    private String mLastName;
    private String mPassWord;
    
    public UserInfo(String userName, String userEmail, String firstName, String lastName){
    	mUserName=userName;
        mFirstName=firstName;
        mLastName=lastName;
        mUserEmail=userEmail;
    }
    
    public void setPassword(String password){
    	mPassWord=password;
    }
    
    public String getmPassword(){
    	return mPassWord;
    }

	public String getmUserEmail() {
		return mUserEmail;
	}

	public String getmUserName() {
		return mUserName;
	}

	public String getmFirstName() {
		return mFirstName;
	}

	public String getmLastName() {
		return mLastName;
	}
}
