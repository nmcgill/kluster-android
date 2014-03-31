package com.cs446.kluster.net;

import retrofit.RequestInterceptor;

public class AuthInterceptor implements RequestInterceptor {
	
	public AuthInterceptor() {
	}

	@Override
	public void intercept(RequestFacade request) {
		request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUzMzhkNTZhZjY3YjY5ZTkxMjMxYjc1ZSIsImVtYWlsIjoibm1jZ2lsbEBleGFtcGxlLmNvbSIsImV4cGlyZXMiOiIyMDE0LTA0LTMwVDAyOjQwOjA4LjMwMFoifQ._7s-WLqDBT33Md-Cfs3y5--1aJQ7uPqhd9FRbHq6N2k");
	}
}
