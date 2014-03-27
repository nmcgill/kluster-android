package com.cs446.kluster.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

import com.cs446.kluster.user.UserAuthInfo;
import com.cs446.kluster.user.UserInfo;


import android.content.ContentResolver;
import android.net.ParseException;

import android.util.JsonReader;

//Sample Request:
//{
//    "email": "jsmith@example.com",
//    "password": "123456"
//}
//
//Sample Response:
//{
//    "_id": "532f7ee2bc34f83684d28dd5",
//    "username": "jsmith",
//    "email": "jsmith@example.com",
//    "name": {
//        "first": "Jon",
//        "last": "Smith"
//    },
//    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1naW5nZXJpY2hAZXhhbXBsZS5jb20ifQ.nnjHcV_92gDVTBaWp9JW4bE_EIsZnpGqDabPBhIQzV4",
//    "tokenExpires": "2014-04-23T00:40:07.000Z"
//}


public class AuthResponseReader {
	private ContentResolver mContentResolver;

	public AuthResponseReader(ContentResolver c) {
		mContentResolver = c;
	}

	public UserAuthInfo readJsonStream(InputStream in) throws IOException, ParseException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		
		String userId=null;
		String userName=null;
		String userEmail=null;
		String firstName=null;
		String lastName=null;
		String token=null;
		String tokenExpiry=null;
		
        try {
    		reader.beginObject();
    		while (reader.hasNext()) {
    			String name = reader.nextName();
    			if (name.equals("_id")) {
    				userId=reader.nextString();
    			}
    			else if(name.equals("username")){
    				userName=reader.nextString();
    			}
    			else if(name.equals("email")){
    				userEmail=reader.nextString();
    			}
    			else if(name.equals("name")){
    				reader.beginObject();
    				while (reader.hasNext()) {
    					name = reader.nextName();
    					if (name.equals("first")) {
    						firstName = reader.nextString();
    					} else if (name.equals("last")) {
    						lastName = reader.nextString();
    					}
    				}
    				reader.endObject();
    			}
    			else if(name.equals("token")){
    				token=reader.nextString();
    			}
    			else if(name.equals("tokenExpires")){
    				tokenExpiry=reader.nextString();
    			}
    			else {
    				reader.skipValue();
    			}
    		}
    		reader.endObject();
        }
         finally {
          reader.close();
        }
        
        return new UserAuthInfo(userId, token, tokenExpiry, new UserInfo(userName, userEmail, firstName, lastName));
    }
}