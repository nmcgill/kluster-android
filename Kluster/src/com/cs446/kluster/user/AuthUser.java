package com.cs446.kluster.user;


public class AuthUser extends User {
	
	private String mUserID;
    private String mToken;
    private String mTokenExpiry;
    
    public AuthUser(String userID, String token, String tokenExpiry, String userName, String userEmail, String firstName, String lastName) {
    	super(userName, userEmail, firstName, lastName);
    	mUserID=userID;
        mToken=token;
        mTokenExpiry=tokenExpiry;

    }

	public String getUserID() {
		return mUserID;
	}

	public String getToken() {
		return mToken;
	}

	public String getTokenExpiry() {
		return mTokenExpiry;
	}
}

