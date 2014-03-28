package com.cs446.kluster;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class ConfigManager {

    final public static String PROP_URL = "url";

    private Context mContext = null;

    private Properties mProperties = null;

    public ConfigManager(Context context) {
        mContext = context;
    }

    public String getProperty(String name) {
        ensurePropertiesLoaded();
        return mProperties.getProperty(name);
    }

    public String getProperty(String name, String defaultValue) {
        ensurePropertiesLoaded();
        return mProperties.getProperty(name, defaultValue);
    }

    private void ensurePropertiesLoaded() {
        if (mProperties == null) {
            loadProperties();
        }
    }

    private void loadProperties() {
        Resources resources = mContext.getApplicationContext().getResources();

        try {
            InputStream in = resources.openRawResource(R.raw.config);
            mProperties = new Properties();
            mProperties.load(in);
        } catch (IOException e) {
            Log.e("ConfigManager", "Failed to load properties", e);
        }
    }
}