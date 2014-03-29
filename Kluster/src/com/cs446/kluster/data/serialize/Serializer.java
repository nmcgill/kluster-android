package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import android.util.JsonReader;


/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public interface Serializer<T> {

    public T read(Reader reader) throws IOException;
    
    public T read(JsonReader jr) throws IOException;

    public void write(Writer writer, T obj) throws IOException;

}