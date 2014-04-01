package com.cs446.kluster.net;



public class AuthKlusterRestAdapter extends KlusterRestAdapter {
	
	public AuthKlusterRestAdapter() {
		super();
        setRequestInterceptor(new AuthInterceptor());
	}

}
