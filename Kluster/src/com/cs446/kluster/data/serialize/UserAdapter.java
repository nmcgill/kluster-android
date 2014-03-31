package com.cs446.kluster.data.serialize;

import java.io.IOException;

import com.cs446.kluster.models.User;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class UserAdapter extends TypeAdapter<User> {
	
    public User read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      
      return null;
    }
    
    public void write(JsonWriter writer, User value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      
      writer.beginObject();
      writer.name("username");
      writer.value(value.getUserName());
      writer.name("email");
      writer.value(value.getUserEmail());
      writer.name("password");
      writer.value(value.getPassword());
      writer.name("name");
      	writer.beginObject();
        writer.name("first");
        writer.value(value.getFirstName());
        writer.name("last");
        writer.value(value.getLastName());
        writer.endObject();
      writer.endObject();
    }
  }