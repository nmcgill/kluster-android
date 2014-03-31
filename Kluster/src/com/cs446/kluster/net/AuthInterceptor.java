package com.cs446.kluster.net;

import retrofit.RequestInterceptor;

public class AuthInterceptor implements RequestInterceptor {
	
	public AuthInterceptor() {
	}

	@Override
	public void intercept(RequestFacade request) {
		request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUzMzlkMzdlNzA4NjI2ZGEyMDRiMTM5OSIsImVtYWlsIjoibm1jZ2lsbEBleGFtcGxlLmNvbSIsImV4cGlyZXMiOiIyMDE0LTA0LTMwVDIwOjQ0OjEzLjM0N1oifQ.ZLJy9uOvFV9xtq-pidRdK99drdcp2orBNfehRFfcWWk");
	}
}
