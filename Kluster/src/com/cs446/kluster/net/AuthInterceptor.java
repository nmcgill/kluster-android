package com.cs446.kluster.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.cs446.kluster.KlusterApplication;

import retrofit.RequestInterceptor;

public class AuthInterceptor implements RequestInterceptor {
	
	public AuthInterceptor() {
	}

	@Override
	public void intercept(RequestFacade request) {
		SharedPreferences pref = KlusterApplication.getInstance().getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
		
		String token = pref.getString("token", "");
		
		request.addHeader("Authorization", "Bearer " + token);
	}
}
