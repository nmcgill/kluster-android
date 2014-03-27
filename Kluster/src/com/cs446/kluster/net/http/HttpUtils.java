package com.cs446.kluster.net.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by marlin_tfk on 2014-03-09.
 */
public class HttpUtils {

    public static String queryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        String separator = "";

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String value = entry.getValue() == null ? "" : entry.getValue().toString();
            sb.append(separator);
            sb.append(urlEncode(entry.getKey()));
            sb.append("=");
            sb.append(urlEncode(value));
            separator = "&";
        }

        return sb.toString();
    }

    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}