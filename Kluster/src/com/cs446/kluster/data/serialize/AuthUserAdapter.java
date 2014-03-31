package com.cs446.kluster.data.serialize;

import java.io.IOException;

import com.cs446.kluster.models.AuthUser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class AuthUserAdapter extends TypeAdapter<AuthUser> {
	
    public AuthUser read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
		String userId=null;
		String userName=null;
		String userEmail=null;
		String firstName=null;
		String lastName=null;
		String token=null;
		String tokenExpiry=null;
      
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

		return new AuthUser(userId, token, tokenExpiry, userName, userEmail, firstName, lastName); 
    }
    
    public void write(JsonWriter writer, AuthUser value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
    }
  }