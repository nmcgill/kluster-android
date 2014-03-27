package com.cs446.kluster.user;

import java.math.BigInteger;

public class UserAuthInfo{
	
	private String mUserID;
    private String mToken;
    private String mTokenExpiry;
    private UserInfo mUserInfo;
    
    public UserAuthInfo(String userID, String token, String tokenExpiry, UserInfo userInfo){
    	mUserID=userID;
        mToken=token;
        mTokenExpiry=tokenExpiry;
        mUserInfo=userInfo;
    }

	public String getmUserID() {
		return mUserID;
	}

	public String getmToken() {
		return mToken;
	}

	public String getmTokenExpiry() {
		return mTokenExpiry;
	}
	
	public UserInfo getUserInfo(){
		return mUserInfo;
	}
}

