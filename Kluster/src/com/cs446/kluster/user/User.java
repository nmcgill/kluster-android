package com.cs446.kluster.user;


public class User {
	
    private String mUserName;
    private String mUserEmail;
    private String mFirstName;
    private String mLastName;
    private String mPassword;
    
    public User(String userName, String userEmail, String firstName, String lastName) {
    	mUserName=userName;
        mFirstName=firstName;
        mLastName=lastName;
        mUserEmail=userEmail;
    }
    
    public void setPassword(String password){
    	this.mPassword = password;
    }
    
    public String getPassword(){
    	return mPassword;
    }

	public String getUserEmail() {
		return mUserEmail;
	}

	public String getUserName() {
		return mUserName;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getLastName() {
		return mLastName;
	}
}
