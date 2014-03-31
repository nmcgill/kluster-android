package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.util.JsonReader;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class SerializerUtils {

    @TargetApi(11)
    public static List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

    @TargetApi(11)
    public static List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }
}