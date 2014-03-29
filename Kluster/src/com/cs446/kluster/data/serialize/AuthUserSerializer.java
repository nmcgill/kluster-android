package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import android.annotation.TargetApi;
import android.util.JsonReader;

import com.cs446.kluster.models.AuthUser;

/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public class AuthUserSerializer implements Serializer<AuthUser> {

    @TargetApi(11)
    public AuthUser read(Reader reader) throws IOException {
        JsonReader jr = new JsonReader(reader);
        try {
            return AuthUserSerializer.readEvent(jr);
        } catch (IOException e) {
            return null;
        } finally {
            jr.close();
        }
    }
    
    @TargetApi(11)
    public AuthUser read(JsonReader jr) throws IOException {
    	return AuthUserSerializer.readEvent(jr);
    }

    public void write(Writer writer, AuthUser user) {

    }

    public static AuthUser readEvent(JsonReader reader) throws IOException {
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
}