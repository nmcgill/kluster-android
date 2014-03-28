package com.cs446.kluster.net.http;


public class AuthRequest extends Request {

    public AuthRequest(Method method, String url) {
        super(method, url);
        header("Authorization: Bearer ", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUzMzRjNTMzNDc1NjZhZGU2N2RlNDE2OCIsImVtYWlsIjoibm1jZ2lsbEBleGFtcGxlLmNvbSIsImV4cGlyZXMiOiIyMDE0LTA0LTI3VDAwOjQxOjQwLjQ5MloifQ.ehgvn9o0e2Q-HYCIjBStdDP1g-EKimxl0jZ8Cqdyawg");
    }
}